<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Log in</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='css/login.css'>
</head>
<body>


<div class="container">
    <div class="header">
        <h1>YouCourse</h1>
        <div class="locale">
            <fmt:message key="label.locale"></fmt:message>
            <div class="locale-content">
                <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=ru">RU</a>
                <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=eng">ENG</a>
                <a href="http://localhost:8080/webapp/controller?command=setLocale&lang=de">DE</a>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="login"/>
        <input type="text" name="login" pattern="^(?=.*[a-z])(?=.*[\d]*)(?=.*[_]*).{0,18}" required placeholder="<fmt:message key="label.login_placeholder"/>"/>
        <input type="password" name="password" pattern="^(?=.*[a-z!?@#$%A-Z\d]).{0,18}$" required placeholder="<fmt:message key="label.password_placeholder"/>"/>
        <c:if test="${error==true}">
            <div class="error_message"><fmt:message key="label.auth_error"/></div>
        </c:if>
        <div><a href="${pageContext.request.contextPath}/controller?command=registrationPage" class="registration-link"><fmt:message
                key="label.to_registration_page"/></a></div>
        <button type="submit" value="submit"><fmt:message key="label.logIn"/></button>
    </form>

</div>
<script>

</script>

</body>
</html>
