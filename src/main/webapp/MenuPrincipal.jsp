<%--
  Created by IntelliJ IDEA.
  User: Labtel
  Date: 9/11/2022
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clasificatorias</title>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-light bg-light">
    <a class="navbar-brand" href="#">Clasificatorias Sudamericanas Qatar 2022</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item" >
                <a class="nav-link" href="<%=request.getContextPath()%>/PartidoServlet">Partidos</a>
            </li>
            <li class="nav-item" >
                <a class="nav-link" href="<%=request.getContextPath()%>/ArbitroServlet">Arbitros</a>
            </li>
        </ul>
    </div>
</nav>

</body>
</html>
