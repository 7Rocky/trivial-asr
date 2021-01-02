<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="asr.trivial.dominio.Quiz" %>
<%@ page import="asr.trivial.dominio.Question" %>
<!doctype html>
<html>
  <head>
    <title>Quiz</title>
    <meta charset="utf">
  </head>
  <body>
	<%
	  Quiz quiz = (Quiz) session.getAttribute("quiz");
	  List<Question> questions = quiz.getQuestions();
	
	  for(int i = 0; i < questions.size(); i++) {
	%>
	<b><%= i + 1 %>: <%= questions.get(i).getQuestion() %></b>
	<audio controls src="audio?q=<%= i %>"></audio>
	<br>
	<ul>
	  <%	
	    for (String answer : questions.get(i).getAnswers()) {
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
