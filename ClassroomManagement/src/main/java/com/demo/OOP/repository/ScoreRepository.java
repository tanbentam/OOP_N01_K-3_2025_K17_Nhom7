package com.demo.OOP.repository;

import com.demo.OOP.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
}