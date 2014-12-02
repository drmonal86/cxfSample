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
  
  public byte[] getTest(){

	  ByteBuffer blob=null;
      ResultSet rows = getSession().execute("SELECT * FROM mdoctor.test_by_score");
      byte[] results=null;
        while(!rows.isExhausted()){
    //  for(Row row: rows){   
    	 
    	        Row row= rows.one();
    	      blob= row.getBytes("test");
    	     results = Bytes.getArray(blob);
    	     // result = new byte[blob.remaining()];
    	   System.out.println(results);
       
    }
        return results;
  }
}
