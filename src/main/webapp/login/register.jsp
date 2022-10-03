<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 10/3/2022
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<a href="/"><button>Back</button></a>
<c:if test="${requestScope['message'] != null}">
    <p style="color: crimson">${requestScope['message']}</p>
</c:if>

<form method="post">
    Name:
    <input type="text" name="name"><br>
    Username:
    <input type="text" name="username"><br>
    Email:
    <input type="email" name="email"><br>
    Password:
    <input type="password" name="password"><br>
    Repeat Password:
    <input type="password" name="repeat"><br>

    <button>Register</button>
</form>

</body>
</html>
