package asr.trivial.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.Set;

import java.util.Map.Entry;

public class VCAPHelper {

  private static final String VCAP_SERVICES = System.getenv("VCAP_SERVICES");

  private VCAPHelper() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isProdEnv() {
    return VCAP_SERVICES != null;
  }

  private static JsonObject getCloudCredentials(String serviceName) {
    JsonObject obj = (JsonObject) JsonParser.parseString(VCAP_SERVICES);
    Entry<String, JsonElement> dbEntry = null;
    Set<Entry<String, JsonElement>> entries = obj.entrySet();

    for (Entry<String, JsonElement> eachEntry : entries) {
      if (eachEntry.getKey().toLowerCase().contains(serviceName)) {
        dbEntry = eachEntry;
        break;
      }
    }

    if (dbEntry == null) {
      System.out.println("VCAP_SERVICES: Could not find " + serviceName);
      return null;
    }

    obj = (JsonObject) ((JsonArray) dbEntry.getValue()).get(0);
    System.out.println("VCAP_SERVICES: Found " + dbEntry.getKey());

    return (JsonObject) obj.get("credentials");
  }

  private static Properties getLocalProperties(String fileName) {
    Properties properties = new Properties();
    InputStream inputStream = VCAPHelper.class.getClassLoader().getResourceAsStream(fileName);

    try {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return properties;
  }

  public static String getProperty(String serviceName, String property) {
    String fileName = new StringBuilder(serviceName).append(".properties").toString();
    String value = "";

    if (VCAPHelper.isProdEnv()) {
      JsonObject watsonCredentials = VCAPHelper.getCloudCredentials(serviceName);

      if (watsonCredentials == null) {
        System.out.println("No " + serviceName + " service bound to this application");
        return "";
      }

      value = watsonCredentials.get(property).getAsString();
    } else {
      System.out.println("Running locally. Looking for credentials in " + fileName);
      value = VCAPHelper.getLocalProperties(fileName).getProperty(property);

      if (value == null || value.length() == 0) {
        System.out.println("To use a " + serviceName + ", set the " + property
            + " in src/main/resources/" + fileName);
        return "";
      }
    }

    return value;
  }

}
