<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.epam.entities.UserRole" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>Document</title>

    <link rel='stylesheet' type='text/css' href='css/header.css'>
</head>
<body>
<input type="checkbox" class="nav-toggle" id="nav-toggle"/>
<label for="nav-toggle" class="nav-toggle-label">
    <span></span>
</label>
<nav>
    <div class="buttons-div">
        <a href="${pageContext.request.contextPath}/controller?command=mainPage">
            <fmt:message key="label.home"/>
        </a>
        <c:if test="${user.role != UserRole.ADMIN}">


            <a href="${pageContext.request.contextPath}/controller?command=myCourses&pageIndex=1">
                <fmt:message key="label.my_courses"/>
            </a>
        </c:if>
        <a onclick="openModal('account-modal')" style="cursor: pointer"><fmt:message
                key="label.account"/></a>
    </div>
    <div class="locale-div">
        <a class="ru" href="${pageContext.request.contextPath}/controller?command=setLocale&lang=ru">RU</a>
        <a  class="eng"  href="${pageContext.request.contextPath}/controller?command=setLocale&lang=eng">ENG</a>
        <a class="de" href="${pageContext.request.contextPath}/controller?command=setLocale&lang=de">DE</a>

        <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="label.logout"/></a>
    </div>
</nav>

<div class="modal-pane" id="account-modal" style="display: none">
    <div class="modal">
        <div class="modal-block">
            <div><fmt:message key="label.name"/></div>
            ${user.name}
        </div>
        <div class="modal-block">
            <div><fmt:message key="label.username"/></div>
            ${user.username}
        </div>
        <div class="modal-block">
            <div><fmt:message key="label.role"/></div>
            ${user.role}
        </div>

        <button onclick="closeModal('account-modal')"><fmt:message key="label.close"/></button>
    </div>
</div>
<script>
    function openModal(modalName) {
        var tabcontent;
        tabcontent = document.getElementById(modalName);
        tabcontent.style.display = "flex";
    }

    function closeModal(modalName) {
        var tabcontent;
        tabcontent = document.getElementById(modalName);
        tabcontent.style.display = "none";
    }

</script>
</body>
</html>
