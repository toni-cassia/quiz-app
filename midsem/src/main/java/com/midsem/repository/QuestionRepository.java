package com.midsem.repository;

import com.midsem.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteByQuizId(Long quizId);
}
