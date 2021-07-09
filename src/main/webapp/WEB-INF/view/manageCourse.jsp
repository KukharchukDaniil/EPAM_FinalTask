<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="https://epam.com/jsp/tlds/mytags" prefix="pagination" %>
<%@ page import="com.epam.ftask.entities.UserRole" %>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="css/course_create.css">
    <title>Document</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <form action="${context}/controller" method="post" onsubmit="submitCourse()">
        <a href="${context}/controller?command=deleteCourse&courseId=${param.courseId}"><fmt:message
                key="label.delete_course"/></a>
        <input type="hidden" name="command" value="manageCourse">

        <input name="courseName" placeholder="<fmt:message key="label.course_name_placeholder"/>" type="text"
               value="${courseName}">


        <input name="courseDescription" placeholder="<fmt:message key="label.course_description_placeholder"/>"
               type="text"
               value="${courseDescription}">


        <select name="courseCategory">
            <option>BIOLOGY</option>
            <option>CHEMISTRY</option>
            <option>COMPUTER_SCIENCE</option>
            <option>MATH</option>
        </select>

        <input type="hidden" name="checkboxesToCheck" id="checkboxesToCheck" value="${checkboxesToCheck}">
        <input type="hidden" name="teachersListToEnroll" id="teachersListToEnroll">
        <input type="hidden" name="teachersListToRemove" id="teachersListToRemove">
        <input type="hidden" name="courseId" value="${param.courseId}">
        <div class="wrapper">

            <div class="table">

                <div class="row header">
                    <div class="cell">
                        Name
                    </div>
                    <div class="cell">
                        Username
                    </div>
                    <div class="Check">
                    </div>
                </div>
                <c:forEach items="${usersList}" var="listItem">
                    <div class="row">
                        <div class="cell" data-title="Name">
                                ${listItem.name}
                        </div>
                        <div class="cell" data-title="Username">
                                ${listItem.username}
                        </div>
                        <div class="cell" data-title="Teacher">

                            <input type="checkbox" ch name="selectedUser" value="${listItem.id}" id="${listItem.id}"
                                   onclick="checkboxOnClick(${listItem.id})">
                        </div>
                    </div>

                </c:forEach>


            </div>
            <div class="pagination">
                <pagination:load uri="${context}/controller?command=showManageCourse&courseId=${param.courseId}"
                                 totalItems="${totalItems}"
                                 currentPage="${pageIndex}"/>
            </div>
        </div>
        <div class="animated-btn">
            <input type="submit" name="create"/>
        </div>
    </form>
    <script>
        function checkLocalStorage() {
            var checkboxesToCheck = localStorage.getItem("teachersListToEnroll");
            if (checkboxesToCheck != null) {
                var usersList = checkboxesToCheck.split(",");
                for (let i = 0; i < usersList.length; i++) {
                    var elem = document.getElementById(usersList[i]);
                    if (elem != null) {
                        elem.checked = true;
                    }
                }

            }
        }

        function checkDefault() {
            var checkboxesToCheck = document.getElementById("checkboxesToCheck");
            var checkboxList = checkboxesToCheck.value.split(",");
            if (checkboxList != null) {
                for (let i = 0; i < checkboxList.length; i++) {
                    var element = document.getElementById(checkboxList[i]);
                    if(element!=null){
                    element.click()
                    }
                }
            }
        }
        checkDefault();
        checkLocalStorage();


        function checkboxOnClick(id) {
            if (document.getElementById(id).checked === true) {

                putIntoStorage("teachersListToEnroll", id);
                removeFromStorage("teachersListToRemove", id)
            } else {
                putIntoStorage("teachersListToRemove", id);
                removeFromStorage("teachersListToEnroll", id)
            }

        }

        function removeFromStorage(storageCell, id) {
            var storageInfo = localStorage.getItem(storageCell);

            if (storageInfo != null) {
                var usersList = storageInfo.split(",");
            }
            if (usersList == null) {
                usersList = [];
            }
            var index = usersList.indexOf(id.toString());
            while (index > -1) {
                usersList.splice(index, 1);
                index = usersList.indexOf(id.toString());
            }
            localStorage.setItem(storageCell, usersList);
        }

        function putIntoStorage(storageCell, id) {
            var storageInfo = localStorage.getItem(storageCell);

            if (storageInfo != null) {
                var usersList = storageInfo.split(",");
            }
            if (usersList == null) {
                usersList = [];
            }
            usersList.push(id);
            var uniqueList = [...new Set(usersList)];
            localStorage.setItem(storageCell, uniqueList);
        }

        function loadAttribute(destination){
            var resultList = localStorage.getItem(destination);
            if (resultList !== null) {
                document.getElementById(destination).value = resultList.split(",");
            }
        }
        function submitCourse() {
            loadAttribute("teachersListToEnroll");
            loadAttribute("teachersListToRemove");
            localStorage.clear();
            return true;
        }


    </script>
</div>
</body>
</html>