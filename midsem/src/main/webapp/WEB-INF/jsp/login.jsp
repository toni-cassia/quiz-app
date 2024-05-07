<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%@ include file="dark-mode-setup.jsp" %>  <!-- Include the Dark Mode Configuration -->
    <style>
        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Adjust this value as needed */
        }

        .login-box {
            width: 300px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>

<div class="login-container">
    <div class="login-box">
        <!-- Display flash messages if any -->
        <c:if test="${not empty errorMessage}">
            <div style="color: red;">
                    ${errorMessage}
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div style="color: green;">
                    ${successMessage}
            </div>
        </c:if>

        <form action="/processlogin" method="post">
            <h2 class="text-center">Sign In</h2>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="darkModeToggle" name="darkMode" onchange="handleDarkMode(this.checked)">
                <label class="form-check-label" for="darkModeToggle">Dark Mode</label>
            </div>
u
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                <label class="form-check-label" for="rememberMe">Remember Me</label>
                <input type="hidden" id="rememberMeHidden" name="rememberMeHidden" value="false">

            </div>

            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
            <div class="text-center mt-3">
                <a href="/">Sign Up</a>
            </div>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    window.onload = function() {
        var rememberMeCheckbox = document.getElementById("rememberMe");
        var emailInput = document.getElementById("username"); // Assuming your username input field has an ID of "username"
        var passwordInput = document.getElementById("password");

        if (document.cookie.includes("rememberMe")) {
            rememberMeCheckbox.checked = true;
            var cookieValue = document.cookie.replace(/(?:(?:^|;\s*)rememberMe\s*\=\s*([^;]*).$)|^.*$/, "$1");
            var email = cookieValue.split(":")[0];
            var password = cookieValue.split(":")[1];
            emailInput.value = email;
            passwordInput.value = password;
        }
    };
</script>

</body>
</html>
