<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="asr.trivial.domain.enums.Category" %>
<%@ page import="asr.trivial.domain.enums.Difficulty" %>
<%@ page import="asr.trivial.domain.enums.SelectedLanguage" %>
<!doctype html>
<html>
  <head>
    <%@ include file="head.jsp" %>
  </head>
  <body>
    <h1>Trivial ASR</h1>
    <a href="stats">Stats</a>
    <hr>
    <form action="quiz" method="get">
      <select name="category">
        <%
          for (Category category : Category.values()) {
        %>
        <option value="<%= category.getValue() %>"><%= category.getText() %></option>
        <%
          }
        %>
      </select>
      <select name="difficulty">
        <%
          for (Difficulty difficulty : Difficulty.values()) {
        %>
        <option value="<%= difficulty.getValue() %>"><%= difficulty.getText() %></option>
        <%
          }
        %>
      </select>
      <select name="language">
        <%
          for (SelectedLanguage selectedLanguage : SelectedLanguage.values()) {
        %>
        <option value="<%= selectedLanguage.getValue() %>"><%= selectedLanguage.getText() %></option>
        <%
          }
        %>
      </select>
      <button type="submit">Start Quiz!</button>
    </form>
    <a href="./login" id="login" style="display: none;">Login</a>
    <a href="./logout" id="logout" style="display: none;">Logout</a>
    <h3 id="user"></h3>
    <img id="picture">
    <script>
      fetch("./api/user")
        .then(res => res.json())
        .then(data => {
          document.querySelector('#user').textContent = 'Hello ' + data.user.name
          document.querySelector('#logout').style.display = ''
          document.querySelector('#picture').src = data.user.picture
        })
        .catch(() => document.querySelector('#login').style.display = '')
    </script>
  </body>
</html>
