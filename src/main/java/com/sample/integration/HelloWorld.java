package com.sample.integration;

import com.example.data.StoreOrder;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorld {

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        System.out.println("Into Service HelloWorld");
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

