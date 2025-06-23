package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository repo) {
        this.studentRepository = repo;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            System.err.println("Error Saving Student: " + e.getMessage());
            return null;
        }
    }

    public boolean delete(UUID id) {
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Student: " + e.getMessage());
            return false;
        }
    }
    
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }
}