package com.midsem.service;


import com.midsem.model.Option;
import com.midsem.model.Question;
import com.midsem.model.Quiz;
import com.midsem.repository.QuizRepository;
import com.midsem.repository.QuestionRepository;
import com.midsem.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;


    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

  public Quiz createQuiz(Quiz quiz) {
    return quizRepository.save(quiz);
}
    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id).orElse(null); // Handle the case where quiz might not exist
    }
    public void deleteQuizById(Long quizId) {
        // Check if the quiz exists
        if (quizRepository.existsById(quizId)) {
            // Delete the quiz by ID
            quizRepository.deleteById(quizId);
        } else {
            throw new RuntimeException("Quiz not found with id: " + quizId);
        }
    }
}