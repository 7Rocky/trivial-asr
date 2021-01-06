<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="asr.trivial.domain.Question" %>
<%@ page import="asr.trivial.domain.Quiz" %>
<!doctype html>
<html>
  <head>
    <%@ include file="head.jsp" %>
    <script>
      const putAudio = button => {
        const parent = button.parentElement
        const number = parent.querySelector('.question-number').textContent

        parent.innerHTML += '<audio autoplay controls src="audio?q=' + (number - 1) + '" style="top: 0; position: absolute; right: 0; margin: 2%;"></audio>'
        parent.removeChild(parent.querySelector('button'))
      }
    </script>
  </head>
  <body style="background: #444">
    <%@ include file="navbar.jsp" %>
    <div class="container">
      <%
        Quiz quiz = (Quiz) session.getAttribute("quiz");
      %>
      <div>
        <h1 class="text-white"><%= quiz.getCategory().getText() %></h1>
        <!-- <h1 class="text-white"><%= quiz.getDifficulty().getText() %></h1> -->
      </div>
      <div class="questionContainer">
        <form action="results" id="quiz-form" method="post">
          <%
            List<Question> questions = quiz.getQuestions();

            for (int i = 0; i < questions.size(); i++) {
          %>
          <div class="card bg-dark text-white border-info mt-4">
            <div class="card-body">
              <h4 class="card-title">
                <div class="question-number text-center bg-info text-dark" style="width:36px; float:left; margin-right:20px;border-radius: 100%;"><%= i + 1 %></div>
                <div><%= questions.get(i).getQuestion() %></div>
              </h4>
              <button class="btn icon-btn text-white" onclick="putAudio(this)" style="top: 0; position: absolute; right: 0; margin: 2%;">
                <i class="fas fa-volume-up h1"></i>
              </button>
              <%
                int j = 0;

                for (String answer : questions.get(i).getAnswers()) {
                  j++;
              %>
              <div style="margin:12px 0">
                <input class="form-check-input" id="<%= i + 1 %>_<%= j %>" name="question <%= i + 1 %>" type="radio" value="<c:out value="<%= answer %>"/>">
                <label class="form-check-label" for="<%= i + 1 %>_<%= j %>"><%= answer %></label>
                <%
                  if (answer.equals(questions.get(i).getCorrectAnswer())) {
                %>
                <u>ESTA</u>
                <%
                  }
                %>
              </div>
              <%
                }
              %>
            </div>
          </div>
          <%
            }
          %>
          <div class="d-grid gap-2 col-6 mx-auto">
            <button class="btn btn-info btn-lg my-5" type="submit" form="quiz-form">
              <b>Submit Quiz!</b>
            </button>
          </div>
        </form>
      </div>
    </div>
    <%@ include file="footer.jsp" %>
  </body>
</html>
