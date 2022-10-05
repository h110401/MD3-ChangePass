<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Pass</title>
</head>
<body>

<h1>Change pass</h1>

<c:if test="${requestScope['message'] != null}">
    <p style="color: red">${requestScope['message']}</p>
</c:if>

<form method="post">
    Old Password<br>
    <input type="text" name="old-pass"><br>
    New Password<br>
    <input type="text" name="new-pass"><br>
    Repeat Password<br>
    <input type="text" name="repeat-pass"><br>
    <button>Submit</button>
</form>

<a href="/">home</a>

</body>
</html>
