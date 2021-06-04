<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Log in</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='login.css'>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>YouCourse</h1>
    </div>
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <input type="text" name="login" required placeholder="Enter username"/>
        <input type="password" name="password" required placeholder="Enter password"/>
        <c:if test="${error==true}">
            <div class="error_message">Wrong username or password</div>
        </c:if>
        <div><a href="${pageContext.request.contextPath}/controller?command=registrationPage" class="registration-link">Not
            registered yet?
            Register now</a></div>
        <button type="submit" value="submit">Log in</button>
    </form>

</div>


</body>
</html>