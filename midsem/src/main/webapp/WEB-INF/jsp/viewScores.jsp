<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%@ include file="dark-mode-setup.jsp" %>
    <title>Your Past Scores</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Past Scores</h1>
    <table>
        <thead>
        <tr>
            <th>Quiz Title</th>
            <th>Score</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scoreDetails}" var="detail">
            <tr>
                <td>${detail.key.quiz.title}</td>
                <td>${detail.key.value}/${detail.value}</td> <!-- Show score as X/Y -->
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-primary">Return to Dashboard</a>

</div>
</body>
</html>
