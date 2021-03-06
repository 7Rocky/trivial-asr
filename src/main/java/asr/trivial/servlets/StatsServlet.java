package asr.trivial.servlets;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.dao.UserDao;

import asr.trivial.domain.User;

public class StatsServlet extends HttpServlet {

  private static final long serialVersionUID = -6027760521556652184L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    UserDao store = new UserDao();
    User user = null;

    if (request.getRemoteUser() != null) {
      user = store.getBySub(request.getRemoteUser());
    } else {
      user = store.getBySub(User.ALL_USERS_SUB);
    }

    request.getSession(true).setAttribute("user", user);

    try {
      request.getRequestDispatcher("/stats.jsp").forward(request, response);
    } catch (IOException | ServletException e) {
      e.printStackTrace();
    }
  }

}
