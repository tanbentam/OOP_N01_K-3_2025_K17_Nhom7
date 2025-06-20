package com.demo.OOP.service;

import com.demo.OOP.model.Score;
import com.demo.OOP.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {
    private final ScoreRepository repo;

    public ScoreService(ScoreRepository repo) {
        this.repo = repo;
    }

    public List<Score> findAll() {
        return repo.findAll();
    }

    public Optional<Score> findById(UUID id) {
        return repo.findById(id);
    }

    public boolean save(Score score) {
        try {
            repo.save(score);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving Score: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Score: " + e.getMessage());
            return false;
        }
    }
}