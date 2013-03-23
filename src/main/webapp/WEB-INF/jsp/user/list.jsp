<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List users</title>
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">
</head>
<body>

    <legend>
        <span><c:out value="${user.email}" /></span>
        <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
    </legend>

    <ul class="nav nav-tabs">
        <li><a href="/admin">Users and transactions</a></li>
        <li class="active"><a>Show all users</a></li>
        <li><a href="/admin/transaction/list" >Show all transactions</a></li>
    </ul>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>User first name</th>
                <th>Email</th>
                <th>Is Enabled</th>
                <th>ACTIONS</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${userlist}">
                <c:if test="${user.id > 0}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.enabled}"/></td>
                        <td style="width: 200px">
                            <c:if test="${user.enabled == true}">
                                <a href="/user/ban/<c:out value="${user.id}"/>" class="btn-warning">Ban</a>
                            </c:if>
                            <c:if test="${user.enabled == false}">
                                <a href="/user/unban/<c:out value="${user.id}"/>" class="btn-inverse">UnBan</a>
                            </c:if>
                                <a href="users/<c:out value="${user.id}"/>/transactions" class="btn">Transactions</a>
                         </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>