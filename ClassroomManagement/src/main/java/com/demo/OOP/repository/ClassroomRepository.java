package com.demo.OOP.repository;

import com.demo.OOP.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    Classroom findByClassNameIgnoreCase(String className);
}