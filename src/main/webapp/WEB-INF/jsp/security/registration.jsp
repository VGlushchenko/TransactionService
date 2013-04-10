<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
    <link type="text/css" rel="stylesheet" href="/css/registration.css" />

    <title>Registration</title>
</head>
<body>

<div class="container">
    <form:form method="post" action="/registration" commandName="registrationForm" class="form-signin" >
        <h2>Registration</h2>

        <form:errors path="firstName" class="error" />
        <form:input id="first_name" path="firstName" placeholder="First name" class="input-block-level" /><br/>

        <form:errors path="email" class="error" />
        <span class="error">${email_error}</span>
        <form:input id="email" path="email" placeholder="Email address" class="input-block-level" /><br/>

        <form:errors path="password" class="error" />
        <form:password path="password" placeholder="Password" class="input-block-level" /><br/>

        <form:errors path="confirm" class="error" />
        <form:password path="confirm" placeholder="Confirm password" class="input-block-level" /><br/>

        <input class="btn btn-large btn-primary" type="submit" value="Register" />
        <a href="/facebook/registration"><img id="fb_login" src="/img/facebook/facebook-icon-small.jpg" width="50" height="25"/></a>
    </form:form>
</div>

</body>
</html>