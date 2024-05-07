package com.midsem.service;
import com.midsem.model.Question;
import com.midsem.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public void save(Question question) {
        questionRepository.save(question);
    }
    @Transactional  // Make sure this method is transactional
    public Question saveQuestionWithOptions(Question question) {
        return questionRepository.save(question);  // Saving the question should cascade to options
    }
}
