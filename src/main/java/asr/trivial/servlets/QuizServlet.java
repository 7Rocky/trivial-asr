package asr.trivial.servlets;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.Category;
import asr.trivial.domain.enums.Difficulty;
import asr.trivial.domain.enums.SelectedLanguage;

import asr.trivial.services.Trivial;

public class QuizServlet extends HttpServlet {

  private static final long serialVersionUID = 2139741182050617922L;

  private static final String SELECTED_LANGUAGE = "selectedLanguage";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Quiz quiz = null;

    try {
      int category = request.getParameter("category") == null ? -1
          : Integer.parseInt(request.getParameter("category"));

      String difficulty = request.getParameter("difficulty");

      if (Category.isValid(category) && Difficulty.isValid(difficulty)) {
        quiz = Trivial.getTrivial(category, difficulty);

        request.getSession(true).setAttribute("quiz", quiz);
        request.getSession().setAttribute(SELECTED_LANGUAGE, SelectedLanguage.ENGLISH);

        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
      } else {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
      }
    } catch (IOException | ServletException e) {
      e.printStackTrace();
    }
  }

}
