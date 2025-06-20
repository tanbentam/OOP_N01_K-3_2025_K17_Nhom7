package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}