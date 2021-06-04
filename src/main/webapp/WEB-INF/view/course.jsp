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
    <c:if test="${isEnrolled == false}">
        <div class="modal-pane" id="Enroll">
            <div class="modal">
                <div class="question">Question</div>
                <div class="answer-pane">
                    <a class="modal-button"
                       href="${pageContext.request.contextPath}/controller?command=enroll&courseId=${course.id}">Yes</a>
                    <a class="modal-button" onclick="closeModal('Enroll')">No</a>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${isEnrolled}">

        <div class="modal-pane" id="send_solution">
            <div class="modal">
                <form action="${pageContext.request.contextPath}/controller" method="post" class="solution-form">
                    <input type="hidden" name="command" value="sendSolution"/>
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <select name="taskId">
                        <c:forEach var="task" items="${solutionTaskDtoList}">
                            <c:if test="${task.solutionStatus != SolutionStatus.GRADED && task.solutionStatus!= SolutionStatus.SENT}">
                                <option value="${task.taskId}">${task.taskName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <textarea type="text" name="solutionValue" placeholder="Type here your solution"
                              maxlength="300"></textarea>
                    <button type="submit">Submit</button>
                </form>
                <button onclick="closeModal('send_solution')" class="modal-button" style="width: 100%">Close</button>
            </div>
        </div>
        <c:forEach items="${solutionTaskDtoList}" var="dto">
            <c:if test="${dto.solutionId != null}">
                <div class="modal-pane" id="${dto.solutionId}" style="display: none">
                    <div class="modal">
                        <div class="modal-block">
                            <div>Task name:</div>
                                ${dto.taskName}
                        </div>
                        <div class="modal-block">
                            <div>Description:</div>
                                ${dto.taskDescription}
                        </div>
                        <div class="modal-block">
                            <div>Solution:</div>
                                ${dto.solutionValue}
                        </div>
                        <div class="modal-block">
                            <div>Status:</div>
                                ${dto.solutionStatus}
                        </div>
                        <c:if test="${dto.solutionStatus == SolutionStatus.GRADED}">
                            <div class="modal-block">
                                <div>Comment:</div>
                                    ${dto.solutionComment}
                            </div>
                            <div class="modal-block">
                                <div>Mark:</div>
                                    ${dto.solutionMark}
                            </div>
                        </c:if>
                        <a class="modal-button" onclick="closeModal(${dto.solutionId})">Close</a>
                    </div>

                </div>
            </c:if>
        </c:forEach>
        <div class="modal-pane" id="add-task-modal" style="display: none">
            <div class="modal">
                <form action="${pageContext.request.contextPath}/controller" method="post" class="add-task-form">
                    <input type="hidden" name="command" value="addTask"/>
                    <input type="hidden" name="courseId" value="${course.id}"/>
                    <input type="text" name="taskName" required placeholder="Task name.."/>
                    <textarea type="text" name="taskDescription" required placeholder="Description"></textarea>
                    <button type="submit" value="Submit">Submit</button>
                </form>
                <button onclick="closeModal('add-task-modal')" class="modal-button"
                        style="min-width: 100px; width: auto">Close
                </button>
            </div>

        </div>
    </c:if>

    <div class="main-contents">
        <div class="menu">
            <a class="menu-button" onclick="openTab(event, 'About')">About course</a>
            <c:choose>
                <c:when test="${isEnrolled == true}">

                    <c:if test="${user.role == UserRole.TEACHER || user.role == UserRole.ADMIN}">
                        <a class="menu-button"
                           href="${pageContext.request.contextPath}/controller?command=showSolutions&courseId=${course.id}">Solutions</a>
                    </c:if>
                    <a class="menu-button" onclick="openTab(event, 'Tasks')" id="defaultOpen">Tasks</a>
                </c:when>
                <c:otherwise>
                    <a class="menu-button" onclick="openModal('Enroll')">Enroll</a>
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
                    <div class="list-item">
                        <div class="task-name">Task name</div>
                        <div class="task-description">Task description</div>
                    </div>
                    <c:if test="${solutionTaskDtoList.isEmpty()}">
                        <div class="task-name"> No tasks available</div>
                    </c:if>

                    <c:forEach var="task" items="${solutionTaskDtoList}">
                        <div class="list-item">
                            <div class="task-name">${task.taskName}</div>
                            <div class="task-description">${task.taskDescription}</div>
                            <c:if test="${user.role == UserRole.STUDENT}">
                                <a class="tab-button" onclick=
                                <c:choose>
                                    <c:when test="${task.solutionId==null}">
                                        "openModal('send_solution')">Send solution</a>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${task.solutionStatus == SolutionStatus.SENT}">
                                                "openModal('${task.solutionId}')">Check solution</a>
                                            </c:when>
                                            <c:otherwise>"openModal('${task.solutionId}')"> Check solution</a></c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${user.role == UserRole.ADMIN || user.role == UserRole.TEACHER}">
                                <a class="tab-button" style="background: #7d2626; text-decoration: none"
                                   href="${pageContext.request.contextPath}/controller?command=deleteTask&taskId=${task.taskId}&courseId=${course.id}&pageIndex=${pageIndex}">Delete
                                    task</a>
                            </c:if>
                        </div>
                    </c:forEach>
                    <c:if test="${totalItems > 5}">
                        <div class="pagination">
                            <pagination:load
                                    uri="${pageContext.request.contextPath}/controller?command=showCourse&courseId=${course.id}"
                                    totalItems="${totalItems}"
                                    currentPage="${pageIndex}"/>
                        </div>
                    </c:if>
                    <a class="add-task-button" onclick="openModal('add-task-modal')">
                        Add new task
                    </a>
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