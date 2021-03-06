package com.example.cassandra;

import com.datastax.driver.core.*;

/**
 */
public class SimpleClient
{
  private Cluster cluster;

  public Session getSession()
  {
    return session;
  }

  private Session session;

  public void connect(String node) {
    cluster = Cluster.builder()
            .addContactPoint(node)
            .build();
    Metadata metadata = cluster.getMetadata();
    System.out.printf("Connected to cluster: %s\n",
            metadata.getClusterName());
    for (Host host : metadata.getAllHosts()) {
      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
              host.getDatacenter(), host.getAddress(), host.getRack());
    }
    session = cluster.connect();
  }

  public void close() {
    cluster.close();
  }

  public void createSchema() {
    session.execute("CREATE KEYSPACE IF NOT EXISTS testCassandra WITH replication " +
          "= {'class':'SimpleStrategy', 'replication_factor':1};");

    session.execute(
            "CREATE TABLE IF NOT EXISTS testCassandra.songs (" +
                    "id uuid PRIMARY KEY," +
                    "title text," +
                    "album text," +
                    "artist text," +
                    "tags set<text>," +
                    "data blob" +
                    ");");

    session.execute(
            "CREATE TABLE IF NOT EXISTS testCassandra.playlists (" +
                    "id uuid," +
                    "title text," +
                    "album text, " +
                    "artist text," +
                    "song_id uuid," +
                    "PRIMARY KEY (id, title, album, artist)" +
                    ");");

  }

  public void loadData() {
    session.execute(
            "INSERT INTO testCassandra.songs (id, title, album, artist, tags) " +
                    "VALUES (" +
                    "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                    "'La Petite Tonkinoise'," +
                    "'Bye Bye Blackbird'," +
                    "'Joséphine Baker'," +
                    "{'jazz', '2013'})" +
                    ";");
    session.execute(
            "INSERT INTO testCassandra.playlists (id, song_id, title, album, artist) " +
                    "VALUES (" +
                    "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d," +
                    "756716f7-2e54-4715-9f00-91dcbea6cf50," +
                    "'La Petite Tonkinoise'," +
                    "'Bye Bye Blackbird'," +
                    "'Joséphine Baker'" +
                    ");");

  }

  public String getConnectedClusterName()
  {
    return cluster.getMetadata().getClusterName();
  }

  public ResultSet querySchema()
  {
    ResultSet results = session.execute("SELECT * FROM testCassandra.playlists " +
            "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");

    System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
            "-------------------------------+-----------------------+--------------------"));
    for (Row row : results) {
      System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"),
              row.getString("album"),  row.getString("artist")));
    }
    System.out.println();

    return results;
  }
}
