package asr.trivial.servlets;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.dao.UserDao;

import asr.trivial.domain.Quiz;
import asr.trivial.domain.User;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;
import asr.trivial.domain.enums.SelectedLanguage;

import asr.trivial.services.Dictator;
import asr.trivial.services.Translator;
import asr.trivial.services.Trivial;

@WebServlet(urlPatterns={ "/audio", "/quiz" })
public class QuizServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    UserDao store = new UserDao();
    Quiz quiz = null;

    switch (request.getServletPath()) {
    case "/quiz":
      int category = Integer.parseInt(request.getParameter("category"));
      String difficulty = request.getParameter("difficulty");

      if (Category.isValid(category) && Difficulty.isValid(difficulty)) {
        quiz = Trivial.getTrivial(category, difficulty);

        String language = request.getParameter("language");

        if (language == null) {
          language = SelectedLanguage.ENGLISH.getValue();
        }

        if (SelectedLanguage.isValid(language)) {
          Translator.translate(quiz, language);
        }

        request.getSession(true).setAttribute("quiz", quiz);
        request.getSession().setAttribute("selectedLanguage", SelectedLanguage.getSelectedLanguage(language));

        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
      } else {
        response.sendError(400, "Bad URL query parameters for Quiz");
      }

      break;

    case "/audio":
      quiz = (Quiz) request.getSession().getAttribute("quiz");
      SelectedLanguage selectedLanguage = (SelectedLanguage) request.getSession().getAttribute("selectedLanguage");

      int questionId = Integer.parseInt(request.getParameter("q"));
      String question = quiz.getQuestions().get(questionId).getQuestion();

      byte[] bytes = Dictator.getAudio(question, selectedLanguage.getVoice());

      response.getOutputStream().write(bytes);

      break;
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
