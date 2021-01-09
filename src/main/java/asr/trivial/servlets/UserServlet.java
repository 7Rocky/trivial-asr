package asr.trivial.servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ibm.websphere.security.WSSecurityException;

import com.ibm.websphere.security.auth.WSSubject;

import javax.security.auth.Subject;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Base64;
import java.util.Hashtable;

import asr.trivial.dao.UserDao;

import asr.trivial.domain.User;

public class UserServlet extends HttpServlet {

  private static final long serialVersionUID = 1198609739470665938L;

  private static final String PICTURE = "picture";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Subject subject = null;

    try {
      subject = WSSubject.getRunAsSubject();
    } catch (WSSecurityException e) {
      e.printStackTrace();
    }

    if (subject != null) {
      Hashtable<?, ?> privateCredentials =
          subject.getPrivateCredentials(Hashtable.class).iterator().next();

      Object idTokenObj = privateCredentials.get("id_token");

      if (idTokenObj == null) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
      } else {
        String payloadString = idTokenObj.toString().split("\\.")[1];
        byte[] decodedPayload = Base64.getUrlDecoder().decode(payloadString);
        String decodedPayloadString = new String(decodedPayload);

        User user = this.decodeUser(decodedPayloadString);

        request.getSession(true).setAttribute("user", user);

        JsonObject userJson = new JsonObject();

        if (user != null) {
          userJson.addProperty("givenName", user.getGivenName());
          userJson.addProperty(PICTURE, user.getPicture());
        }

        response.addHeader("Content-Type", "application/json");

        try {
          response.getWriter().println(userJson.toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private User decodeUser(String decodedPayloadString) {
    JsonObject userJson = null;

    try {
      userJson = (JsonObject) JsonParser.parseString(decodedPayloadString);
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
    }

    UserDao store = new UserDao();
    User user = null;

    if (userJson != null) {
      user = store.getBySub(userJson.get("sub").getAsString());

      if (user == null) {
        String givenName = userJson.get("given_name").getAsString();
        String familyName = userJson.get("family_name").getAsString();
        String sub = userJson.get("sub").getAsString();
        String email = userJson.get("email").getAsString();
        String picture =
            userJson.has(PICTURE) ? userJson.get(PICTURE).getAsString() : User.DEFAULT_PICTURE;

        user = new User(givenName, familyName, sub, email, picture);
        store.persist(user);
      }
    }

    return user;
  }

}
