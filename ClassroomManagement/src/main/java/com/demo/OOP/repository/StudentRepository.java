package com.demo.OOP.repository;

import com.demo.OOP.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}