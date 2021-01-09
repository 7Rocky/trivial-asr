<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<!doctype html>
<html lang="en">
  <head>
    <%@ include file="/include/head.jsp" %>
    <style>
      footer {
        bottom: 0;
        position: fixed;
        width: 100%;
      }
      .container {
        margin-bottom: 15% !important;
      }
    </style>
  </head>
  <body>
    <%@ include file="/include/navbar.jsp" %>
    <div class="container">
      <div class="img-blurred-edge text-center">
        <img alt="500: Internal Server Error" src="/img/500.gif" style="filter: invert(100%);">
      </div>
    </div>
    <%@ include file="/include/footer.jsp" %>
  </body>
</html>
