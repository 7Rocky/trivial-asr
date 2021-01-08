<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" language="java" %>
<%@ page import="asr.trivial.domain.enums.Category" %>
<%@ page import="asr.trivial.domain.enums.Difficulty" %>
<%@ page import="asr.trivial.domain.enums.SelectedLanguage" %>
<!doctype html>
<html lang="en">
  <head>
    <%@ include file="head.jsp" %>
    <style>
      footer {
        bottom: 0;
        position: absolute;
        width: 100%;
      }
    </style>
  </head>
  <body onload="getUser()">
    <%@ include file="navbar.jsp" %>
    <div class="container mb-5">
      <h1 class="text-white">Welcome to ASR Trivial!</h1>
      <br>
      <div class="row">
        <div class="col col-lg-4 col-md-6">
          <h3 class="text-white">Game parameters</h3>
          <div class="bg-dark border-info card mt-4 text-white">
            <div class="card-body">
              <form action="quiz" method="get">
                <h6>Category</h6>
                <select name="category" class="form-select form-select-sm mb-4">
                  <%
                    for (Category category : Category.values()) {
                  %>
                  <option value="<%= category.getValue() %>"><%= category.getText() %></option>
                  <%
                    }
                  %>
                </select>
                <h6>Difficulty</h6>
                <select name="difficulty" class="form-select form-select-sm mb-4">
                  <%
                    for (Difficulty difficulty : Difficulty.values()) {
                  %>
                  <option value="<%= difficulty.getValue() %>"><%= difficulty.getText() %></option>
                  <%
                    }
                  %>
                </select>
                <div class="d-flex justify-content-end">
                  <button type="submit" class="btn btn-info">Start Quiz!</button>
                </div>
              </form>
            </div>
          </div> 
        </div>
      </div>
    </div>
    <%@ include file="footer.jsp" %>
  </body>
</html>
