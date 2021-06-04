<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="https://epam.com/jsp/tlds/mytags" prefix="pagination" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title></title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="main_contents.css"/>
</head>
<body>
<div class="tab-contents">

        <c:set var="pageIndex" value="${not empty param.pageIndex?param.pageIndex : 1}"/>
    <div>
        <c:set var="courses" value="${coursesList}"/>
        <ul class="courses-list">
            <c:forEach items="${courses}" var="course">
                <li class="courses-item">
                    <a href="${pageContext.request.contextPath}/controller?command=showCourse&courseId=${course.id}"
                       class="no-style-a">
                        <div class="content-box">
                            <div class="image_wrapper">
                                <div class="image_s"></div>
                            </div>
                            <div class="vertical-box card-info">
                                <div class="horizontal-box">
                                    <div class="course-name">${course.courseName}</div>
                                </div>
                                <div class="horizontal-box">
                                    <div class="course-category">${course.category}</div>
                                </div>
                                <div class="horizontal-box">${course.description}</div>
                            </div>
                        </div>
                    </a>
                </li>
            </c:forEach>
            <div class="pagination">
                <pagination:load uri="${pageContext.request.contextPath}/controller?command=mainPage"
                                 totalItems="${totalItems}"
                                 currentPage="${pageIndex}"/>
            </div>
        </ul>
    </div>
</div>
</body>
</html>
