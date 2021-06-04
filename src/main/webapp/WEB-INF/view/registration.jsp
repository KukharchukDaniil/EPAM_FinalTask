<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Register</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='registration.css'>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post" class="registration-form">
    <div class="header">
        <h1>Registration form</h1>
    </div>
    <input type="hidden" name="command" value="signUp"/>
    <input type="text" name="name" required placeholder="Enter your name"/>
    <c:if test="${nameError==true}">
        <div class="error_message">
            <ul>
                Name should contain only letters from a to z(and A-Z)
            </ul>
        </div>
    </c:if>

    <input type="text" name="username"required  placeholder="Enter your login"/>
    <c:if test="${usernameIsTaken==true}">
        <div class="error_message">This login is already used. Choose another username</div>
    </c:if>
    <c:if test="${usernameError==true}">
        <div class="error_message">
            Username should contain only:
            <ul>
                <li>lowercase letters</li>
                <li>digits</li>
                <li>"_" symbol</li>
            </ul>
        </div>
    </c:if>


    <input type="password" name="password" required placeholder="Enter your password"/>
    <c:if test="${passwordError == true}">
        <div class="error_message">
            PasswordError
        </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/controller?command=loginPage" class="login-link">Back to login page</a>
    <button type="submit" value="submit">Sign up</button>
</form>
</body>
</html>
