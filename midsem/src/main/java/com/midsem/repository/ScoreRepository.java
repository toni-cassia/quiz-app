package com.midsem.repository;

import com.midsem.model.Score;
import com.midsem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByUser(User user);
}
