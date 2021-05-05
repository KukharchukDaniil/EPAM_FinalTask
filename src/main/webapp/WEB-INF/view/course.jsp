<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="course.css" type="text/css"/>
</head>
<body>
<header>
    <h1>${user.name}</h1>
    You're welcome!
</header>
<nav>
    <a href="${pageContext.request.contextPath}/controller?command=mainPage">
        Home
    </a>
    <a href="${pageContext.request.contextPath}/controller?command=myCourses">
        My courses
    </a>
    <a>
        Find course
    </a></nav>
<div>
    <jsp:useBean id="courseService" scope="request" class="com.epam.service.CourseService"
                 type="com.epam.service.CourseService"/>
    <c:set var="courseId" value="${param.get('courseId')}"></c:set>
    <c:set var="course" value="${courseService.getCourseById(Integer.valueOf( courseId))}"></c:set>
    <c:set var="isEnrolled" value="${courseService.getMyCourses(user.id).contains(course)}"></c:set>
    <div class="tab-contents">
        <div class="tab-contents-header">
            <h1>${course.courseName}</h1>
            <h2>${course.description}</h2>
            <c:if test="${isEnrolled==false}">
                <form action="${pageContext.request.contextPath}/controller" method="post">
                    <input type="hidden" name="command" value="enroll"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <input type="submit" value="Enroll" class="header-button">
                </form>
            </c:if>
            <c:if test="${isEnrolled==true}">

            </c:if>
        </div>
        <c:if test="${isEnrolled==true}">
            <div class="contents-panel">
                <div class="contents-menu">
                    <button class="contents-menu-button">Tasks</button>
                    <button class="contents-menu-button">Students</button>
                    <button class="contents-menu-button">Marks</button>
                </div>
                <div class="contents-info">
                    <jsp:useBean id="taskService" scope="request" type="com.epam.service.TaskService"
                                 class="com.epam.service.TaskService"></jsp:useBean>
                    <jsp:useBean id="solutionService" scope="request" type="com.epam.service.SolutionService"
                                 class="com.epam.service.SolutionService"></jsp:useBean>
                    <c:set var="tasks" value="${taskService.getTasksByCourseId(courseId)}"></c:set>
                    <c:forEach items="${tasks}" var="task">
                        <c:set var="solution"
                               value="${solutionService.getSolutionByUserIdAndTaskId(task.id,user.id)}"></c:set>
                        <div class="task-item">
                            <div class="task-name">
                                    ${task.name}
                                <c:choose>
                                    <c:when test="${solution.isPresent()}">

                                        <button class="solution-status-${solution.get().status}"
                                                onclick="showSolution('solution-tab#${solution.get().id}')">
                                                ${solution.get().status}
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="solution-status-not_solved"
                                                onclick="showSolution('send-solution-tab#${task.id}')">
                                            NOT SOLVED
                                        </button>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="task-description">${task.description}</div>
                            <c:choose>

                                <c:when test="${solution.isPresent()}">
                                    <div class="solution-tab" id="solution-tab#${solution.get().id}">
                                        <table>
                                            <tr>
                                                <td>Solution:</td>
                                                <td>${solution.get().value}</td>
                                            </tr>
                                            <c:if test="${solution.get().status.toString().equals('GRADED')}">
                                                <tr>
                                                    <td>Mark:</td>
                                                    <td>${solution.get().mark}</td>
                                                </tr>
                                                <c:if test="${!solution.get().comment.isEmpty()}">
                                                    <tr>
                                                        <td>Comment</td>
                                                        <td>${solution.get().comment}</td>
                                                    </tr>
                                                </c:if>
                                            </c:if>
                                        </table>

                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="send-solution-tab" id="send-solution-tab#${task.id}">
                                        <form action="${pageContext.request.contextPath}/controller" method="post">
                                            <input type="hidden" name="command" value="sendSolution">
                                            <input type="hidden" name="taskId" value="${task.id}"/>
                                            <input type="hidden" name="userId" value="${user.id}"/>
                                            <input type="hidden" name="courseId" value="${course.id}"/>
                                            <input type="text" name="solutionValue"/>
                                            <input type="submit" value="send solution"/>
                                        </form>
                                    </div>
                                </c:otherwise>
                            </c:choose>


                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <div class="contents-footer"></div>
    </div>
</div>
<script>
    function openPage(pageName, element) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        document.getElementById(pageName).style.display = "flex";
    }

    function showSolution(elementName) {
        var x = document.getElementById(elementName);
        if (x.style.display == "none") {
            x.style.display = "flex";
        } else {
            x.style.display = "none";
        }
    }

    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();
</script>
</body>

</html>