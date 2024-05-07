<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Signup</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f8f9fa; /* Set background color */
        }

        .form-container {
            width: 400px;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-container h2 {
            margin-bottom: 20px;
            text-align: center;
        }

        .form-container label {
            font-weight: bold;
        }

        .form-container input[type="email"],
        .form-container input[type="text"],
        .form-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .form-container input[type="submit"] {
            display: block;
            width: auto; /* Adjusted width */
            margin: 0 auto; /* Center the button */
            padding: 10px 20px; /* Adjusted padding */
            text-align: center;
            color: #fff;
            background-color: #007bff; /* Button color */
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-container a {
            display: block;
            width: fit-content; /* Adjusted width */
            margin: 0 auto; /* Center the link */
            padding: 10px;
            text-align: center;
            color: #007bff; /* Link color */
            text-decoration: underline; /* Underline the link */
            cursor: pointer;
        }

        .form-container a:hover {
            text-decoration: none; /* Remove underline on hover */
        }
    </style>

</head>
<body>
<div class="form-container">
    <form action="/processsignup" method="post">
        <h2>Signup</h2>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" class="btn btn-primary" value="SignUp">
        <a href="/login">Log in</a>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
