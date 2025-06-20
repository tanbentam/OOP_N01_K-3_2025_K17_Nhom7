package com.demo.OOP.service;

import com.demo.OOP.model.Classroom;
import com.demo.OOP.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClassroomService {
    private final ClassroomRepository repo;

    public ClassroomService(ClassroomRepository repo) {
        this.repo = repo;
    }

    public List<Classroom> findAll() {
        return repo.findAll();
    }

    public Optional<Classroom> findById(UUID id) {
        return repo.findById(id);
    }

    public boolean save(Classroom classroom) {
        try {
            repo.save(classroom);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving Classroom: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Classroom: " + e.getMessage());
            return false;
        }
    }
}