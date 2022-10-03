<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 10/3/2022
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<c:if test="${requestScope['message'] != null}">
    <p style="color: lightseagreen">${requestScope['message']}</p>
</c:if>

<form method="post">
    <input type="hidden" name="action" value="login">
    <label>
        Username:
        <input type="text" name="username">
    </label>
    <br>

    <label>
        Password:
        <input type="password" name="password">
    </label>
    <br>

    <button>Login</button>

    <a href="/userServlet?action=register"><button type="button">Register</button></a>

</form>

</body>
</html>
