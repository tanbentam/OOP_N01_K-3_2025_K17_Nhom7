package com.demo.OOP.repository;

import com.demo.OOP.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}