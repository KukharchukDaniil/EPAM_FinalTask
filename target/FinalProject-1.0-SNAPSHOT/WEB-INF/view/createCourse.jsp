<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="https://epam.com/jsp/tlds/mytags" prefix="pagination" %>
<%@ page import="com.epam.entities.UserRole" %>
<fmt:setLocale value="${not empty sessionScope.locale? sessionScope.locale:'en_US'}" scope="session"/>
<fmt:setBundle basename="language" scope="session"/>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<!doctype html>
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
    <form action="${context}/controller" method="post" >
        <input type="hidden" name="command" value="createCourse">
        <input type="text" name="courseName" placeholder="<fmt:message key="label.course_name_placeholder"/>">
        <input type="text" name="courseDescription"
               placeholder="<fmt:message key="label.course_description_placeholder"/>">
        <select name="courseCategory">
            <option>BIOLOGY</option>
            <option>CHEMISTRY</option>
            <option>COMPUTER_SCIENCE</option>
            <option>MATH</option>
        </select>
        <input type="hidden" name="teachersList" id="teachersList">
        <div class="animated-btn">
            <input type="submit" name="create"/>
        </div>
    </form>
    <script>
        // function checkLocalStorage() {
        //     var checkboxesToCheck = localStorage.getItem("selectedUsers");
        //     if (checkboxesToCheck != null) {
        //         var usersList = checkboxesToCheck.split(",");
        //         for (let i = 0; i < usersList.length; i++) {
        //             var elem = document.getElementById(usersList[i]);
        //             if (elem != null) {
        //                 elem.checked = true;
        //             }
        //         }
        //
        //     }
        // }
        //
        // checkLocalStorage();
        //
        // function checkboxOnClick(id) {
        //     var storageInfo = localStorage.getItem("selectedUsers");
        //
        //     if (storageInfo != null) {
        //         var usersList = storageInfo.split(",");
        //     }
        //     if (usersList == null) {
        //         usersList = [];
        //     }
        //     if (document.getElementById(id).checked === true) {
        //
        //         console.log("pushing");
        //         usersList.push(id);
        //         uniqueList = [...new Set(usersList)];
        //         localStorage.setItem("selectedUsers", uniqueList);
        //
        //     } else {
        //         console.log("removing");
        //         var index = usersList.indexOf(id.toString());
        //         while (index > -1) {
        //             usersList.splice(index, 1);
        //             index = usersList.indexOf(id.toString());
        //         }
        //         localStorage.setItem("selectedUsers", usersList);
        //     }
        //
        // }
        //
        // function submitCourse() {
        //     console.log("submitting");
        //     var resultList = localStorage.getItem("selectedUsers");
        //     if (resultList !== null) {
        //         document.getElementById("teachersList").value = resultList.split(" ");
        //     }
        //     localStorage.clear();
        //     return true;
        // }


    </script>
</div>
</body>
</html>