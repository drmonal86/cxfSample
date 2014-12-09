package com.sample.integration;

import com.example.data.StoreOrder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Properties;
 

@Path("/hello")
@Component("HelloWorld")
public class HelloWorld {

    private Properties defaultProperties;

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    @Async
    public String ping(@PathParam("input") String input) {
    	String serviceName = defaultProperties.getProperty("cxfsample.service.name");
    	System.out.println("Before property is [" + serviceName + "]");
//        try {
//        	Thread.sleep(10000);
//        } catch (InterruptedException ie) {
//
//        }
//        System.out.println("Out of sleep");
      
        return input + " " + serviceName;
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
    public void setDefaultProperties(Properties defaultProperties)
    {
        this.defaultProperties = defaultProperties;
    }

    public Properties getDefaultProperties()
    {
        return defaultProperties;
    }
}

