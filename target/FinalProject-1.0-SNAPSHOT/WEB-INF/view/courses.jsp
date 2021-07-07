<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>My courses</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <jsp:include page="courses_content.jsp"/>
</div>
</body>

</html>