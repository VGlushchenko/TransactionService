<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>

    <script src="/js/jquery-1.9.1.js"></script>
    <script src="/js/bootstrap.js"></script>
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

    <table class="table table-striped">

        <tr>
            <th>Debit account</th>
            <th>Credit account</th>
            <th>Sum</th>
            <th>Status</th>
            <th>Options</th>
        </tr>

        <c:forEach var="transaction" items="${transactions}">
            <tr>
                <td>${transaction.credit.email}</td>
                <td>${transaction.debit.email}</td>
                <td>${transaction.sum}</td>
                <td>${transaction.status}</td>
                <td>
                    <c:if test="${transaction.id > 0}">
                        <c:if test="${transaction.status.equals('in progress')}">
                                <a href="/transaction/<c:out value="${transaction.id}"/>/cancel">
                                    <button class="btn btn-small btn-danger">Rollback</button>
                                </a>
                        </c:if>
                        <c:if test="${transaction.status.equals('completed') || transaction.status.equals('failed')}">
                                <button class="btn btn-small disabled btn-danger">Rollback</button>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>
