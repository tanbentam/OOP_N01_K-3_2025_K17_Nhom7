package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {
    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) {
        this.repo = repo;
    }

    public List<Teacher> findAll() {
        return repo.findAll();
    }

    public Optional<Teacher> findById(UUID id) {
        return repo.findById(id);
    }

    public boolean save(Teacher teacher) {
        try {
            repo.save(teacher);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving Teacher: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Teacher: " + e.getMessage());
            return false;
        }
    }
}