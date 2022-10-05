<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Hung
  Date: 10/3/2022
  Time: 4:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

<c:if test="${requestScope['success'] != null}">
    <p style="color: lightseagreen">${requestScope['success']}</p>
</c:if>

Hello ${sessionScope['userLogin'].name} <br>

<a href="/userServlet?action=change-pass">
    <button>Change pass</button>
</a><br><br>
<c:if test="${sessionScope['role'] == 'ADMIN' }">
    menu admin
</c:if>

<a href="userServlet?action=logout">
    <button>logout</button>
</a>

</body>
</html>
