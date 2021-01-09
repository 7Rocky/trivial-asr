package asr.trivial.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import asr.trivial.domain.Question;
import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;
import asr.trivial.domain.enums.SelectedLanguage;

public class Trivial {

  private static final String API_URL = "https://opentdb.com/api.php";

  private Trivial() {
    throw new IllegalStateException("Utility class");
  }

  private static String queryString(int category, String difficulty) {
    return new StringBuilder("?").append("amount=").append(Quiz.AMOUNT).append("&")
        .append("category=").append(category).append("&").append("dificulty").append(difficulty)
        .append("&").append("type=").append(Quiz.TYPE).toString();
  }

  public static Quiz getTrivial(int category, String difficulty) {
    Quiz quiz = new Quiz(Category.getCategory(category), Difficulty.getDifficulty(difficulty));
    quiz.setSelectedLanguage(SelectedLanguage.ENGLISH);

    String requestUrl =
        new StringBuilder(API_URL).append(queryString(category, difficulty)).toString();

    HttpURLConnection connection = null;

    try {
      URL url = new URL(requestUrl);

      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");

      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        Trivial.setQuiz(quiz, connection.getInputStream());
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    return quiz;
  }

  private static void setQuiz(Quiz quiz, InputStream inputStream) {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      JsonObject response = JsonParser.parseString(bufferedReader.readLine()).getAsJsonObject();
      JsonArray results = response.getAsJsonArray("results");

      Iterator<JsonElement> questionsIt = results.iterator();

      while (questionsIt.hasNext()) {
        JsonObject jsonQuestion = questionsIt.next().getAsJsonObject();

        String question = jsonQuestion.get("question").getAsString();
        String correctAnswer = jsonQuestion.get("correct_answer").getAsString();

        List<String> answers = new ArrayList<>();

        Iterator<JsonElement> answersIt =
            jsonQuestion.getAsJsonArray("incorrect_answers").iterator();

        while (answersIt.hasNext()) {
          answers.add(answersIt.next().getAsString());
        }

        quiz.addQuestion(new Question(question, correctAnswer, answers));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
