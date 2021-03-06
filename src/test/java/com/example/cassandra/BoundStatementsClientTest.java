package com.example.cassandra;

import java.nio.ByteBuffer;
import java.util.List;

import com.datastax.driver.core.BoundStatement;


import com.datastax.driver.core.ResultSet;

import org.junit.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 */

public class BoundStatementsClientTest
{
  public static final String CASSANDRA_IP = "172.28.65.97";
  public static final String CASSANDRA_CLUSTER_NAME = "demo_1node";
  @BeforeClass
  public static void setUpClass() throws Exception
  {
  }

  @AfterClass
  public static void tearDownClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Ignore
  @Test
  public void exercisesBoundStatementsClient()
  {
    BoundStatementsClient boundStatementsClient = new BoundStatementsClient();
    boundStatementsClient.connect(CASSANDRA_IP);
    boundStatementsClient.createSchema();
    boundStatementsClient.loadData();
    ResultSet resultSet = boundStatementsClient.querySchema();
    assertThat(resultSet, notNullValue());
    boundStatementsClient.close();
  }


  @Test
  public void getListClient(){
	  	BoundStatementsClient boundStatementsClient = new BoundStatementsClient();
	    boundStatementsClient.connect(CASSANDRA_IP);
	  //  boundStatementsClient.insertIntoTable();
	    ResultSet dataOut = boundStatementsClient.getTest();
	    
	    boundStatementsClient.close();
  }
}