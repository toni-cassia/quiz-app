<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${question.text}</title>
    <%@ include file="dark-mode-setup.jsp" %>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Custom styles */
        .container {
            margin-top: 50px;
        }
        #timer {
            margin-bottom: 20px;
        }
        /* Add more custom styles as needed */
    </style>

    <script>
        // Wait until the document is fully loaded
        document.addEventListener('DOMContentLoaded', function() {
            var duration = ${sessionScope.quizDuration} * 60; // assuming duration is in minutes, convert to seconds
            var timerDisplay = document.getElementById('timer');

            var timer = setInterval(function() {
                var minutes = parseInt(duration / 60, 10);
                var seconds = duration % 60;
                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                // Update the display with the remaining time
                timerDisplay.textContent = 'Time left: ' + minutes + ':' + seconds;

                if (--duration < 0) {
                    clearInterval(timer);
                    document.getElementById('quizForm').submit(); // Submit the form when time runs out
                }
            }, 1000);
        });
    </script>
</head>
<body>
<div class="container">
    <div id="timer" class="alert alert-warning">Time left: </div>
    <div>
        Question ${currentQuestionIndex} of ${totalQuestions}
    </div>
    <h2>${question.text}</h2>

    <form action="${pageContext.request.contextPath}/user/quizzes/question/answer" method="post">
        <c:forEach items="${question.options}" var="option">
            <div class="form-check">
                <input type="radio" id="option${option.id}" name="answer" value="${option.id}" class="form-check-input">
                <label for="option${option.id}" class="form-check-label">${option.text}</label>
            </div>
        </c:forEach>
        <button type="submit" class="btn btn-primary mt-3">Submit Answer</button>
    </form>
    <a href="${pageContext.request.contextPath}/user/quizzes/quit" class="btn btn-danger mt-2">Quit Quiz</a>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    var visibilityChange;
    if (typeof document.hidden !== "undefined") {
        visibilityChange = "visibilitychange";
    } else if (typeof document.mozHidden !== "undefined") {
        visibilityChange = "mozvisibilitychange";
    } else if (typeof document.msHidden !== "undefined") {
        visibilityChange = "msvisibilitychange";
    } else if (typeof document.webkitHidden !== "undefined") {
        visibilityChange = "webkitvisibilitychange";
    }

    document.addEventListener(visibilityChange, function() {
        if (document.hidden) {
            // Page is hidden, quit the quiz
            // You can call a function here to quit the quiz
            quitQuiz();
        }
    });

    function quitQuiz() {
        // Perform actions to quit the quiz
        window.location.href = "${pageContext.request.contextPath}/user/quizzes/quit"; // Example: Redirect to quit URL
    }
</script>
</body>
</html>
