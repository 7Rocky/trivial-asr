package asr.trivial.servlets;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.domain.Quiz;

import asr.trivial.domain.enums.SelectedLanguage;

import asr.trivial.services.Dictator;

public class AudioServlet extends HttpServlet {

  private static final long serialVersionUID = 677655926504884504L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Quiz quiz = (Quiz) request.getSession(true).getAttribute("quiz");
    SelectedLanguage selectedLanguage =
        (SelectedLanguage) request.getSession().getAttribute("selectedLanguage");

    int questionId = -1;

    try {
      questionId = Integer.parseInt(request.getParameter("q"));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    if (questionId != -1 && quiz != null) {
      String question = quiz.getQuestions().get(questionId).getQuestionTitle();

      byte[] bytes = Dictator.getAudio(question, selectedLanguage.getVoice());

      response.setHeader("Content-Type", "audio/ogg");

      try {
        response.getOutputStream().write(bytes);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
