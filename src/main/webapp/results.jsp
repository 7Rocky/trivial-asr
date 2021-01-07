<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<!doctype html>
<html>
  <head>
    <%@ include file="head.jsp" %>
  </head>
  <body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
      Acertadas: ${correctlyAnswered}
      <br>
      <a href="/">Go to home</a>
      <a href="/stats">View stats</a>
    </div>
    <%@ include file="footer.jsp" %>
  </body>
</html>
