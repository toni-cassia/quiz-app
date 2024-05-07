<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Question</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%@ include file="dark-mode-setup.jsp" %>
</head>
<body>
<div class="container mt-3">
    <h2>Add Question to Quiz ${quiz_name}</h2>
<%--    http://localhost:8080/admin/quizzes/2/admin/quizzes--%>
<%--    /quizzes/{quizId}/admin/quizzes--%>
<%--    http://localhost:8080/admin/quizzes/1/admin/admin/quizzes--%>
<%--    /quizzes/{quizId}/admin/quizzes--%>
<%--    /quizzes/questions/{quizId}--%>
<%--<jsp:useBean id="quizzes" scope="request" type="java.util.List"/>--%>
<%--<c:forEach  var="quiz" items="${quizzes}" >--%>
<%--    <h3>Current Number of Questions: ${fn:length(quiz.questions)}</h3>--%>
<%--</c:forEach>--%>
    <h3 id="questionCountDisplay">Question: ${questionCount}</h3>
    <form action="/admin/quizzes/questions/${quizId}" method="post">

        <div class="form-group">
            <label for="questionText">Question Text:</label>
            <input type="text" class="form-control" id="questionText" name="questionText" required>
        </div>

        <!-- Dynamic input fields for options -->
        <c:forEach var="i" begin="1" end="3">
            <div class="form-group">
                <label for="option${i}">Option ${i}:</label>
                <input type="text" class="form-control" name="options" id="option${i}" required>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="correctOption" id="correctOption${i}" value="${i - 1}">
                <label class="form-check-label" for="correctOption${i}">
                    This is the correct answer
                </label>
            </div>
        </c:forEach>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <a href="${pageContext.request.contextPath}/admin/quizzes"><button>Cancel</button></a>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
