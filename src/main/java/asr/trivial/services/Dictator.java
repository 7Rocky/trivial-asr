package asr.trivial.services;

import java.io.IOException;
import java.io.InputStream;

import com.google.gson.JsonObject;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;

import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;

import asr.trivial.dao.VCAPHelper;

public class Dictator {

  private static String apikey = "";
  private static String url = "";
  
  public static String getApikey() {
    if (Dictator.apikey.length() == 0) {
      Dictator.apikey = createClient()[0];
    }

    return Dictator.apikey;
  }
  
  public static String getUrl() {
    if (Dictator.url.length() == 0) {
      Dictator.url = createClient()[1];
    }

    return Dictator.url;
  }
  
  private static String[] createClient() {
    String apikey;
    String url;

    if (System.getenv("VCAP_SERVICES") != null) {
      JsonObject watsonCredentials = VCAPHelper.getCloudCredentials("text_to_speech");

      if (watsonCredentials == null) {
        System.out.println("No language_translator service bound to this application");
        return null;
      }

      apikey = watsonCredentials.get("apikey").getAsString();
      url = watsonCredentials.get("url").getAsString();
    } else {
      System.out.println("Running locally. Looking for credentials in text_to_speech.properties");
      apikey = VCAPHelper.getLocalProperties("text_to_speech.properties").getProperty("text_to_speech_apikey");
      url = VCAPHelper.getLocalProperties("text_to_speech.properties").getProperty("text_to_speech_url");

      if (apikey == null || apikey.length() == 0 || url == null || url.length() == 0) {
        System.out.println("To use a text_to_speech, set the Watson apikey and url in src/main/resources/text_to_speech.properties");
        return null;
      }
    }

    return new String[] { apikey, url };
  }

  public static byte[] getAudio(String text, String language) {
    IamAuthenticator authenticator = new IamAuthenticator(Dictator.getApikey());
    TextToSpeech textToSpeech = new TextToSpeech(authenticator);
    textToSpeech.setServiceUrl(Dictator.getUrl());
    
    byte[] bytes = null;
    
    SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
      .text(text)
      .accept("audio/mp3")
      .voice(language)
      .build();

    InputStream inputStream = textToSpeech.synthesize(synthesizeOptions).execute().getResult();

    try {
      bytes = WaveUtils.toByteArray(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    return bytes;
  }

}
