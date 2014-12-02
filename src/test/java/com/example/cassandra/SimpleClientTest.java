package com.example.cassandra;

import com.datastax.driver.core.ResultSet;
import org.junit.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
  */

public class SimpleClientTest
{

  //public static final String CASSANDRA_IP = "127.0.0.1";
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
  public void returnsClusterNameWhenValidNodeSupplied()
  {
    SimpleClient simpleClient = new SimpleClient();
    simpleClient.connect(CASSANDRA_IP);
    String clusterName = simpleClient.getConnectedClusterName();
    assertThat(clusterName, notNullValue());
    assertThat(clusterName, equalTo(CASSANDRA_CLUSTER_NAME));
  }

  @Ignore
  @Test
  public void returnsResultsFromQuery()
  {
    SimpleClient simpleClient = new SimpleClient();
    simpleClient.connect(CASSANDRA_IP);
   simpleClient.createSchema();
  simpleClient.loadData();
    ResultSet resultSet = simpleClient.querySchema();
    assertThat(resultSet, notNullValue());
    simpleClient.close();
  }
}