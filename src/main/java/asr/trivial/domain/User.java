package asr.trivial.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asr.trivial.domain.enums.Category;

public class User {

  public static final String ALL_USERS_SUB = "all_users_sub";

  private String _id;
  private String _rev;

  private String givenName;
  private String familyName;
  private String sub;
  private String email;
  private String picture;
  private Map<String, List<Integer>> stats = new HashMap<>();

  public User(String givenName, String familyName, String sub, String email, String picture) {
    this.setGivenName(givenName);
    this.setFamilyName(familyName);
    this.setSub(sub);
    this.setEmail(email);
    this.setPicture(picture);
    this.initStats();
  }

  @Override
  public String toString() {
    return new StringBuilder("Name: ").append(givenName).append("\n")
      .append("Family name: ").append(familyName).append("\n")
      .append("Email: ").append(email).append("\n")
      .append("sub: ").append(sub).append("\n")
      .append("Stats: ").append(stats).append("\n").toString();
  }

  private void initStats() {
    for (Category category : Category.values()) {
      List<Integer> results = new ArrayList<>(2);
      results.add(0);
      results.add(0);
      stats.put(category.getText(), results);
    }
  }

  public void updateStats(Category category, int correctAnswers) {
    List<Integer> categoryStats = stats.get(category.getText());
    categoryStats.set(0, categoryStats.get(0) + correctAnswers);
    categoryStats.set(1, categoryStats.get(1) + Quiz.AMOUNT);
  }

  public String get_id() {
    return _id;
  }

  public String get_rev() {
    return _rev;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public String getSub() {
    return sub;
  }

  public String getEmail() {
    return email;
  }

  public String getPicture() {
    return picture;
  }

  public Map<String, List<Integer>> getStats() {
    return stats;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public void set_rev(String _rev) {
    this._rev = _rev;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public void setSub(String sub) {
    this.sub = sub;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public void setStats(Map<String, List<Integer>> stats) {
    this.stats = stats;
  }

}
