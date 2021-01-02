package asr.trivial.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asr.trivial.dominio.Quiz;
import asr.trivial.services.Dictator;
import asr.trivial.services.Trivial;

@WebServlet(urlPatterns = { "/audio", "/quiz" })
public class QuizController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println(request.getServletPath());
    
    Quiz quiz = null;
    
    switch (request.getServletPath()) {
    case "/quiz":
      quiz = Trivial.getTrivial(10, Trivial.MATHEMATICS, Trivial.HARD, Trivial.MULTIPLE);

      request.getSession(true).setAttribute("quiz", quiz);
      request.getRequestDispatcher("/quiz.jsp").forward(request, response);

      break;

    case "/audio":
      quiz = (Quiz) request.getSession().getAttribute("quiz");

      int questionId = Integer.parseInt(request.getParameter("q"));
      String question = quiz.getQuestions().get(questionId).getQuestion();

      byte[] bytes = Dictator.getAudio(question, Dictator.ENGLISH);

      response.getOutputStream().write(bytes);

      break;
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
