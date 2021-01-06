package asr.trivial.servlets;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import asr.trivial.dao.UserDao;

import asr.trivial.domain.User;

@WebServlet(urlPatterns="/stats")
public class StatsServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println(request.getServletPath());

    System.out.println(request.getRemoteUser());
    UserDao store = new UserDao();

    User user = null;

    if (request.getRemoteUser() != null) {
      user = store.getBySub(request.getRemoteUser());
    } else {
      user = store.getBySub(User.ALL_USERS_SUB);
    }

    request.getSession(true).setAttribute("user", user);
    request.getRequestDispatcher("/stats.jsp").forward(request, response);
    //System.out.println(store.get("ce2c95de1d0aef3b8e09e7aed01ca028"));
    //System.out.println(store.get("d3c3732e9ae04f3c90044640cb04556c"));
    //System.out.println(store.persist(new User("Test3", "Test2 Test3", "sub3", "test3@test.com", "http://test3.jpg")));
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
