package asr.proyectoFinal.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;

import com.ibm.watson.developer_cloud.language_translator.v3.util.Language;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

import asr.proyectoFinal.dao.VCAPHelper;

public class Traductor {
  
  public static final String ENGLISH = Language.ENGLISH;
  public static final String SPANISH = Language.SPANISH;
  public static final String FRENCH = Language.FRENCH;
  public static final String GERMAN = Language.GERMAN;
  
  private static String apikey = "";
  
  public static String getApikey() {
    if (Traductor.apikey.length() == 0) {
      Traductor.apikey = createClient();
    }

    return Traductor.apikey;
  }
  
  private static String createClient() {
    String apikey;

    if (System.getenv("VCAP_SERVICES") != null) {
      // When running in Bluemix, the VCAP_SERVICES env var will have the credentials
      // for all bound/connected services
      // Parse the VCAP JSON structure looking for cloudant.
      JsonObject watsonCredentials = VCAPHelper.getCloudCredentials("language_translator");

      if (watsonCredentials == null) {
        System.out.println("No language_translator service bound to this application");
        return null;
      }

      apikey = watsonCredentials.get("apikey").getAsString();
    } else {
      System.out.println("Running locally. Looking for credentials in watson.properties");
      apikey = VCAPHelper.getLocalProperties("watson.properties").getProperty("watson_apikey");

      if (apikey == null || apikey.length() == 0) {
        System.out.println("To use a language_translator, set the Watson apikey in src/main/resources/watson.properties");
        return null;
      }
    }

    return apikey;
  }

  public static String translate(String text, String language) {
    Authenticator authenticator = new IamAuthenticator(Traductor.getApikey());
    LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);

    languageTranslator.setServiceUrl("https://gateway-lon.watsonplatform.net/language-translator/api");

    TranslateOptions translateOptions = new TranslateOptions.Builder()
      .addText(text)
      .source(Language.ENGLISH)
      .target(language)
      .build();

    TranslationResult translationResult = languageTranslator.translate(translateOptions).execute().getResult();

    System.out.println(translationResult);

    String translationJSON = translationResult.toString();
    JsonObject rootObj = JsonParser.parseString(translationJSON).getAsJsonObject();
    JsonArray translations = rootObj.getAsJsonArray("translations");
    String translation = text;

    if (translations.size() > 0) {
      translation = translations.get(0).getAsJsonObject().get("translation").getAsString();
    }

    return translation;
  }

}
