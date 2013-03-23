<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
    <title>Sign in</title>
    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
        .form-signin {
            max-width: 300px;
            padding: 19px 29px 29px;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
            box-shadow: 0 1px 2px rgba(0,0,0,.05);
        }
        .form-signin input[type="text"],
        .form-signin input[type="password"] {
            font-size: 16px;
            height: auto;
            margin-bottom: 15px;
            padding: 7px 9px;
        }
        .error {
            color: red;
        }
    </style>

</head>
<body>
<div class="container">
    <form method="post" action="/j_spring_security_check" class="form-signin" >
        <h2>Sign in</h2>

        <input type="text" name="j_username" class="input-block-level" placeholder="Email" value="${email}"/>
        <span class="error">${error}</span>
        <input type="password" name="j_password" class="input-block-level" placeholder="Password" />

        <input class="btn btn-large btn-primary" type="submit" value="Log in" />

        <a href="/facebook/registration"><img id="fb_login" src="/img/facebook/facebook-icon-small.jpg" width="50" height="25"/></a>

        <span style="padding:0px 10px;"><a href="/registration">Registration</a></span>
    </form>
</div>

</body>
</html>
