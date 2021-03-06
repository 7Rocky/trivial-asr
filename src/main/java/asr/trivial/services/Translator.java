package asr.trivial.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;

import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

import java.util.Collection;
import java.util.TreeSet;

import java.util.stream.Collectors;

import asr.trivial.dao.VCAPHelper;

import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.SelectedLanguage;

public class Translator {

  private static String apikey = "";
  private static String url = "";

  public static String getApikey() {
    if (Translator.apikey.length() == 0) {
      Translator.apikey = VCAPHelper.getProperty("language_translator", "apikey");
    }

    return Translator.apikey;
  }

  public static String getUrl() {
    if (Translator.url.length() == 0) {
      Translator.url = VCAPHelper.getProperty("language_translator", "url");
    }

    return Translator.url;
  }

  public static void translate(Quiz quiz, final String toLanguage) {
    String fromLanguage = quiz.getSelectedLanguage().getValue();

    if (fromLanguage.equals(toLanguage)) {
      return;
    }

    quiz.getQuestions().stream().forEach(question -> {
      question.setQuestionTitle(translate(question.getQuestionTitle(), fromLanguage, toLanguage));
      question.setCorrectAnswer(translate(question.getCorrectAnswer(), fromLanguage, toLanguage));

      Collection<String> newAnswers =
          question.getAnswers().stream().map(answer -> translate(answer, fromLanguage, toLanguage))
              .collect(Collectors.toCollection(TreeSet::new));
      question.setAnswers(newAnswers);
    });

    quiz.setSelectedLanguage(SelectedLanguage.getSelectedLanguage(toLanguage));
  }

  public static String translate(String text, String fromLanguage, String toLanguage) {
    Authenticator authenticator = new IamAuthenticator(Translator.getApikey());
    LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);

    languageTranslator.setServiceUrl(Translator.getUrl());

    text = text.replace("&quot;", "\"").replace("&#034;", "\"").replace("&#039;", "'");

    TranslateOptions translateOptions = new TranslateOptions.Builder().addText(text)
        .source(fromLanguage).target(toLanguage).build();

    TranslationResult translationResult =
        languageTranslator.translate(translateOptions).execute().getResult();

    String translationJSON = translationResult.toString();
    JsonObject rootObj = JsonParser.parseString(translationJSON).getAsJsonObject();
    JsonArray translations = rootObj.getAsJsonArray("translations");
    String translation = text;

    if (translations.size() > 0) {
      translation = translations.get(0).getAsJsonObject().get("translation").getAsString();
    }

    translation =
        translation.replace("\"", "&quot;").replace("\"", "&#034;").replace("'", "&#039;");

    return translation;
  }

}
