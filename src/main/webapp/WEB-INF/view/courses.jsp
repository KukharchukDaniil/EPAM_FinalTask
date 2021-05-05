<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Main</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel="stylesheet" href="main.css"/>
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
    <a class="active" href="${pageContext.request.contextPath}/controller?command=myCourses">
        My courses
    </a>
    <a>
        Find course
    </a></nav>
<div>
    <jsp:include page="courses_content.jsp"></jsp:include>
</div>
</body>

</html>