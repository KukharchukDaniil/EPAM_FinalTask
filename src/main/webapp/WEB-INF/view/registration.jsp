<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="lang"
       value="${param.lang!= null?param.lang : sessionScope.lang != null? sessionScope.lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Register</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='registration.css'>
</head>
<body>
<div class="locale">
    <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=ru">ru</a>
    <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=eng">eng</a>
    <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=de">de</a>
</div>
<form action="${pageContext.request.contextPath}/controller" method="post" class="registration-form">
    <div class="header">
        <h1><fmt:message key="label.register_form"/></h1>
    </div>
    <input type="hidden" name="command" value="signUp"/>
    <input type="text" name="name" required placeholder="<fmt:message key="label.registration_name"/>"/>
    <c:if test="${nameError==true}">
        <div class="error_message">
            <ul>
                <fmt:message key="label.name_error"/>
                Name should contain only letters from a to z(and A-Z)
            </ul>
        </div>
    </c:if>

    <input type="text" name="username"required  placeholder="Enter your login"/>
    <c:if test="${usernameIsTaken==true}">
        <fmt:message key="label.login_error"/>
        <div class="error_message">This login is already used. Choose another username</div>
    </c:if>
    <c:if test="${usernameError==true}">
        <div class="error_message">
            <fmt:message key="label.invalid_username"/>
            Username should contain only:
            <ul>
                <li><fmt:message key="label.lowercase_letters"/></li>
                <li><fmt:message key="label.digits"/></li>
                <li>"_"</li>
            </ul>
        </div>
    </c:if>


    <input type="password" name="password" required placeholder="<fmt:message key="label.password_placeholder"/>"/>
    <c:if test="${passwordError == true}">
        <div class="error_message">
            <fmt:message key="label.password_error"/>
        </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/controller?command=loginPage" class="login-link"><fmt:message key="label.back_to_login"/></a>
    <button type="submit" value="submit"><fmt:message key="label.sign_up"/> </button>
</form>
</body>
</html>
