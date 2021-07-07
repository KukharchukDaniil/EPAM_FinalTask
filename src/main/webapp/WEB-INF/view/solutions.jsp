<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pagination" uri="https://epam.com/jsp/tlds/mytags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="com.epam.entities.UserRole" %>
<%@ page import="com.epam.entities.SolutionStatus" %>
<c:set var="pageIndex" value="${not empty param.pageIndex?param.pageIndex : 1}"/>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="css/course.css" type="text/css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
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

    <c:forEach items="${solutionTaskDtoList}" var="dto">
        <c:if test="${dto.solutionStatus != SolutionStatus.GRADED}">
            <div class="modal-pane" id="${dto.solutionId}" style="display: none">
                <div class="modal">
                    <form action="${pageContext.request.contextPath}/controller" method="post" class="grading-form">
                        <div class="modal-block">
                            <div>Task name:</div> ${dto.taskName}</div>
                        <div class="modal-block"><div><fmt:message key="label.description"/>:</div> ${dto.taskDescription}</div>
                        <div class="modal-block"><div><fmt:message key="label.solution"/>:</div> ${dto.solutionValue}</div>
                        <input type="hidden" value="gradeSolution" name="command"/>
                        <input type="hidden" value="${dto.taskId}" name="taskId"/>
                        <input type="hidden" value="${dto.solutionId}" name="solutionId"/>
                        <input type="hidden" value="${dto.solutionValue}" name="value"/>
                        <input type="hidden" value="${dto.userId}" name="userId"/>
                        <input type="hidden" value="${course.id}" name="courseId"/>
                        <textarea name="solutionComment" placeholder="<fmt:message key="label.comment"/>"></textarea>
                        <input max="10" min="0" name="solutionMark" required="true" type="number" placeholder="<fmt:message key="label.mark"/>.."/>
                        <input type="submit" value="<fmt:message key="label.submit"/>" />
                    </form>
                </div>
            </div>
        </c:if>
    </c:forEach>
    <div class="main-contents">
        <div class="menu">
            <a class="menu-button" onclick="openTab(event, 'About')"><fmt:message key="label.about_course"/></a>

            <c:if test="${user.role == UserRole.TEACHER}">
                <a class="menu-button" onclick="openTab(event, 'Solutions')" id="defaultOpen"><fmt:message key="label.solutions"/></a>
                <a class="menu-button"
                   href="${pageContext.request.contextPath}/controller?command=showCourse&courseId=${course.id}"><fmt:message key="label.tasks"/></a>
            </c:if>
        </div>
        <div class="contents">
            <div class="tabcontent" id="About">
                <div class="name" id="odd">${course.courseName}</div>
                <div class="description">${course.description}</div>
            </div>
            <div class="tabcontent" id="Solutions">
                <div class="list-item">
                    <div class="task-name"><fmt:message key="label.task_name"/></div>
                    <div class="status"><fmt:message key="label.solution_status"/></div>
                    <div class="mark"><fmt:message key="label.mark"/></div>
                    <div class="task-description"><fmt:message key="label.description"/></div>
                </div>
                <c:if test="${solutionTaskDtoList.isEmpty()}">
                    <div class="task-name"> <fmt:message key="label.no_solution"/></div>
                </c:if>
                <div class="wrapper">
                    <div class="table">
                        <div class="row header blue">
                            <div class="cell"><fmt:message key="label.task_name"/></div>
                            <div class="cell"><fmt:message key="label.description"/></div>
                            <div class="cell"></div>
                        </div>
                        <c:if test="${solutionTaskDtoList.isEmpty()}">
                            <div class="task-name"><fmt:message key="label.no_task"/></div>
                        </c:if>

                        <c:forEach var="task" items="${solutionTaskDtoList}">
                            <div class="row">
                                <div class="cell">${task.taskName}</div>
                                <div class="cell">${task.taskDescription}</div>
                                <c:if test="${user.role == UserRole.STUDENT}">
                                    <a onclick=
                                    <c:choose>
                                        <c:when test="${task.solutionId==null}">
                                            "openModal('send_solution')" class="tab-button"><fmt:message
                                                key="label.send_solution"/></a>
                                        </c:when>
                                        <c:otherwise>

                                            "openModal('${task.solutionId}')" class="tab-button graded" ><fmt:message
                                                key="label.check_solution"/></a>
                                        </c:otherwise>
                                    </c:choose>

                                </c:if>
                                <c:if test="${user.role == UserRole.ADMIN || user.role == UserRole.TEACHER}">
                                    <a class="tab-button" style="background: #7d2626; text-decoration: none"
                                       href="${pageContext.request.contextPath}/controller?command=deleteTask&taskId=${task.taskId}&courseId=${course.id}&pageIndex=${pageIndex}"><fmt:message
                                            key="label.delete_task"/></a>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <c:forEach var="solution" items="${solutionTaskDtoList}">
                    <div class="list-item">
                        <div class="task-name">${solution.taskName}</div>
                        <div class="status">${solution.solutionStatus}</div>
                        <div class="mark">${solution.solutionMark}</div>
                        <div class="task-description">${solution.taskDescription}</div>
                        <c:if test="${user.role == UserRole.ADMIN || user.role == UserRole.TEACHER &&
                         solution.solutionStatus != SolutionStatus.GRADED}">
                            <a class="tab-button" style="background: #5056a8eb; text-decoration: none"
                               onclick="openModal('${solution.solutionId}')"><fmt:message key="label.evaluate"/></a>
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