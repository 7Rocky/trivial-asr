package asr.trivial.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asr.trivial.domain.Question;
import asr.trivial.domain.Quiz;

public class Trivial {
  
  private static final String API_URL = "https://opentdb.com/api.php";
  
  private static String queryString(int amount, int category, String difficulty, String type) {
    return new StringBuilder("?")
      .append("amount=").append(amount).append("&")
      .append("category=").append(category).append("&")
      .append("dificulty").append(difficulty).append("&")
      .append("type=").append(type).toString();
  }
  
  public static Quiz getTrivial(int amount, int category, String difficulty, String type) {
    Quiz quiz = new Quiz();
    String requestUrl = new StringBuilder(API_URL).append(queryString(amount, category, difficulty, type)).toString();
    HttpURLConnection conn = null;

    try {
      URL url = new URL(requestUrl);

      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
        throw new RuntimeException("Failed: HTTP Error code: " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      JsonObject response = JsonParser.parseString(br.readLine()).getAsJsonObject();
      JsonArray results = response.getAsJsonArray("results");
      
      Iterator<JsonElement> questionsIt = results.iterator();
      
      while (questionsIt.hasNext()) {
        JsonObject jsonQuestion = questionsIt.next().getAsJsonObject();

        String question = jsonQuestion.get("question").getAsString();
        String correctAnswer = jsonQuestion.get("correct_answer").getAsString();

        List<String> answers = new ArrayList<>();

        Iterator<JsonElement> answersIt = jsonQuestion.getAsJsonArray("incorrect_answers").iterator();
        
        while(answersIt.hasNext()) {
          answers.add(answersIt.next().getAsString());
        }

        quiz.addQuestion(new Question(question, correctAnswer, answers));
      }

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      conn.disconnect();
    }

    return quiz;
  }

}
