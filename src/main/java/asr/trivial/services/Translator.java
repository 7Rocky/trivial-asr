package asr.trivial.services;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

import asr.trivial.dao.VCAPHelper;
import asr.trivial.domain.Quiz;
import asr.trivial.domain.enums.SelectedLanguage;

public class Translator {

  private static String apikey = "";

  public static String getApikey() {
    if (Translator.apikey.length() == 0) {
      Translator.apikey = VCAPHelper.getProperty("language_translator", "apikey");
    }

    return Translator.apikey;
  }

  public static void translate(Quiz quiz, final String language) {
    quiz.getQuestions().stream().forEach(question -> {
      question.setQuestion(translate(question.getQuestion(), language));
      question.setCorrectAnswer(translate(question.getCorrectAnswer(), language));

      Collection<String> newAnswers = question.getAnswers().stream()
                                        .map(answer -> translate(answer, language))
                                        .collect(Collectors.toCollection(TreeSet::new));
      question.setAnswers(newAnswers);
    });
  }

  public static String translate(String text, String language) {
    if (language.equals(SelectedLanguage.ENGLISH.getValue())) {
      return text;
    }

    Authenticator authenticator = new IamAuthenticator(Translator.getApikey());
    LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);

    languageTranslator.setServiceUrl("https://gateway-lon.watsonplatform.net/language-translator/api");

    TranslateOptions translateOptions = new TranslateOptions.Builder()
      .addText(text)
      .source(SelectedLanguage.ENGLISH.getValue())
      .target(language)
      .build();

    TranslationResult translationResult = languageTranslator.translate(translateOptions).execute().getResult();

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
