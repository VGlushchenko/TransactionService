<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Add credit card</title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css">
</head>
<body>

    <legend>
        <span><c:out value="${user.email}" /></span>
        <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
    </legend>

    <ul class="nav nav-tabs">
        <li class="active"><a href="/transaction/create/creditfromcard">Pay</a></li>
        <li><a href="/transaction/create/debitedtothecard">Cash-out</a></li>
        <li><a href="/transaction/create">Send money</a></li>
        <li><a href="/transaction/history">History</a></li>
        <li style="float: right">Balance: <c:out value = "${user.balance}" />$</li>
    </ul>

    <form class="form-horizontal" method="post" action="/transaction/create/creditfromcard">

        <div class="control-group">
            <label class="control-label" for="cardId">
                <p>
                    Card
                </p>
            </label>
            <div class="controls">
                <select id="cardId" name="card">
                    <c:forEach var="card" items="${cards}">
                        <option value="<c:out value="${card.id}" />"><c:out value="${card.name}" /></option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="sumid">
                <p>
                    Sum
                </p>
            </label>
            <div class="controls">
                <input type="text" id="sumid" name="sum" placeholder="Sum">
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn">Get money</button>
            </div>
        </div>
    </form>
</body>
</html>