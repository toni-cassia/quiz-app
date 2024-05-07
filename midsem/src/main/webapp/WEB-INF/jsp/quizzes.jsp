<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Quizzes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <%@ include file="dark-mode-setup.jsp" %>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light justify-content-end">
    <form class="form-inline" action="${pageContext.request.contextPath}/logout" method="get">
        <button class="btn btn-primary my-2 my-sm-0" type="submit">Logout</button>
    </form></nav>

<div class="container mt-3">
    <h2>Quiz List</h2>
    <!-- Link to create new quiz -->
    <a href="${pageContext.request.contextPath}/admin/quizzes/new" class="btn btn-primary mb-3">Create New Quiz</a>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>Description</th>
            <th>Duration (minutes)</th>
            <th>Questions</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% int i = 1;%>
        <c:forEach  var="quiz" items="${quizzes}" >
            <tr>
                <td><%=i++%></td>
                <td>${quiz.title}</td>
                <td>${quiz.description}</td>
                <td>${quiz.duration}</td>
                <td>${fn:length(quiz.questions)}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/quizzes/${quiz.id}/questions/new" class="btn btn-sm btn-primary">Add Questions</a>
                    <form action="${pageContext.request.contextPath}/admin/quizzes/${quiz.id}/delete" method="post" style="display: inline;">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>
