<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- add_options.jsp -->
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Options</title>
    <%@ include file="dark-mode-setup.jsp" %>
</head>
<body>
<h1>Add Options to Question</h1>
<form action="${pageContext.request.contextPath}/admin/questions/${questionId}/options" method="post">
    <input type="hidden" name="questionId" value="${questionId}">
    <label for="option1">Option 1:</label>
    <input type="text" id="option1" name="option1"><br>
    <label for="option2">Option 2:</label>
    <input type="text" id="option2" name="option2"><br>
    <label for="option3">Option 3:</label>
    <input type="text" id="option3" name="option3"><br>
    <label for="correctOption">Correct Option:</label>
    <select id="correctOption" name="correctOption">
        <option value="0">Option 1</option>
        <option value="1">Option 2</option>
        <option value="2">Option 3</option>
    </select><br>
    <input type="submit" value="Add Options">
</form>
</body>
</html>
