package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
}