<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.css"/>

        <script src="/js/jquery-1.9.1.js"></script>
        <script src="/js/jquery.ba-outside-events.min.js"></script>
        <script src="/js/history.js"></script>
        <script src="/js/bootstrap.js"></script>

        <script>
            $(document).ready(function() {
                HISTORY.initFunctions();
            });
        </script>
    </head>
    <body>

        <legend>
            <span><c:out value="${user.username}" /></span>
            <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
        </legend>

        <ul class="nav nav-tabs">
            <li><a href="/transaction/create/creditfromcard">Cash-out</a></li>
            <li><a href="/transaction/create">Send money</a></li>
            <li class="active"><a href="/transaction/history">History</a></li>
            <sec:authorize ifAllGranted="ROLE_ADMIN">
                <li class="dropdown">
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

      <%--  <table class="table table-striped">

            <tr>
                <th>ID</th>
                <th>Debit account</th>
                <th>Credit account</th>
                <th>Sum</th>
            </tr>

            <c:forEach var="transaction" items="${transactions}">
                <tr>
                    <td>${transaction.id}</td>
                    <td>${transaction.credit.email}</td>
                    <td>${transaction.debit.email}</td>
                    <td>${transaction.sum}</td>
                </tr>
            </c:forEach>

        </table>--%>


        <div id="user-transactions" class="table table-striped" style="height: 457px; position: relative">
            <table id="transaction-table">
                <colgroup>
                    <col width="300px"/>
                    <col width="300px"/>
                    <col width="25px"/>
                    <col width="25px"/>
                </colgroup>
                <thead>
                <tr>
                    <th>Debit user</th>
                    <th>Credit user</th>
                    <th>Status</th>
                    <th>Sum</th>
                </tr>
                </thead>
                <tbody class="transactions"></tbody>
            </table>
            <div class="pagination" style="text-align: center; position: absolute; top: 430px; width: 100%;">
                <ul>
                    <li class="disabled page-before"><a href="#">&laquo;</a></li>
                    <li class="page-forward"><a href="#">»</a></li>
                </ul>
            </div>
        </div>
    </body>
</html>
