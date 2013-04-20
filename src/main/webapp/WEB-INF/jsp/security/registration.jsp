<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/css/bootstrap.css" />
    <link type="text/css" rel="stylesheet" href="/css/registration.css" />

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>

    <style>
        #register-form {
            background-color: #ffffff;
            border: 1px solid #DFDCDC;
            border-radius: 15px 15px 15px 15px;
            display: inline-block;
            padding: 25px 50px 25px;
            width: 270px;
        }

        #register-form .fieldgroup {
            display: inline-block;
            padding: 8px 10px;
            width: 270px;
        }

        #register-form .fieldgroup label {
            float: left;
            padding: 15px 0 0;
            text-align: right;
            width: 110px;
        }

        #register-form .fieldgroup input, #register-form .fieldgroup textarea, #register-form .fieldgroup select {
            height: 25px;
        }

        #register-form .submit {
            padding: 10px;
            width: 220px;
            height: 40px !important;
        }

        #register-form .fieldgroup label.error {
            color: #FB3A3A;
            display: inline-block;
            padding: 0;
            text-align: left;
            width: 220px;
        }
    </style>

    <script>
        (function($,W,D)
        {
            var JQUERY4U = {};

            JQUERY4U.UTIL =
            {
                setupFormValidation: function()
                {
                    $("#register-form").validate({
                        rules: {
                            firstName: "required",
                            email: {
                                required: true,
                                email: true
                            },
                            password: {
                                required: true,
                                minlength: 5
                            },

                            confirm: {
                                required: true
                            }
                        },
                        messages: {
                            firstName: "Please enter your firstname",
                            password: {
                                required: "Please provide a password",
                                minlength: "Your password must be at least 5 characters long"
                            },
                            confirm: {
                                required: "Please confirm the password"
                            },
                            email: "Please enter a valid email address"
                        },
                        submitHandler: function(form) {
                            form.submit();
                        }
                    });
                }
            }

            $(D).ready(function($) {
                JQUERY4U.UTIL.setupFormValidation();
            });

        })(jQuery, window, document);
    </script>

    <title>Registration</title>
</head>
<body>

<div class="container" style="text-align: center">

    <form action="/registration" method="post" id="register-form" novalidate="novalidate">

        <h2>User Registration</h2>

        <div id="form-content">
            <fieldset>

                <div class="fieldgroup">
                    <input type="text" name="firstName"
                           value="<c:out value='${form.firstName}'/>"
                           placeholder="First Name">
                </div>

                <div class="fieldgroup">
                    <input type="text" name="email"
                           value="<c:out value='${form.email}'/>"
                           placeholder="Email">
                    <div style="color: red"><c:out value="${mailError}" default=""/></div>
                </div>

                <div class="fieldgroup">
                    <input type="password" name="password" placeholder="Password">
                </div>

                <div class="fieldgroup">
                    <input type="password" name="confirm" placeholder="Confirm password">
                </div>

            </fieldset>
            <div style="padding-left: 10px">
                <input class="btn btn-large btn-primary" type="submit" value="Register" />
                <a href="/facebook/registration"><img id="fb_login" src="/img/facebook/facebook-icon-small.jpg" width="50" height="25"/></a>
                <span style="margin-left: 10px"><a href="/login">Sign in</a></span>
            </div>

        </div>
    </form>
</div>

</body>
</html>