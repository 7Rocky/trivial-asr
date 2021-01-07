<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="asr.trivial.domain.User" %>
<%@ page import="asr.trivial.domain.enums.Category" %>
<!doctype html>
<html>
  <head>
    <%@ include file="head.jsp" %>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
    <div class="container mb-5">
      <%
        User user = (User) session.getAttribute("user");

        if (user.getSub().equals(User.ALL_USERS_SUB)) {
      %>
      <h1 class="text-white">Global stats of all users</h1>
      <%
        } else {
      %>
      <h1 class="text-white">These are your stats, <%= user.getGivenName() %></h1>
      <%
        }
      %>
      <div class="g-4 row row-cols-1 row-cols-md-3">
        <%
          for (Map.Entry<String, List<Integer>> entry : user.getStats().entrySet()) {
            int correct = entry.getValue().get(0);
            int done = entry.getValue().get(1);
            String colorClass = "";
            String message = "";
            String difficulty = "";

            if (done == 0) {
              colorClass = "primary";
              message = "Start Quiz!";
              difficulty = "easy";
            } else if (100 * correct / done > 66) {
              colorClass = "success";
              message = "Try Hard level!";
              difficulty = "hard";
            } else if (100 * correct / done > 33) {
              colorClass = "warning";
              message = "Try Medium level?";
              difficulty = "medium";
            } else {
              colorClass = "danger";
              message = "Try Easy level...";
              difficulty = "easy";
            }

            int category = Category.getCategory(entry.getKey()).getValue();
        %>
        <div class="col col-md-4 col-xl-3">
          <div class="bg-dark border-<%= colorClass %> card text-center text-white">
            <div class="card-body">
              <h5 class="card-title"><%= entry.getKey() %></h5>
              <p class="card-text text-<%= colorClass %>"><%= correct %>/<%= done %></p>
              <a class="btn btn-<%= colorClass %>" href="quiz?category=<%= category %>&difficulty=<%= difficulty %>"><%= message %></a>
            </div>
          </div>
        </div>
        <%
          }
        %>
      </div>
    </div>
    <%@ include file="footer.jsp" %>
  </body>
</html>
