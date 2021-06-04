<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="lang"
       value="${param.lang!= null?param.lang : sessionScope.lang != null? sessionScope.lang : pageContext.request.locale}"/>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="main.css"/>
</head>
<body>
<nav>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=mainPage&pageIndex=1">
            <fmt:message key="label.home"/>
        </a>
        <a class="active" href="${pageContext.request.contextPath}/controller?command=myCourses">
            <fmt:message key="label.my_courses"/>
        </a>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=ru">ru</a>
        <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=eng">eng</a>
        <a href="${pageContext.request.contextPath}/controller?command=setLocale&lang=de">de</a>
        <a onclick="openModal('account-modal')" style="cursor: pointer"><fmt:message key="label.account"/></a>
        <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="label.logout"/></a>
    </div>
</nav>
<div class="modal-pane" id="account-modal" style="display: none">
    <div class="modal">
        <div class="modal-block">
            <div><fmt:message key="label.name"/> </div>
            ${user.name}
        </div>
        <div class="modal-block">
            <div><fmt:message key="label.username"/> </div>
            ${user.username}
        </div>
        <div class="modal-block">
            <div><fmt:message key="label.role"/> </div>
            ${user.role}
        </div>

        <button onclick="closeModal('account-modal')"><fmt:message key="label.close"/></button>
    </div>
</div>
<div>
    <jsp:include page="courses_content.jsp"></jsp:include>
</div>
</body>

</html>