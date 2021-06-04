<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pagination" uri="https://epam.com/jsp/tlds/mytags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.epam.entities.UserRole" %>
<%@ page import="com.epam.entities.SolutionStatus" %>
<c:set var="pageIndex" value="${not empty param.pageIndex?param.pageIndex : 1}"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="course.css" type="text/css"/>
</head>
<body>
<nav>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=mainPage">
            Home
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=myCourses">
            My courses
        </a>
        <a>
            Find course
        </a>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/controller?command=account_info">Account info</a>
        <a href="${pageContext.request.contextPath}/controller?command=logout">Log out</a>
    </div>
</nav>
<div>

    <c:forEach items="${solutionTaskDtoList}" var="dto">
        <c:if test="${dto.solutionStatus != SolutionStatus.GRADED}">
            <div class="modal-pane" id="${dto.solutionId}" style="display: none">
                <div class="modal">
                    <form action="${pageContext.request.contextPath}/controller" method="post" class="grading-form">
                        <div class="modal-block">
                            <div>Task name:</div> ${dto.taskName}</div>
                        <div class="modal-block"><div>Description:</div> ${dto.taskDescription}</div>
                        <div class="modal-block"><div>Solution:</div> ${dto.solutionValue}</div>
                        <input type="hidden" value="gradeSolution" name="command"/>
                        <input type="hidden" value="${dto.taskId}" name="taskId"/>
                        <input type="hidden" value="${dto.solutionId}" name="solutionId"/>
                        <input type="hidden" value="${dto.solutionValue}" name="value"/>
                        <input type="hidden" value="${dto.userId}" name="userId"/>
                        <input type="hidden" value="${course.id}" name="courseId"/>
                        <textarea name="solutionComment" placeholder="Comment here"></textarea>
                        <input max="10" min="0" name="solutionMark" required="true" type="number" placeholder="Mark.."/>
                        <input type="submit" value="Submit" />
                    </form>
                </div>
            </div>
        </c:if>
    </c:forEach>
    <div class="main-contents">
        <div class="menu">
            <a class="menu-button" onclick="openTab(event, 'About')">About course</a>

            <c:if test="${user.role == UserRole.TEACHER}">
                <a class="menu-button" onclick="openTab(event, 'Solutions')" id="defaultOpen">Solutions</a>
                <a class="menu-button"
                   href="${pageContext.request.contextPath}/controller?command=showCourse&courseId=${course.id}">Tasks</a>
            </c:if>
        </div>
        <div class="contents">
            <div class="tabcontent" id="About">
                <div class="name" id="odd">${course.courseName}</div>
                <div class="description">${course.description}</div>
            </div>
            <div class="tabcontent" id="Solutions">
                <div class="list-item">
                    <div class="task-name">Task name</div>
                    <div class="status">Solution status</div>
                    <div class="mark">Mark</div>
                    <div class="task-description">Task description</div>
                </div>
                <c:if test="${solutionTaskDtoList.isEmpty()}">
                    <div class="task-name"> No solutions available</div>
                </c:if>
                <c:forEach var="solution" items="${solutionTaskDtoList}">
                    <div class="list-item">
                        <div class="task-name">${solution.taskName}</div>
                        <div class="status">${solution.solutionStatus}</div>
                        <div class="mark">${solution.solutionMark}</div>
                        <div class="task-description">${solution.taskDescription}</div>
                        <c:if test="${user.role == UserRole.ADMIN || user.role == UserRole.TEACHER &&
                         solution.solutionStatus != SolutionStatus.GRADED}">
                            <a class="tab-button" style="background: #5056a8eb; text-decoration: none"
                               onclick="openModal('${solution.solutionId}')">Evaluate</a>
                        </c:if>
                    </div>
                </c:forEach>
                    <c:if test="${totalItems > 5}">
                    <div class="pagination">
                        <pagination:load
                                uri="${pageContext.request.contextPath}/controller?command=showSolutions&courseId=${course.id}"
                                totalItems="${totalItems}"
                                currentPage="${pageIndex}"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</div>
<script>
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;

        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("menu-button");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        document.getElementById(tabName).style.display = "flex";
        evt.currentTarget.className += " active";
    }

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

    document.getElementById("defaultOpen").click();
    $("#solutionSelect").on("change", function () {
        $("#" + $(this).val()).show().siblings().hide();
    })
</script>
</body>

</html>