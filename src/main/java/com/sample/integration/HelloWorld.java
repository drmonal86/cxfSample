package com.sample.integration;

import java.util.concurrent.TimeUnit;

import com.example.data.StoreOrder;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
 

@Path("/hello")
@Component("HelloWorld")
public class HelloWorld {

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    @Async
    public String ping(@PathParam("input") String input) {
    	
    	System.out.println("Before");
        try {
        	Thread.sleep(10000);  
        } catch (InterruptedException ie) {

        }
        System.out.println("Out of sleep");
      
        return input;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/jsonBean")
    public Response modifyJson(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }

    @POST
    @Produces("application/xml")
    @Consumes("application/xml")
    @Path("/order")
    public Response storeOrder(StoreOrder order) {

        return Response.ok().build();
    }
}

