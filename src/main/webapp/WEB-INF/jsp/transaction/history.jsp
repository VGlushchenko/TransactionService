<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.css"/>
    </head>
    <body>

        <legend>
            <span><c:out value="${user.email}" /></span>
            <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
        </legend>

        <ul class="nav nav-tabs">
            <li><a href="/transaction/create/creditfromcard">Pay</a></li>
            <li><a href="/transaction/create/debitedtothecard">Cash-out</a></li>
            <li><a href="/transaction/create">Send money</a></li>
            <li class="active"><a href="/transaction/history">History</a></li>
            <li style="float: right">Balance: <c:out value="${user.balance}" />$</li>
        </ul>

        <table class="table table-striped">

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

        </table>

    </body>
</html>
