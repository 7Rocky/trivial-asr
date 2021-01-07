package asr.trivial.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.websphere.security.WSSecurityException;

import com.ibm.websphere.security.auth.WSSubject;

import asr.trivial.dao.UserDao;
import asr.trivial.domain.User;

import javax.security.auth.Subject;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Base64;
import java.util.Hashtable;

@WebServlet(urlPatterns="/api/user")
public class UserServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Subject subject = null;

    try {
      subject = WSSubject.getRunAsSubject();
    } catch (WSSecurityException e) {
      e.printStackTrace();
    }

    Hashtable<?, ?> privateCredentials = subject.getPrivateCredentials(Hashtable.class).iterator().next();
    Object idTokenObj = privateCredentials.get("id_token");

    if (idTokenObj == null) {
      response.setStatus(401);
      response.getWriter().println("Unauthorized");
      return;
    }

    String payloadString = idTokenObj.toString().split("\\.")[1];
    byte[] decodedPayload = Base64.getUrlDecoder().decode(payloadString);
    String decodedPayloadString = new String(decodedPayload);
    JsonObject userJson = (JsonObject) JsonParser.parseString(decodedPayloadString);

    UserDao store = new UserDao();
    User user = store.getBySub(userJson.get("sub").getAsString());

    if (user == null) {
      System.out.println("USER IS NULL");
      String givenName = userJson.get("given_name").getAsString();
      String familyName = userJson.get("family_name").getAsString();
      String sub = userJson.get("sub").getAsString();
      String email = userJson.get("email").getAsString();
      String picture = userJson.has("picture") ? userJson.get("picture").getAsString() : "FAKE";

      user = new User(givenName, familyName, sub, email, picture);
      store.persist(user);
    }

    request.getSession(true).setAttribute("user", user);

    JsonObject responseJson = new JsonObject();
    responseJson.add("user", userJson);//userJson);

    response.addHeader("Content-Type", "application/json");
    response.getWriter().println(responseJson.toString());
  }

}
