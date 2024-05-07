 package com.midsem.controller;

import com.midsem.model.*;

//import com.midsem.service.QuizInterface;
import com.midsem.repository.OptionRepository;
import com.midsem.repository.QuestionRepository;
import com.midsem.service.QuestionService;
import com.midsem.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping("/quizzes")
    public String listQuizzes(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }

        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quizzes";
    }
    @GetMapping("/quizzes/new")
    public String showCreateForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        model.addAttribute("quiz", new Quiz());
        return "/createQuiz";
    }

    @PostMapping("/quizz")
    public String createQuiz(@ModelAttribute Quiz quiz, HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        quizService.createQuiz(quiz);
        return "redirect:/admin/quizzes";
    }
    @GetMapping("/quizzes/{quizId}/questions/new")
    public String showAddQuestionForm(@PathVariable Long quizId, HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null) {
            return "redirect:/admin/quizzes";
        }
        model.addAttribute("quiz", quiz);
        model.addAttribute("quizName", quizService.findQuizById(quizId).getTitle()); // Assume Quiz has a getName method
        model.addAttribute("questionCount", quiz.getQuestions().size() +1);
        return "addQuestion";
//        model.addAttribute("quizId", quizId);
//        model.addAttribute("quiz_name",quizService.findQuizById(quizId).getTitle());
//
//        model.addAttribute("questionForm", new QuestionForm());  // You need to define QuestionForm to include options.
//        return "addQuestion";
    }
//    debug code
    @PostMapping("/try")
    public String tryQuiz(){
        return "redirect:/admin/quizzes";
    }
//    http://localhost:8080/admin/quizzes/2/admin/quizzes
//    /quizzes/2/admin/quizzes
    @PostMapping("/quizzes/questions/{quizId}")
    public String addQuestion(@PathVariable Long quizId, @ModelAttribute QuestionForm questionForm, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        Quiz quiz = quizService.findQuizById(quizId);  // Ensure you have a method to fetch the Quiz by ID.
        Question question = new Question();
        question.setText(questionForm.getQuestionText());
        question.setQuiz(quiz);
        List<Question> questions = quiz.getQuestions();

        List<Option> options = createOptionsFromForm(questionForm);  // Implement this method to convert form inputs into Option entities.
        question.setOptions(options);




        questionService.save(question);  // Your service that handles saving questions with options.
        questions.add(question);
        quiz.setQuestions(questions);
        quizService.createQuiz(quiz);

        return "redirect:/admin/quizzes/"+quizId+"/questions/new";
    }
    private List<Option> createOptionsFromForm(QuestionForm questionForm) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < questionForm.getOptions().size(); i++) {
            Option option = new Option();
            option.setText(questionForm.getOptions().get(i));
            option.setCorrect(i == questionForm.getCorrectOption()); // Set true if this option is the correct one
            options.add(option);
        }
        return options;
    }
    @PostMapping("quizzes/{quizId}/delete")
    public String deleteQuiz(@PathVariable Long quizId) {
        try {
            // Call the service method to delete the quiz
            quizService.deleteQuizById(quizId); // Assuming you have a method to delete quiz by ID
            // Redirect the user to the list of quizzes
            return "redirect:/admin/quizzes";
        } catch (Exception e) {
            // Handle any exceptions that may occur during deletion
            e.printStackTrace(); // Log the exception for debugging purposes
            return "fail"; // Redirect to an error page or handle the error appropriately
        }
    }
}

