package com.saloon.controller;

import com.dolphindoors.resource.jaxrs.JaxResponse;
import com.dolphindoors.resource.jpa.CrudApi;
import static com.dolphindoors.resource.utilities.JUtils.hashText;
import com.dolphindoors.resource.utilities.Msg;
import com.saloon.dto.UserDTO;
import com.saloon.entity.Employee;
import com.saloon.entity.User;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richardnarh
 */
@Path("v1/users")
public class UserController {
    @Inject private CrudApi crudApi;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserDTO dto){
         User user = new User();
         if(dto.getEmployeeId() == null) throw new IllegalArgumentException("EmployeeId is required");
         Employee employee = crudApi.find(Employee.class, dto.getEmployeeId());
         user.setEmployee(employee);
         user.setRole(dto.getRole());
         user.setUsername(dto.getUsername());
         user.setPassword(hashText(dto.getPassword()));
         User u = crudApi.save(user);
         UserDTO userDTO = new UserDTO();
         userDTO.setEmployee(employee.getFullName());
         userDTO.setEmployeeId(employee.getId());
         userDTO.setUsername(u.getUsername());
         userDTO.setId(u.getId());
         userDTO.setRole(u.getRole());
        return JaxResponse.created(Msg.CREATED, userDTO);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(UserDTO dto){
         User user = null;
         if(dto.getEmployeeId() == null) throw new IllegalArgumentException("EmployeeId is required");
         Employee employee = crudApi.find(Employee.class, dto.getEmployeeId());
         
         if(dto.getId() != null){
             user = crudApi.find(User.class, dto.getId());
         }else{
             user = new User();
         }
         user.setEmployee(employee);
         user.setRole(dto.getRole());
         user.setUsername(dto.getUsername());
         User u = crudApi.save(user);
         
         UserDTO userDTO = new UserDTO();
         userDTO.setEmployee(employee.getFullName());
         userDTO.setEmployeeId(employee.getId());
         userDTO.setUsername(u.getUsername());
         userDTO.setId(u.getId());
         userDTO.setRole(u.getRole());
        return JaxResponse.created(Msg.CREATED, userDTO);
    }
    
}
