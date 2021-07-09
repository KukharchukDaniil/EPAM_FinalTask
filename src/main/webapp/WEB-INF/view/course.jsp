<%@ page contentType="text/html;charset=utf-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pagination" uri="https://epam.com/jsp/tlds/mytags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.epam.ftask.entities.UserRole" %>
<%@ page import="com.epam.ftask.entities.SolutionStatus" %>
<c:set var="pageIndex" value="${not empty param.pageIndex?param.pageIndex : 1}"/>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<html>
<head>
    <meta charset='UTF-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="css/course.css" type="text/css"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div>
    <c:if test="${isEnrolled == false && user.role != UserRole.TEACHER && user.role != UserRole.ADMIN}">
        <div class="modal-pane" id="Enroll">
            <div class="modal">
                <div class="question"><fmt:message key="label.modal_question"/></div>
                <div class="answer-pane">
                    <a class="modal-button"
                       href="${pageContext.request.contextPath}/controller?command=enroll&courseId=${course.id}"><fmt:message
                            key="label.yes"/></a>
                    <a class="modal-button" onclick="closeModal('Enroll')"><fmt:message key="label.no"/></a>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${isEnrolled}">

        <div class="modal-pane" id="send_solution">
            <div class="modal">
                <form action="${pageContext.request.contextPath}/controller" method="post" class="solution-form">
                    <input type="hidden"  name="command" value="sendSolution"/>
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <select name="taskId">
                        <c:forEach var="task" items="${solutionTaskDtoList}">
                            <c:if test="${task.solutionStatus != SolutionStatus.GRADED && task.solutionStatus!= SolutionStatus.SENT}">
                                <option value="${task.taskId}">${task.taskName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <textarea type="text" name="solutionValue" placeholder="<fmt:message key="label.enter_solution"/>"
                              maxlength="300"></textarea>
                    <button type="submit"><fmt:message key="label.submit"/></button>
                </form>
                <button onclick="closeModal('send_solution')" class="modal-button" style="width: 100%"><fmt:message
                        key="label.close"/></button>
            </div>
        </div>
        <c:forEach items="${solutionTaskDtoList}" var="dto">
            <c:if test="${dto.solutionId != null}">
                <div class="modal-pane" id="${dto.solutionId}" style="display: none">
                    <div class="modal">
                        <div class="modal-block">
                            <div><fmt:message key="label.task_name"/>:</div>
                                ${dto.taskName}
                        </div>
                        <div class="modal-block">
                            <div><fmt:message key="label.description"/>:</div>
                                ${dto.taskDescription}
                        </div>
                        <div class="modal-block">
                            <div><fmt:message key="label.solution"/>:</div>
                                ${dto.solutionValue}
                        </div>
                        <div class="modal-block">
                            <div><fmt:message key="label.status"/>:</div>
                                ${dto.solutionStatus}
                        </div>
                        <c:if test="${dto.solutionStatus == SolutionStatus.GRADED}">
                            <div class="modal-block">
                                <div><fmt:message key="label.comment"/>:</div>
                                    ${dto.solutionComment}
                            </div>
                            <div class="modal-block">
                                <div><fmt:message key="label.mark"/>:</div>
                                    ${dto.solutionMark}
                            </div>
                        </c:if>
                        <a class="modal-button" onclick="closeModal(${dto.solutionId})"><fmt:message
                                key="label.close"/></a>
                    </div>

                </div>
            </c:if>
        </c:forEach>
        <div class="modal-pane" id="add-task-modal" style="display: none">
            <div class="modal">
                <form action="${pageContext.request.contextPath}/controller" method="post" class="add-task-form">
                    <input type="hidden" name="command" value="addTask"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <input type="text" name="taskName" required placeholder="<fmt:message key="label.task_name"/>.."/>
                    <textarea type="text" pattern="[^<>/]" name="taskDescription" required
                              placeholder="<fmt:message key="label.description"/>"></textarea>
                    <button type="submit" value="Submit"><fmt:message key="label.submit"/></button>
                </form>
                <button onclick="closeModal('add-task-modal')" class="modal-button"
                        style="min-width: 100px; width: auto"><fmt:message key="label.close"/>
                </button>
            </div>

        </div>
    </c:if>

    <div class="main-contents">
        <div class="menu">
            <a class="menu-button" onclick="openTab(event, 'About')"><fmt:message key="label.about_course"/></a>
            <c:choose>
                <c:when test="${isEnrolled == true}">

                    <c:if test="${user.role == UserRole.TEACHER}">
                        <a class="menu-button"
                           href="${pageContext.request.contextPath}/controller?command=showSolutions&courseId=${course.id}"><fmt:message
                                key="label.solutions"/></a>
                    </c:if>
                    <a class="menu-button" onclick="openTab(event, 'Tasks')" id="defaultOpen"><fmt:message
                            key="label.tasks"/></a>
                </c:when>
                <c:otherwise>
                    <c:if test="${user.role != UserRole.TEACHER && user.role != UserRole.ADMIN}">
                        <a class="menu-button" onclick="openModal('Enroll')"><fmt:message key="label.enroll"/></a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="contents">
            <div class="tabcontent" id="About">
                <div class="name" id="odd">${course.courseName}</div>
                <div class="description">${course.description}</div>
            </div>
            <c:if test="${isEnrolled == true}">
                <div class="tabcontent" id="Tasks">
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
                                        <a class="tab-button" style="background: #d65656; text-decoration: none"
                                           href="${pageContext.request.contextPath}/controller?command=deleteTask&taskId=${task.taskId}&courseId=${course.id}&pageIndex=${pageIndex}"><fmt:message
                                                key="label.delete_task"/></a>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <c:if test="${totalItems > 5}">
                        <div class="pagination">
                            <pagination:load
                                    uri="${pageContext.request.contextPath}/controller?command=showCourse&courseId=${course.id}"
                                    totalItems="${totalItems}"
                                    currentPage="${pageIndex}"/>
                        </div>
                    </c:if>
                    <c:if test="${user.role == UserRole.TEACHER}">
                        <a class="add-task-button" onclick="openModal('add-task-modal')">
                            <fmt:message key="label.add_new_task"/>
                        </a>
                    </c:if>
                </div>
            </c:if>

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
    closeModal('send_solution')
</script>
</body>

</html>