<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
</head>
<body>

    <legend>
        <span><c:out value="${user.email}" /></span>
        <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
    </legend>

    <ul class="nav nav-tabs">
        <li class="active"><a>Users and transactions</a></li>
        <li><a href="/users" >Show all users</a></li>
        <li><a href="/admin/transaction/list" >Show all transactions</a></li>
    </ul>

</body>
</html>