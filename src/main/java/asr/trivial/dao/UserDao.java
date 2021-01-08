package asr.trivial.dao;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.io.IOException;

import java.net.URL;

import java.util.Collection;
import java.util.List;

import asr.trivial.domain.User;

public class UserDao {

  private Database db = null;
  private static final String databaseName = VCAPHelper.isProdEnv() ? "users" : "users-test";

  public UserDao() {
    CloudantClient cloudant = createClient();

    if (cloudant != null) {
      db = cloudant.database(databaseName, true);
    }
  }

  public Database getDB() {
    return db;
  }

  private static CloudantClient createClient() {
    String url = VCAPHelper.getProperty("cloudant", "url");
    CloudantClient client = null;

    try {
      client = ClientBuilder.url(new URL(url)).build();
    } catch (Exception e) {
      System.out.println("Unable to connect to database");
    }

    return client;
  }

  public Collection<User> getAll() {
    List<User> docs = null;

    try {
      docs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(User.class);
    } catch (IOException e) { }

    return docs;
  }

  public User get(String id) {
    return db.find(User.class, id);
  }
  
  public User getBySub(String sub) {
    List<User> users = db.query(
        new StringBuilder("{\"selector\":{\"sub\":\"").append(sub).append("\"}}").toString(),
        User.class
      )
      .getDocs();

    if (users.isEmpty()) {
      return null;
    }

    return users.get(0);
  }

  public User persist(User td) {
    String id = db.save(td).getId();

    return db.find(User.class, id);
  }

  public User update(String id, User newUser) {
    User visitor = db.find(User.class, id);
    visitor.setStats(newUser.getStats());
    db.update(visitor);

    return db.find(User.class, id);
  }

}
