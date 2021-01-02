package asr.proyectoFinal.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import asr.proyectoFinal.dao.CloudantPalabraStore;
import asr.proyectoFinal.dominio.Quiz;
import asr.proyectoFinal.services.Trivial;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/quiz" })
public class QuizController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //CloudantPalabraStore store = new CloudantPalabraStore();
    System.out.println(request.getServletPath());

    Quiz quiz = Trivial.getTrivial(10, Trivial.MATHEMATICS, Trivial.HARD, Trivial.MULTIPLE);
    request.setAttribute("quiz", quiz);
    request.getRequestDispatcher("/quiz.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
