<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Founds transfer</title>
        <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
    </head>
    <body>
        <div class="container">
            <h4>New transfer.</h4>
            <form method="post" action="/founds/transfer/add">
                <input type="email" placeholder="example@mail.com" name="email">
                <input type="text" placeholder="transfer value" name="transfer_value"></br>
                <input class="btn" type="submit" value="Submit"/>
                <input class="btn" type="reset" value="Reset"/>
            </form>
        </div>
    </body>
</html>