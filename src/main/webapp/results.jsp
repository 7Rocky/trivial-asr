<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<!doctype html>
<html>
  <head>
    <%@ include file="head.jsp" %>
    <link href="style.css" rel="stylesheet">
  </head>
  <body>
    Acertadas: ${correctlyAnswered}
    <br>
    <a href="/">Go to home</a>
    <a href="/stats">View stats</a>
  </body>
</html>
