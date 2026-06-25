/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.controller;

import com.dolphindoors.resource.jaxrs.JaxResponse;
import com.dolphindoors.resource.jaxrs.PageableRequest;
import com.dolphindoors.resource.jpa.CrudApi;
import com.dolphindoors.resource.utilities.Msg;
import com.saloon.dto.EmployeeDto;
import com.saloon.entity.Attendance;
import com.saloon.services.EmployeeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Richard Narh
 */
@Path("v1/employees")
public class EmployeeController {
    @Inject private EmployeeService employeeService;
    @Inject private CrudApi crudApi;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(EmployeeDto dto){
        EmployeeDto employeeDto = employeeService.save(dto);
        return JaxResponse.created(Msg.CREATED, employeeDto);
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(EmployeeDto dto){
        EmployeeDto employeeDto = employeeService.save(dto);
        return JaxResponse.ok(Msg.UPDATED, employeeDto);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@BeanParam PageableRequest pageableRequest){
         return JaxResponse.ok(employeeService.fetchEmployees(pageableRequest));
    }
    
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @BeanParam PageableRequest pageableRequest){
        return JaxResponse.ok(Msg.RECORD_FOUND, employeeService.fetchEmployees(filter,pageableRequest));
    }
    
    @GET
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("employeeId") String employeeId){
        EmployeeDto employeeDto = employeeService.findById(employeeId);
        return JaxResponse.ok(Msg.RECORD_FOUND, employeeDto);
    }
    
    @PUT
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response revoke(@PathParam("employeeId") String employeeId){
        return JaxResponse.ok(employeeService.revoke(employeeId));
    }
    
    @DELETE
    @Path("/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("employeeId") String employeeId){
        boolean delete = employeeService.deleteEmployee(employeeId);
        return JaxResponse.ok(Msg.DELETED, delete);
    }
    
    @GET
    @Path("/attendance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attendancex(@QueryParam("date") String date){
         return JaxResponse.ok(employeeService.fetchAttendance(date));
    }
    
    @GET
    @Path("attendance/{id}/image")
    @Produces({"image/jpeg", "image/png"})
    public Response getImage(@PathParam("id") String id){
        try {
            Attendance attendance = crudApi.find(Attendance.class, id);
            java.nio.file.Path filePath = Paths.get(attendance.getUserImage());
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            StreamingOutput stream = output -> {
                Files.copy(filePath, output);
                output.flush();
            };
            return Response.ok(stream)
                    .header("Content-Disposition", "inline; filename=\"" + filePath.getFileName() + "\"")
                    .header("Cache-Control", "public, max-age=31536000")
                    .type(mimeType)
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error serving image: " + e.getMessage())
                    .build();
        }
    }
}
