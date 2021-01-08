package asr.trivial.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;
import asr.trivial.domain.enums.SelectedLanguage;

import asr.trivial.services.Dictator;
import asr.trivial.services.Translator;
import asr.trivial.services.Trivial;

@WebServlet(urlPatterns={ "/audio", "/quiz", "/questions" })
public class QuizServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Quiz quiz = null;

    switch (request.getServletPath()) {
    case "/quiz":
      int category = request.getParameter("category") == null ?
        -1 : Integer.parseInt(request.getParameter("category"));

      String difficulty = request.getParameter("difficulty");

      if (Category.isValid(category) && Difficulty.isValid(difficulty)) {
        quiz = Trivial.getTrivial(category, difficulty);

        request.getSession(true).setAttribute("quiz", quiz);
        request.getSession().setAttribute("selectedLanguage", SelectedLanguage.ENGLISH);

        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
      } else {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
      }

      break;

    case "/questions":
      quiz = (Quiz) request.getSession(true).getAttribute("quiz");

      if (quiz == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
      } else {
        String language = request.getParameter("language");

        if (language == null) {
          language = SelectedLanguage.ENGLISH.getValue();
        }

        if (SelectedLanguage.isValid(language)) {
          Translator.translate(quiz, language);
          request.getSession().setAttribute("selectedLanguage", SelectedLanguage.getSelectedLanguage(language));
        }

        JsonObject quizJson = (JsonObject) JsonParser.parseString(new Gson().toJson(quiz));
        JsonArray questionsJson = quizJson.getAsJsonArray("questions");
        questionsJson.forEach(e -> e.getAsJsonObject().remove("correctAnswer"));

        response.getWriter().print(questionsJson.toString());
      }

      break;

    case "/audio":
      quiz = (Quiz) request.getSession(true).getAttribute("quiz");
      SelectedLanguage selectedLanguage = (SelectedLanguage) request.getSession().getAttribute("selectedLanguage");

      int questionId = Integer.parseInt(request.getParameter("q"));
      String question = quiz.getQuestions().get(questionId).getQuestion();

      byte[] bytes = Dictator.getAudio(question, selectedLanguage.getVoice());

      response.setHeader("Content-Type", "audio/ogg");
      response.getOutputStream().write(bytes);

      break;
    }
  }

}
