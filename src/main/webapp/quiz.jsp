<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="java.util.List" %>
<%@ page import="asr.trivial.domain.Question" %>
<%@ page import="asr.trivial.domain.Quiz" %>
<!doctype html>
<html>
  <head>
    <title>Quiz</title>
    <meta charset="utf-8">
    <script>
      const putAudio = button => {
    	const number = button.previousElementSibling.children[0].textContent
    	button.parentElement.innerHTML += '<audio autoplay controls src="audio?q=' + (number - 1) + '"></audio>'
      }
    </script>
  </head>
  <body>
	<%
	  Quiz quiz = (Quiz) session.getAttribute("quiz");
	  List<Question> questions = quiz.getQuestions();
	
	  for (int i = 0; i < questions.size(); i++) {
	%>
	<div>
      <b>
	    <span class="question-number"><%= i + 1 %></span>: <span class="question"><%= questions.get(i).getQuestion() %></span>
	  </b>
      <button onclick="putAudio(this)">Get Audio</button>
	</div>
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
