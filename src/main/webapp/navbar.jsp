<nav class="navbar navbar-dark bg-dark mb-4">
  <div class="container-fluid px-5">
    <a class="navbar-brand" href="/">
      <img height="32" src="/img/logo.png">
      <span class="ml-2">ASR Trivial</span>
    </a>
    <%
      if (request.getRemoteUser() != null) {
    %>
    <span class="d-flex w-auto text-white">
      Hello&nbsp;<a class="text-info" href="stats">${user.getGivenName()}</a>&nbsp;
      <img height="32" src="${user.getPicture()}" style="border-radius: 100%;">
    </span>
    <%
      } else {
    %>
    <span class="d-flex w-auto">
      <a class="text-info" href="login">Login</a>
    </span>
    <%
      }
    %>
  </div>
</nav>
