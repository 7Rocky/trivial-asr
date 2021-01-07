<nav class="bg-dark mb-4 navbar navbar-dark">
  <div class="container-fluid px-5">
    <a class="navbar-brand" href="/">
      <img height="32" src="/img/logo.png">
      <span class="ms-2">ASR Trivial</span>
    </a>
    <%
      if (request.getRemoteUser() != null) {
    %>
    <div class="dropdown">
      <a aria-expanded="false" class="align-self-center btn d-flex dropdown-toggle text-white w-auto"  data-bs-toggle="dropdown" href="#" id="dropdown-user" role="button">
        <span class="me-2">${user.getGivenName()}</span>
        &nbsp;
        <img height="32" src="${user.getPicture()}">
      </a>
      <ul class="bg-dark dropdown-menu" aria-labelledby="dropdown-user">
        <li>
          <a class="dropdown-item text-info" href="stats">Stats</a>
        </li>
        <li>
          <a class="dropdown-item text-info" href="logout">Logout</a>
        </li>
      </ul>
    </div>
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
