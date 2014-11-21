package com.connecture.integration;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelloWorldIT {
    private static String endpointUrl;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }
    
   
    @Test
    public void testPing() throws Exception {
//        WebClient client = WebClient.create(endpointUrl + "/hello/echo/SierraTangoNevada");
     /* Client client = WebClient.create("http://localhost:8080" + "/hello/echo/SierraTangoNevada");
        Response r = client.accept("text/plain").get();*/
    	Client client = ClientBuilder.newBuilder().newClient();
    	WebTarget target = client.target("http://localhost:8080" + "/cxfsample/hello/echo/SierraTangoNevada");
    	//target = target.path("service").queryParam("a", "avalue");
    	 
    	Invocation.Builder builder = target.request();
    	Response r = builder.get();
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        assertEquals("SierraTangoNevada", value);
    }

   @Ignore
    @Test
    public void testJsonRoundtrip() throws Exception {
        List<Object> providers = new ArrayList<Object>();
        providers.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
        JsonBean inputBean = new JsonBean();
        inputBean.setVal1("Maple");
        WebClient client = WebClient.create("http://localhost:8080" + "/cxfsample/hello/jsonBean", providers);
        Response r = client.accept("application/json")
            .type("application/json")
            .post(inputBean);
        assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        MappingJsonFactory factory = new MappingJsonFactory();
        JsonParser parser = factory.createJsonParser((InputStream)r.getEntity());
        JsonBean output = parser.readValueAs(JsonBean.class);
        assertEquals("Maple", output.getVal2());
    }
}
