package asr.trivial.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import asr.trivial.dao.CloudantPalabraStore;
import asr.trivial.domain.Palabra;
import asr.trivial.domain.enums.SelectedLanguage;
import asr.trivial.services.Translator;

/**
 * Servlet implementation class Controller
 */
@WebServlet(
  urlPatterns = {
    "/listar", "/insertar"
  }
)
public class Controller extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    out.println("<html><head><meta charset=\"UTF-8\"></head><body>");

    CloudantPalabraStore store = new CloudantPalabraStore();
    System.out.println(request.getServletPath());

    switch (request.getServletPath()) {
      case "/listar":
        if (store.getDB() == null) {
          out.println("No hay DB");
        } else {
          out.println("Palabras en la BD Cloudant:<br />" + store.getAll());
        }

        break;

      case "/insertar":
        Palabra palabra = new Palabra();
        String parametro = request.getParameter("palabra");

        if (parametro == null) {
          out.println("usage: /insertar?palabra=palabra_a_traducir");
        } else {
          if (store.getDB() == null) {
            out.println(String.format("Palabra: %s", palabra));
          } else {
            palabra.setName(Translator.translate(parametro, SelectedLanguage.FRENCH.getValue()));
            store.persist(palabra);
            out.println(String.format("Almacenada la palabra: %s", palabra.getName()));
          }
        }

        break;
    }
    
    out.println("</html>");
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }

}
