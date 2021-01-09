<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="asr.trivial.domain.Question" %>
<%@ page import="asr.trivial.domain.Quiz" %>
<%@ page import="asr.trivial.domain.enums.SelectedLanguage" %>
<!doctype html>
<html lang="en">
  <head>
    <%@ include file="/include/head.jsp" %>
  </head>
  <body>
    <%@ include file="/include/navbar.jsp" %>
    <div class="container">
      <%
        Quiz quiz = (Quiz) session.getAttribute("quiz");
      %>
      <div class="row justify-content-between">
        <h1 class="col-9 text-white">
          <span><%= quiz.getCategory().getText() %></span>
          <span class="badge bg-info text-dark"><%= quiz.getDifficulty().getText() %></span>
        </h1>
        <div class="col-3 dropdown">
          <button aria-expanded="false" class="btn btn-lg btn-secondary dropdown-toggle float-right" data-bs-toggle="dropdown" id="dropdown-languages" type="button"><%= quiz.getSelectedLanguage().getText() %></button>
          <ul class="bg-dark dropdown-menu" aria-labelledby="dropdown-languages">
            <%
              for (SelectedLanguage selectedLanguage : SelectedLanguage.values()) {
            %>
            <li>
              <a class="dropdown-item <%= selectedLanguage.getValue().equals(SelectedLanguage.ENGLISH.getValue()) ? "bg-info text-dark" : "bg-dark text-white" %>" href="#" id="<%= selectedLanguage.getValue() %>" onclick="loadQuiz(this)"><%= selectedLanguage.getText() %></a>
            </li>
            <%
              }
            %>
          </ul>
        </div>
      </div>
      <form action="results" id="quiz-form" method="post">
        <%
          List<Question> questions = quiz.getQuestions();

          for (int i = 0; i < questions.size(); i++) {
        %>
        <div class="bg-dark border-info card mt-4 text-white">
          <div class="card-body d-flex">
            <div class="p-2 w-100">
              <h4 class="card-title mb-4">
                <span class="badge bg-info question-number rounded-pill text-center text-dark"><%= i + 1 %></span>
                <span class="question"><%= questions.get(i).getQuestionTitle() %></span>
              </h4>
              <%
                int j = 0;

                for (String answer : questions.get(i).getAnswers()) {
                  j++;
              %>
              <div class="my-3">
                <input class="form-check-input" id="<%= i + 1 %>_<%= j %>" name="question <%= i + 1 %>" type="radio" value="<c:out value="<%= answer %>"/>">
                <label class="form-check-label" for="<%= i + 1 %>_<%= j %>"><%= answer %></label>
              </div>
              <%
                }
              %>
            </div>
            <button class="align-self-center btn d-flex text-white" onclick="putAudio(this)">
              <i class="fas fa-volume-up h1"></i>
            </button>
          </div>
        </div>
        <%
          }
        %>
        <div class="d-grid gap-2 col-6 mx-auto">
          <button class="btn btn-info btn-lg my-5" form="quiz-form" type="submit">
            <b>Submit Quiz!</b>
          </button>
        </div>
      </form>
    </div>
    <div aria-hidden="true" aria-labelledby="modal-lavel" class="fade modal" id="text-to-speech-error" tabindex="-1">
      <div class="modal-dialog">
        <div class="bg-dark border-info modal-content text-white">
          <div class="modal-header">
            <h5 class="modal-title" id="modal-lavel">Text to Speech</h5>
            <button aria-label="Close" class="btn-close" data-bs-dismiss="modal" style="filter: invert(100%);" type="button"></button>
          </div>
          <div class="modal-body">The IBM Watson Text to Speech service is not available right now. Please, try again later...</div>
          <div class="modal-footer">
            <button class="btn btn-info" data-bs-dismiss="modal" type="button">Close</button>
          </div>
        </div>
      </div>
    </div>
    <%@ include file="/include/footer.jsp" %>
  </body>
</html>
