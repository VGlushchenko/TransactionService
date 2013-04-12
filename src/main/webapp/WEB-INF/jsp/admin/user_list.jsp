<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>List users</title>
    <link href="css/bootstrap.css" rel="stylesheet" media="screen">

    <script src="/js/jquery-1.9.1.js"></script>
    <script src="/js/login.js"></script>
    <script src="/js/bootstrap.js"></script>

    <style>
        tr {
            height: 20px;
        }
    </style>
</head>
<body>

    <legend>
        <span><c:out value="${user.username}" /></span>
        <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
    </legend>


    <ul class="nav nav-tabs">
        <li><a href="/transaction/create/creditfromcard">Cash-out</a></li>
        <li><a>Send money</a></li>
        <li class="history"><a href="/transaction/history">History</a></li>
        <sec:authorize ifAllGranted="ROLE_ADMIN">
            <li class="dropdown active">
                <a class="dropdown-toggle" data-toggle="dropdown"
                   href="#">
                    Admin options
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/users">Show all users</a></li>
                    <li><a href="/admin/transaction/list" >Show all transactions</a></li>
                </ul>
            </li>
        </sec:authorize>
        <li style="float: right">Balance: <c:out value="${user.balance}" />$</li>
    </ul>

    <div style="float: left; width: 50%">
    <table class="table table-striped" style="width: 600px">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Is Enabled</th>
                <th>Options</th>
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
                                <input value="Disable" onClick="location.href='./user/disable/' +
                                    '<c:out value="${user.id}"/>'" type="button" class="btn btn-small btn-danger"/>
                            </c:if>
                            <c:if test="${user.enabled == false}">
                                <input value="Enable" onClick="location.href='./user/enable/' +
                                    '<c:out value="${user.id}"/>'" type="button" class="btn btn-small btn-success"/>
                            </c:if>
                                <a href="users/<c:out value="${user.id}"/>/transactions" class="btn btn-small">Transactions</a>
                         </td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
    </div>

</body>
</html>