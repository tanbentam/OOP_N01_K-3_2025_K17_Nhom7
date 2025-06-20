package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<Student> findById(UUID id) {
        return repo.findById(id);
    }

    public boolean save(Student student) {
        try {
            repo.save(student);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving Student: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Student: " + e.getMessage());
            return false;
        }
    }
}