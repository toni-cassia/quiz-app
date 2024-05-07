<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quiz Score</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%@ include file="dark-mode-setup.jsp" %>
</head>
<body>
<div class="container mt-3">
    <h2>Quiz Completed</h2>
    <p>Congratulations! You have completed the quiz.</p>
    <p>Your Score: ${score}  / ${quizCount}</p>
    <a href="${pageContext.request.contextPath}/user/dashboard" class="btn btn-primary">Return to Dashboard</a>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
