package com.example.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.utils.Bytes;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 */
public class BoundStatementsClient extends SimpleClient
{
  public void loadData()
  {
    PreparedStatement statement = getSession().prepare(
            "INSERT INTO testcassandra.songs " +
                    "(id, title, album, artist, tags) " +
                    "VALUES (?, ?, ?, ?, ?);");

    BoundStatement boundStatement = new BoundStatement(statement);
    Set<String> tags = new HashSet<String>();
    tags.add("jazz");
    tags.add("2013");
    getSession().execute(boundStatement.bind(
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
            "La Petite Tonkinoise",
            "Bye Bye Blackbird",
            "Joséphine Baker",
            tags ) );

    tags = new HashSet<String>();
    tags.add("rock");
    tags.add("2013");
    getSession().execute(boundStatement.bind(
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf51"),
            "The Enemy Inside",
            "Dream Theater",
            "Dream Theater",
            tags ) );

    tags = new HashSet<String>();
    tags.add("progressive rock");
    tags.add("2013");
    getSession().execute(boundStatement.bind(
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf52"),
            "All Time Low",
            "Hesitation Marks",
            "Nine Inch Nails",
            tags ) );

    // Add code to create a new bound statement for inserting data into the simplex.playlists table.
    statement = getSession().prepare(
            "INSERT INTO testcassandra.playlists " +
                    "(id, song_id, title, album, artist) " +
                    "VALUES (?, ?, ?, ?, ?);");
    boundStatement = new BoundStatement(statement);
    getSession().execute(boundStatement.bind(
            UUID.fromString("2cc9ccb7-6221-4ccb-8387-f22b6a1b354d"),
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf50"),
            "La Petite Tonkinoise",
            "Bye Bye Blackbird",
            "Joséphine Baker") );

    getSession().execute(boundStatement.bind(
            UUID.fromString("2cc9ccb7-6221-4ccb-8387-f22b6a1b354d"),
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf51"),
            "The Enemy Inside",
            "Dream Theater",
            "Dream Theater") );

    getSession().execute(boundStatement.bind(
            UUID.fromString("2cc9ccb7-6221-4ccb-8387-f22b6a1b354d"),
            UUID.fromString("756716f7-2e54-4715-9f00-91dcbea6cf52"),
            "All Time Low",
            "Hesitation Marks",
            "Nine Inch Nails") );

  }
  
  
  public void insertIntoTable() {
	  byte[] dataIn = new byte[]{1,2,3};
      PreparedStatement statement = getSession().prepare("INSERT INTO mdoctor.test_table (id,data) VALUES (?, ?)");
      BoundStatement boundStatement = new BoundStatement(statement);
      getSession().execute(boundStatement.bind(123,ByteBuffer.wrap(dataIn)));
  }
  
  public byte[] readFromTable() {
      String q1 = "SELECT * FROM mdoctor.test_table WHERE id = 123;";

      ResultSet results = getSession().execute(q1);
      for (Row row : results) {
          ByteBuffer data = row.getBytes("data");
           byte[] result = new byte[data.remaining()];
            data.get(result);
            System.out.println(result[1]);
            return result;
      }
      return null;
  }

  
  public ResultSet getTest(){
      ResultSet resultSet = getSession().execute("SELECT * FROM mdoctor.test_by_score");
      {  
        	ByteBuffer bb = resultSet.one().getBytes("test");
           	byte[] data = new byte[bb.remaining()];
           bb.get(data);
           String s1= new String(data);
        	System.out.println("Data:" +s1);
        	 try {
        		 JSONObject jsonObj = new JSONObject(s1);
				 String value = (String) jsonObj.get("prefix");
				 System.out.println(value);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
        return null;
  }
}
