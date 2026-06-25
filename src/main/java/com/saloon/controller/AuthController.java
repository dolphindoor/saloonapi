/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.controller;

import com.dolphindoors.resource.enums.Role;
import com.dolphindoors.resource.jaxrs.JaxResponse;
import com.dolphindoors.resource.jpa.CrudApi;
import com.dolphindoors.resource.utilities.DateUtil;
import static com.dolphindoors.resource.utilities.JUtils.hashText;
import com.dolphindoors.resource.utilities.Msg;
import com.dolphindoors.resource.utilities.Pattern;
import com.saloon.dto.AuthRequest;
import com.saloon.dto.UserDTO;
import com.saloon.entity.Attendance;
import com.saloon.entity.Employee;
import com.saloon.entity.User;
import com.saloon.services.DefaultService;
import com.saloon.services.EmployeeService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richa
 */
@Path("v1/auth")
public class AuthController {

    @Inject private EmployeeService employeeService;
    @Inject private DefaultService ds;
    @Inject private CrudApi crudApi;
    
    private static final String ROOT_PATH = System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("win") ?
    System.getProperty("user.home") + "/Documents/orah/attendance/"+LocalDate.now()+"/" : "/var/orah/attendance/"+LocalDate.now()+"/";
    
    @POST
    @Path(value = "/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogin(AuthRequest authRequest) {
        System.out.println("username: "+authRequest.getUsername());
        System.out.println("password: "+authRequest.getPassword());
        UserDTO dtoUser = Optional.ofNullable(User.query(crudApi.getEm())
        .where(User._username, authRequest.getUsername())
                    .andWhere(User._password, hashText(authRequest.getPassword()))
                    .execute())
                .map(user -> {
                   UserDTO dto = new UserDTO();
                   dto.setId(user.getId());
                   dto.setEmployee(user.getEmployee().getFullName());
                   dto.setEmployeeId(user.getEmployee().getId());
                   dto.setUsername(user.getUsername());
                   return dto;
                })
                .orElse(null);
        if (dtoUser != null) {
            return JaxResponse.ok(Msg.RECORD_FOUND, dtoUser);
        }
        return JaxResponse.error(Msg.FAILED, "Email/Password Incorrect");
    }
    
    @GET
    @Path(value = "/{id}")
    public Response findById(String id){
        Employee emp = crudApi.findById(Employee.class, id);
        return JaxResponse.ok(emp);
    }

    @POST
    @Path(value = "/attendance/{userCode}/{imageName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attendance(InputStream file, @PathParam("userCode") String userCode,  @PathParam("imageName") String imageName) {
        String ok = null;
        Employee emp = crudApi.getEm().createQuery("SELECT e FROM Employee e WHERE e.userCode = :userCode", Employee.class)
                .setParameter(Employee._userCode, userCode)
                .getResultStream().findFirst().orElse(null);
        if (emp != null) {
            
            Attendance att = crudApi.getEm().createQuery("SELECT e FROM Attendance e WHERE e.employee =:employee AND e.valueDate = :valueDate", Attendance.class)
                    .setParameter(Attendance._employee, emp)
                    .setParameter(Attendance._valueDate, LocalDate.now())
                    .getResultStream().findFirst().orElse(null);
            
            System.out.println("attendance: "+att);
            if (att != null) {
                return JaxResponse.ok(Msg.FAILED, "Attendance already captured for today!");
            }
            
            try {
                File uploadDir = new File(ROOT_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                String filePath = ROOT_PATH + imageName;
                Files.copy(file, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

                Attendance a = new Attendance();
                a.setEmployee(emp);
                a.setAttendanceTime(LocalDateTime.now());
                a.setUserImage(filePath);

                if (crudApi.save(a) != null) {
                    ok = "Attendance saved successful";
                }
                
                User user = User.query(crudApi.getEm())
                        .where(User._role, Role.SUPER_USER)
                        .execute();
                
                if(ok != null){
                    String message = "Attendance recorded for "+emp.getFullName() +" at "+DateUtil.localDateTimeToString(a.getAttendanceTime(), Pattern.hhmma);
                    ds.sms(List.of(user.getEmployee().getPhoneNumber()),  message);
                }
            } catch (IOException e) {
                e.getMessage();
                return JaxResponse.error("Failed to upload file: " + e.getMessage());
            }
            
        } else {
            return JaxResponse.ok(Msg.FAILED, "Incorrect User Code");
        }
        return JaxResponse.ok(ok);
    }
}
