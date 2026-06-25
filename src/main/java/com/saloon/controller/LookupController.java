package com.saloon.controller;

import com.dolphindoors.resource.jaxrs.JaxResponse;
import com.dolphindoors.resource.utilities.Msg;
import com.saloon.dto.LookupItem;
import com.saloon.enums.ClientSource;
import com.saloon.enums.EnumResolver;
import com.saloon.enums.Status;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author richardnarh
 */
@Path("v1/lookups")
public class LookupController {
    @GET
    @Path("/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response status(){
        return JaxResponse.ok(Msg.RECORD_FOUND, PrepareEnum(Status.values()));
    }
    
    @GET
    @Path("/client-source")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clientSource(){
        return JaxResponse.ok(Msg.RECORD_FOUND, PrepareEnum(ClientSource.values()));
    }
    
    public static <T extends Enum<T> & EnumResolver> List<LookupItem> PrepareEnum(T[] values){
        return Arrays.stream(values)
                .map(v -> new LookupItem(v.getValue(), v.getLabel()))
                .collect(Collectors.toList());
    }
     
}
