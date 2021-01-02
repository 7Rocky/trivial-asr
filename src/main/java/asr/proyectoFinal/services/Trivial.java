package asr.proyectoFinal.services;

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

import asr.proyectoFinal.dominio.Question;
import asr.proyectoFinal.dominio.Quiz;

public class Trivial {
  
  public static final int GENERAL_KNOWLEDGE =  9;
  public static final int BOOKS = 10;
  public static final int FILM = 11;
  public static final int MUSIC = 12;
  public static final int MUSICALS_AND_THEATRES = 13;
  public static final int TELEVISION = 14;
  public static final int VIDEO_GAMES = 15;
  public static final int BOARD_GAMES = 16;
  public static final int SCIENCE_AND_NATURE = 17;
  public static final int COMPUTERS = 18;
  public static final int MATHEMATICS = 19;
  public static final int MYTHOLOGY = 20;
  public static final int SPORTS = 21;
  public static final int GEOGRAPHY = 22;
  public static final int HISTORY = 23;
  public static final int POLITICS = 24;
  public static final int ART = 25;
  public static final int CELEBRITIES = 26;
  public static final int ANIMALS = 27;
  public static final int VEHICLES = 28;
  public static final int COMICS = 29;
  public static final int GADGETS = 30;
  public static final int ANIME_AND_MANGA = 31;
  public static final int CARTOON_AND_ANIMATIONS = 32;
  
  public static final String EASY = "easy";
  public static final String MEDIUM = "medium";
  public static final String HARD = "hard";
  
  public static final String MULTIPLE = "multiple";
  public static final String TRUE_FALSE = "boolean";
  
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
        answers.add(correctAnswer);
        
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
