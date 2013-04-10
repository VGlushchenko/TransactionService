<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Add credit card</title>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css">

    <script src="/js/jquery-1.9.1.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>
<body>

    <legend>
        <span><c:out value="${user.username}" /></span>
        <span style="float: right"><a href="<c:url value="j_spring_security_logout" />" >Logout</a></span>
    </legend>

    <ul class="nav nav-tabs">
        <li class="active"><a href="/transaction/create/creditfromcard">Cash-out</a></li>
        <li><a href="/transaction/create">Send money</a></li>
        <li><a href="/transaction/history">History</a></li>
        <sec:authorize ifAllGranted="ROLE_ADMIN">
            <li class="dropdown">
                <a class="dropdown-toggle"
                   data-toggle="dropdown"
                   href="#">
                    Admin options
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="/transaction/create">Main</a></li>
                    <li><a href="/users">Show all users</a></li>
                    <li><a href="/admin/transaction/list" >Show all transactions</a></li>
                </ul>
            </li>
            <%--<li><a href="/admin">Admin panel</a></li>--%>
        </sec:authorize>
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