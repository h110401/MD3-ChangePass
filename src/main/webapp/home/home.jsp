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


Hello ${sessionScope['userLogin'].name} <br>


<c:if test="${sessionScope['role'] == 'ADMIN' }">
    menu admin
</c:if>

<a href=""><button>logout</button></a>

</body>
</html>
