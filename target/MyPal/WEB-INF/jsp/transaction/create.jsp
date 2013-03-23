<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:sec="http://www.springframework.org/security/tags">
    <head>
        <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
    </head>
    <body>
        <legend>
            <span><c:out value="${user.email}" /></span>
            <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
        </legend>

        <ul class="nav nav-tabs">
            <li><a href="/transaction/create/creditfromcard">Pay</a></li>
            <li><a href="/transaction/create/debitedtothecard">Cash-out</a></li>
            <li class="active"><a href="/transaction/create">Send money</a></li>
            <li><a href="/transaction/history">History</a></li>
            <sec:authorize ifAllGranted="ROLE_ADMIN">
                <li><a href="/admin">Admin panel</a></li>
            </sec:authorize>
            <li style="float: right">Balance: <c:out value="${user.balance}" />$</li>
        </ul>

        <c:if test="${!transactionComplete && transactionComplete!=null}"><h4 style="color: red">Can not create transaction!</h4></c:if>

        <form method="post" action="/transaction/create">
            Debit email:<br>
            <input name="debit" type="text" class="input-large" placeholder="Email"><br>
             Sum: <br>
            <input name="sum" type="text" class="input-large" placeholder="Sum"><br>
             <button type="submit" class="btn">Create</button>
        </form>
    </body>
</html>
