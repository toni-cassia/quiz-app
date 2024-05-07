package com.midsem.controller;

import com.midsem.model.*;
import com.midsem.service.QuizService;
import com.midsem.service.ScoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/user")
public class UserController {
    private Map<String, Long> activeSessions = new ConcurrentHashMap<>();
        @Autowired
        private QuizService quizService;
    @Autowired
    private ScoreService scoreService;
    private HttpSession session;

    @GetMapping("/dashboard")
        public String showDashboard(Model model, HttpSession session) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login"; // Redirect to login if user is not logged in
            }
            List<Quiz> quizzes = quizService.getAllQuizzes();
            model.addAttribute("quizzes", quizzes);
            return "success";
        }
    @GetMapping("/quizzes/{quizId}/start")
    public String startQuiz(@PathVariable Long quizId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz != null) {
            session.setAttribute("currentQuiz", quiz);
            session.setAttribute("currentQuestionIndex", 0);
            session.setAttribute("quizDuration", quiz.getDuration()); // Assuming getDuration returns the duration in minutes
            activeSessions.put(session.getId(), quizId);
            return "redirect:/user/quizzes/question";
        } else {
            model.addAttribute("error", "Quiz not found");
            return "fail";
        }
    }
        @GetMapping("/quizzes/question")
        public String showQuestion(HttpSession session, Model model) {


//            if (isQuizSessionActive(session)) {
//                return "redirect:/user/dashboard"; // Redirect to dashboard if quiz session is not active
//            }



            Quiz quiz = (Quiz) session.getAttribute("currentQuiz");
            User user = (User) session.getAttribute("user");

            if (user == null) {
                return "redirect:/login"; // Redirect to login if user is not logged in
            }
            else{
            System.out.println(user);
                System.out.println(user.getUsername());
            }
//            if(quiz)
            Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
//            if (quiz == null  || currentIndex >= quiz.getQuestions().size()) {
//                return "redirect:/user/quizzes/complete"; // Redirect to complete if there are no more questions
//            }
            List<Question> questions = quiz.getQuestions();
            System.out.println("Checking the Questions staff");
            System.out.println(questions);
            Question question = quiz.getQuestions().get(currentIndex);
            model.addAttribute("question", question);
            model.addAttribute("currentQuestionIndex", currentIndex + 1); // +1 to make it human-readable
            model.addAttribute("totalQuestions", quiz.getQuestions().size());

            return "userQuestion";

        }


    @PostMapping("/quizzes/question/answer")
    public String processAnswer(@RequestParam("answer") Long optionId, HttpSession session) {
//        if (isQuizSessionActive(session)) {
//            return "redirect:/user/dashboard"; // Redirect to dashboard if quiz session is not active
//        }
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        Quiz quiz = (Quiz) session.getAttribute("currentQuiz");
        Question currentQuestion = quiz.getQuestions().get(currentIndex);

        Map<Long, Long> answers = (Map<Long, Long>) session.getAttribute("userAnswers");
        if (answers == null) {
            answers = new HashMap<>();
            session.setAttribute("userAnswers", answers);
        }
        answers.put(currentQuestion.getId(), optionId);

        currentIndex++;
        session.setAttribute("currentQuestionIndex", currentIndex);

        if (currentIndex >= quiz.getQuestions().size()) {
            return "redirect:/user/quizzes/complete"; // If last question, go to completion
        }

        return "redirect:/user/quizzes/question"; // Otherwise, go to next question
    }



    @GetMapping("/quizzes/complete")
    public String completeQuiz(HttpSession session, Model model) {
        Quiz quiz = (Quiz) session.getAttribute("currentQuiz");
        if (quiz == null) {
            return "redirect:/user/dashboard"; // Redirect if no quiz in session
        }

        int scoreValue = calculateScore(quiz, session);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        Score score = new Score();
        score.setUser(user);
        score.setQuiz(quiz);
        score.setValue(scoreValue);
        scoreService.saveScore(score); // Save the score

        model.addAttribute("score", scoreValue);
        model.addAttribute("quizCount", quiz.getQuestions().size());
        session.removeAttribute("currentQuiz");
        session.removeAttribute("currentQuestionIndex");

        return "userQuizScore"; // Redirect to the quiz score JSP page
    }


    private int calculateScore(Quiz quiz, HttpSession session) {
        Map<Long, Long> answers = (Map<Long, Long>) session.getAttribute("userAnswers");
        int score = 0;

        for (Question question : quiz.getQuestions()) {
            Long correctOptionId = findCorrectOptionId(question);
            Long userAnswer = answers.get(question.getId());

            if (userAnswer != null && userAnswer.equals(correctOptionId)) {
                score++; // Increment score for each correct answer
            }
        }

        return score;
    }

    private Long findCorrectOptionId(Question question) {
        for (Option option : question.getOptions()) {
            if (option.isCorrect()) {
                return option.getId(); // Return the ID of the correct option
            }
        }
        return null; // Return null if no option is marked as correct, should not happen in a well-formed quiz
    }

    @GetMapping("/quizzes/quit")
    public String quitQuiz(HttpSession session) {
        // Clean up the session to remove quiz-related attributes
        session.removeAttribute("currentQuiz");
        session.removeAttribute("currentQuestionIndex");
        session.removeAttribute("userAnswers");

        return "redirect:/user/dashboard"; // Redirect to the dashboard or a "quiz quit" confirmation page
    }
    @GetMapping("/scores")
    public String viewScores(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        List<Score> scores = scoreService.getScoresForUser(user);
        Map<Score, Integer> scoreDetails = new HashMap<>();
        for (Score score : scores) {
            int totalQuestions = score.getQuiz().getQuestions().size(); // Assuming you can directly access questions
            scoreDetails.put(score, totalQuestions);
        }
        model.addAttribute("scoreDetails", scoreDetails);
        return "viewScores"; // Name of the JSP file to display the scores
    }
//    private boolean isQuizSessionActive(HttpSession session) {
//        Long quizId = activeSessions.get(session.getId());
//        if (quizId == null) {
//            return false; // Quiz session not active for this session
//        }
//
//        Quiz quiz = quizService.findQuizById(quizId);
//        if (quiz == null) {
//            return false; // Quiz not found
//        }
//
//        // Check if session attributes related to the quiz session exist
//        Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
//        Integer quizDuration = (Integer) session.getAttribute("quizDuration");
//        Long quizStartTime = (Long) session.getAttribute("quizStartTime");
//
//        // Check if all necessary session attributes are not null
//        if (currentQuestionIndex != null && quizDuration != null && quizStartTime != null) {
//            // Calculate the current time
//            long currentTimeMillis = System.currentTimeMillis();
//
//            // Calculate the elapsed time since the quiz started
//            long elapsedTime = currentTimeMillis - quizStartTime;
//
//            // Convert quiz duration from minutes to milliseconds
//            long quizDurationMillis = quizDuration * 60000;
//
//            // Check if the elapsed time is within the quiz duration
//            if (elapsedTime <= quizDurationMillis) {
//                // Quiz session is active
//                return true;
//            }
//        }
//
//        // Quiz session is not active
//        return false;
//    }
}
