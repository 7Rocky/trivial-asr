package asr.trivial.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asr.trivial.dao.UserDao;

import asr.trivial.domain.Quiz;
import asr.trivial.domain.User;

@WebServlet(urlPatterns="/results")
public class ResultsServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Quiz quiz = (Quiz) request.getSession().getAttribute("quiz");
    int correctlyAnswered = 0;

    for (int i = 0; i < quiz.getQuestions().size(); i++) {
      String selectedAnswer = request.getParameter(new StringBuilder("question ").append(i + 1).toString());
      String correctAnswer = quiz.getQuestions().get(i).getCorrectAnswer();

      if (correctAnswer.equals(selectedAnswer)) {
        correctlyAnswered++;
      }
    }

    UserDao store = new UserDao();

    if (request.getRemoteUser() != null) {
      String sub = ((User) request.getSession().getAttribute("user")).getSub();
      User user = store.getBySub(sub);

      user.updateStats(quiz.getCategory(), correctlyAnswered);
      store.update(user.get_id(), user);
    }

    User allUsers = store.getBySub(User.ALL_USERS_SUB);
    allUsers.updateStats(quiz.getCategory(), correctlyAnswered);
    store.update(allUsers.get_id(), allUsers);

    request.getSession().setAttribute("correctlyAnswered", correctlyAnswered);

    request.getRequestDispatcher("/results.jsp").forward(request, response);
  }

}
