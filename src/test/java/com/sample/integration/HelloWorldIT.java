package com.sample.integration;

import com.example.data.StoreOrder;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.scheduling.annotation.Async;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HelloWorldIT {
    private static String endpointUrl;

    @BeforeClass
    public static void beforeClass() {
        endpointUrl = System.getProperty("service.url");
    }
    
    @Ignore 
    @Test
    public void testPing() throws Exception {
    	Client client = ClientBuilder.newBuilder().newClient();
    	WebTarget target = client.target("http://localhost:8080" + "/cxfsample/hello/echo/SierraTangoNevada");
    	//target = target.path("service").queryParam("a", "avalue");
    	 
    	Invocation.Builder builder = target.request();
    	Response r = builder.get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        String value = IOUtils.toString((InputStream)r.getEntity());
        Assert.assertEquals("SierraTangoNevada", value);
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
        Assert.assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
        MappingJsonFactory factory = new MappingJsonFactory();
        JsonParser parser = factory.createJsonParser((InputStream)r.getEntity());
        JsonBean output = parser.readValueAs(JsonBean.class);
        Assert.assertEquals("Maple", output.getVal2());
    }


    @Ignore
    @Test
    public void testAsyncGet() throws Exception
    {
        Client client = ClientBuilder.newBuilder().newClient();
        WebTarget target = client.target("http://localhost:8080" + "/cxfsample/hello/echo/SierraTangoNevada");
        Future myFuture = target.request().async().get();

        // Block until done
        try {
            String myResponse = (String) myFuture.get(5, TimeUnit.SECONDS);

        } catch (TimeoutException | InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

  @Ignore
  @Test
  public void storesOrderAndReturnsSuccess()  throws Exception
  {
    Client client = ClientBuilder.newBuilder().newClient();
    WebTarget target = client.target("http://localhost:8080" + "/cxfsample/hello/order");
    StoreOrder order = new StoreOrder();
    order.setCustomerName("Jane Doe");
    order.setItemName("Widget");
    order.setOrderId(1234);
    order.setQuantity(2);
    order.setShippingAddress("123 E. West Ave");
    Invocation.Builder builder = target.request(MediaType.APPLICATION_XML);
    Response r = builder.post(Entity.xml(order));
    Assert.assertEquals(Response.Status.OK.getStatusCode(), r.getStatus());
  }

   @Ignore
   @Test
   public void callAsyncTest() throws Exception
   {
	    
	    String testReturn =  pingTest();
	    System.out.println(testReturn);
   }
   
   @Async 
   public String pingTest() throws Exception
   {
	   Client client = ClientBuilder.newBuilder().newClient();
   		WebTarget target = client.target("http://localhost:8080" + "/cxfsample/hello/echo/SierraTangoNevada");
   	//target = target.path("service").queryParam("a", "avalue");
   
   	Invocation.Builder builder = target.request();
   	Response r = builder.get();
   	String value = IOUtils.toString((InputStream)r.getEntity());
   	return value;
   }
    
   @Async
    public Boolean asyncHttpClient() throws Exception{
    	 RequestConfig requestConfig = RequestConfig.custom()
    	            .setSocketTimeout(20000)
    	            .setConnectTimeout(20000).build();
    	        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
    	            .setDefaultRequestConfig(requestConfig)
    	            .build();
    	        try {
    	            httpclient.start();
    	            final HttpGet[] requests = new HttpGet[] {
    	                    new HttpGet("http://localhost:8080" + "/cxfsample/hello/echo/SierraTangoNevada"),
    	                    new HttpGet("https://www.verisign.com/"),
    	                    new HttpGet("http://www.google.com/")
    	            };
    	            final CountDownLatch latch = new CountDownLatch(requests.length);
    	            for (final HttpGet request: requests) {
    	                httpclient.execute(request, new FutureCallback<HttpResponse>() {

    	                    public void completed(final HttpResponse response) {
    	                        latch.countDown();
    	                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
    	                    }

    	                    public void failed(final Exception ex) {
    	                        latch.countDown();
    	                        System.out.println(request.getRequestLine() + "->" + ex);
    	                    }

    	                    public void cancelled() {
    	                        latch.countDown();
    	                        System.out.println(request.getRequestLine() + " cancelled");
    	                    }

    	                });
    	            }
    	            latch.await();
    	            System.out.println("Shutting down");
    	        } finally {
    	            httpclient.close();
    	        }
    	        System.out.println("Done");
				return true;
    	

    	
    }
    

}
