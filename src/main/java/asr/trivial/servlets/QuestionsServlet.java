package asr.trivial.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.SelectedLanguage;

import asr.trivial.services.Translator;

public class QuestionsServlet extends HttpServlet {

  private static final long serialVersionUID = 7134541682257697271L;

  private static final String SELECTED_LANGUAGE = "selectedLanguage";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Quiz quiz = (Quiz) request.getSession(true).getAttribute("quiz");

    if (quiz == null) {
      try {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      String language = request.getParameter("language");

      if (language == null) {
        language = SelectedLanguage.ENGLISH.getValue();
      }

      if (SelectedLanguage.isValid(language)) {
        Translator.translate(quiz, language);
        request.getSession().setAttribute(SELECTED_LANGUAGE,
            SelectedLanguage.getSelectedLanguage(language));
      }

      JsonObject quizJson = null;

      try {
        quizJson = (JsonObject) JsonParser.parseString(new Gson().toJson(quiz));
      } catch (JsonSyntaxException e) {
        e.printStackTrace();
      }

      if (quizJson != null) {
        JsonArray questionsJson = quizJson.getAsJsonArray("questions");
        questionsJson.forEach(e -> e.getAsJsonObject().remove("correctAnswer"));

        try {
          response.getWriter().print(questionsJson.toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
