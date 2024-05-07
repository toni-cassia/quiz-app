package com.midsem.service;

import com.midsem.model.Score;
import com.midsem.model.User;
import com.midsem.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getScoresForUser(User user) {
        return scoreRepository.findByUser(user);
    }
    public void saveScore(Score score) {
        scoreRepository.save(score);
    }
}
