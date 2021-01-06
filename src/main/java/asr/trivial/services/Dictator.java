package asr.trivial.services;

import java.io.IOException;
import java.io.InputStream;

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
      Dictator.apikey = VCAPHelper.getProperty("text_to_speech", "apikey");
    }

    return Dictator.apikey;
  }

  public static String getUrl() {
    if (Dictator.url.length() == 0) {
      Dictator.url = VCAPHelper.getProperty("text_to_speech", "url");
    }

    return Dictator.url;
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
