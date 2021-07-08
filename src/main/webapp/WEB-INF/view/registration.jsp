<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Register</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='css/registration.css'>
</head>
<body>

<div class="container">
    <form action="${pageContext.request.contextPath}/controller" method="post" class="registration-form">
        <div class="header">
            <h1><fmt:message key="label.register_form"/></h1>
            <div class="locale">
                <fmt:message key="label.locale"></fmt:message>
                <div class="locale-content">
                    <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=ru">RU</a>
                    <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=eng">ENG</a>
                    <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=de">DE</a>
                </div>
            </div>
        </div>
        <input type="hidden" name="command" value="signUp"/>
        <input type="text" name="name" pattern="[a-zA-Z]+" required placeholder="<fmt:message key="label.registration_name"/>"/>
        <c:if test="${nameError==true}">
            <div class="error_message">
                <ul>
                    <fmt:message key="label.name_error"/>
                </ul>
            </div>
        </c:if>

        <input type="text" name="username" pattern="^(?=.*[a-z])(?=.*[\d]*)(?=.*[_]*).{0,18}" required placeholder="<fmt:message key="label.login_placeholder"/>"/>
        <c:if test="${usernameIsTaken==true}">

            <div class="error_message"><fmt:message key="label.login_error"/></div>
        </c:if>
        <c:if test="${usernameError==true}">
            <div class="error_message">
                <fmt:message key="label.invalid_username"/>
                <ul>
                    <li><fmt:message key="label.lowercase_letters"/></li>
                    <li><fmt:message key="label.digits"/></li>
                    <li>"_"</li>
                </ul>
            </div>
        </c:if>


        <input type="password" name="password" pattern="^(?=.*[a-z!?@#$%A-Z\d]).{6,18}$" required placeholder="<fmt:message key="label.password_placeholder"/>"/>
        <c:if test="${passwordError == true}">
            <div class="error_message">
                <fmt:message key="label.password_error"/>
            </div>
        </c:if>
        <a href="${pageContext.request.contextPath}/controller?command=loginPage" class="login-link"><fmt:message
                key="label.back_to_login"/></a>
        <button type="submit" value="submit"><fmt:message key="label.sign_up"/></button>
    </form>
</div>
</body>
</html>
