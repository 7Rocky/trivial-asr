<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ page import="asr.trivial.domain.enums.Category" %>
<%@ page import="asr.trivial.domain.Question" %>
<%@ page import="asr.trivial.domain.Quiz" %>
<%@ page import="asr.trivial.domain.User" %>
<!doctype html>
<html lang="en">
  <head>
    <%@ include file="/include/head.jsp" %>
    <style>
      footer {
        bottom: 0;
        position: fixed;
        width: 100%;
      }
    </style>
  </head>
  <body>
    <%@ include file="/include/navbar.jsp" %>
    <%
      User user = (User) session.getAttribute("user");
    %>
    <div class="container mb-5">
      <%
        if (user == null) {
      %>
      <h1 class="text-white mb-4">These are your results</h1>
      <%
        } else {
      %>
      <h1 class="text-white mb-4">These are your results, <%= user.getGivenName() %></h1>
      <%
        }
      %>
      <br>
      <%
        int correct = (Integer) session.getAttribute("correctlyAnswered");
        
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        int done = quiz.getQuestions().size();
        Category category = quiz.getCategory();

        String colorClass = "";
        String message = "";
        String difficulty = "";

        if (100 * correct / done > 66) {
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
      %>
      <div class="col col-md-6 col-lg-4 col-xl-3 mb-5 mx-auto">
        <div class="row">
          <div class="bg-dark border-<%= colorClass %> card text-center text-white">
            <div class="card-body">
              <h5 class="card-title">
                <span><%= category.getText() %></span>
                <span class="badge bg-info text-dark"><%= quiz.getDifficulty().getText() %></span>
              </h5>
              <p class="card-text text-<%= colorClass %>"><%= correct %>/<%= done %></p>
              <a class="btn btn-<%= colorClass %>" href="quiz?category=<%= category.getValue() %>&difficulty=<%= difficulty %>"><%= message %></a>
            </div>
          </div>
        </div>
        <br>
        <div class="row">
          <div class="col col-6">
            <a class="btn btn-info text-center" href="/" role="button" style="width: 100%;">Return to index</a>
          </div>
          <div class="col col-6">
            <a class="btn btn-info text-center" href="/stats" role="button" style="width: 100%;">View stats</a>
          </div>
        </div>
      </div>
      <br>
    </div>
    <%@ include file="/include/footer.jsp" %>
  </body>
</html>
