<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="asr.proyectoFinal.dominio.Quiz" %>
<%@ page import="asr.proyectoFinal.dominio.Question" %>
<!doctype html>
<html>
  <head>
    <title>Quiz</title>
    <meta charset="utf">
  </head>
  <body>
	<%
	  Quiz quiz = (Quiz) request.getAttribute("quiz");
	
	  for (Question question : quiz.getQuestions()) {
	%>
	<b><%= question.getQuestion() %></b>
	<br>
	<ul>
	  <%	
	    for (String answer : question.getAnswers()) {
	  %>
	  <li><%= answer %></li>
	  <%
	    }
	  %>
	</ul>
	<%
	  }
	%>
  </body>
</html>
