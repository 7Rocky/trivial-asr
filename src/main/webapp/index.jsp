<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="asr.trivial.domain.enums.Category" %>
<%@ page import="asr.trivial.domain.enums.Difficulty" %>
<%@ page import="asr.trivial.domain.enums.SelectedLanguage" %>
<%@ page import="asr.trivial.domain.enums.Type" %>
<!doctype html>
<html>
  <head>
    <title>Trivial ASR</title>
    <meta charset="utf-8">
  </head>
  <body>
    <h1>Trivial ASR</h1>
    <hr>

      <form action="quiz" method="get">
        <select name="amount">
          <option value="5">5</option>
          <option value="10">10</option>
        </select>
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
        <select name="type">
          <%
            for (Type type : Type.values()) {
          %>
          <option value="<%= type.getValue() %>"><%= type.getText() %></option>
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
  </body>
</html>
