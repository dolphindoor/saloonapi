/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saloon.controller;

import com.dolphindoors.resource.jaxrs.JaxResponse;
import com.dolphindoors.resource.jaxrs.PageableRequest;
import com.dolphindoors.resource.utilities.Msg;
import com.saloon.dto.CustomerDto;
import com.saloon.dto.Message;
import com.saloon.services.CustomerService;
import com.saloon.services.DefaultService;
import java.util.List;
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


/**
 *
 * @author Pascal
 */
@Path("v1/customers")
public class CustomerController {
    @Inject private CustomerService customerService;
    @Inject private DefaultService ds;
 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CustomerDto typeDto){
        CustomerDto dto = customerService.save(typeDto);
        return JaxResponse.created(Msg.CREATED, dto);
    }
    
    @POST
    @Path("/sms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(Message message){
        return JaxResponse.ok(ds.sms(message.getNumbers(), message.getTextMessage()));
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(CustomerDto typeDto){
        CustomerDto dto = customerService.save(typeDto);
        return JaxResponse.ok(Msg.UPDATED, dto);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@BeanParam PageableRequest pageableRequest){
         return JaxResponse.ok(customerService.fetchAllCustomers(pageableRequest));
    }
    
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("filter") String filter, @BeanParam PageableRequest pageableRequest){
        return JaxResponse.ok(Msg.RECORD_FOUND, customerService.filterCustomers(filter,pageableRequest));
    }
    
    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("customerId") String customerId){
        return JaxResponse.ok(Msg.RECORD_FOUND, customerService.findCustomerById(customerId));
    }
    

    @DELETE
    @Path("/{customerId}")
    public Response delete(@PathParam("customerId") String customerId){
        boolean delete = customerService.deleteCustomer(customerId);
        if(delete)
            return JaxResponse.ok(Msg.DELETE_MESSAGE,delete);
        return JaxResponse.ok("Could not delete customer",delete);
    }
}
