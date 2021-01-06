<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
  <body style="background: #444">
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
      <div class="row row-cols-1 row-cols-md-3 g-4">
        <%
          for (Map.Entry<String, List<Integer>> entry : user.getStats().entrySet()) {
            int correct = entry.getValue().get(0);
            int total = entry.getValue().get(1);
            String colorClass = "";
            String message = "";
            String difficulty = "";

            if (total == 0) {
              colorClass = "primary";
              message = "Start Quiz!";
              difficulty = "easy";
            } else if (100 * correct / total > 66) {
              colorClass = "success";
              message = "Try Hard level!";
              difficulty = "hard";
            } else if (100 * correct / total > 33) {
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
          <div class="card bg-dark text-white border-<%= colorClass %> text-center">
            <div class="card-body">
              <h5 class="card-title"><%= entry.getKey() %></h5>
              <p class="card-text text-<%= colorClass %>"><%= entry.getValue().get(0) %>/<%= entry.getValue().get(1) %></p>
              <a href="quiz?category=<%= category %>&difficulty=<%= difficulty %>" class="btn btn-<%= colorClass %>"><%= message %></a>
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
