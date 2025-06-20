package com.demo.OOP.service;

import com.demo.OOP.model.AttendanceRecord;
import com.demo.OOP.model.Classroom;
import com.demo.OOP.model.Student;
import com.demo.OOP.repository.AttendanceRepository;
import com.demo.OOP.repository.ClassroomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttendanceService {
    private final AttendanceRepository repo;

@Autowired
    private ClassroomRepository classroomRepository;
    public List<String> getClassroomNames() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<String> names = new ArrayList<>();
        for (Classroom c : classrooms) {
            names.add(c.getClassName());
        }
        return names;
    }

    public List<Student> getPresentStudents(String className, LocalDate date) {
    Classroom classroom = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> present = new ArrayList<>();
    if (classroom != null) {
        for (Student s : classroom.getStudentList()) {
            if (s.isPresentOnDate(date)) {
                present.add(s);
            }
        }
    }
    return present;
}

public List<Student> getAbsentStudents(String className, LocalDate date) {
    Classroom classroom = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> absent = new ArrayList<>();
    if (classroom != null) {
        for (Student s : classroom.getStudentList()) {
            if (!s.isPresentOnDate(date)) {
                absent.add(s);
            }
        }
    }
    return absent;
}
    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    public List<AttendanceRecord> findAll() {
        return repo.findAll();
    }

    public Optional<AttendanceRecord> findById(UUID id) {
        return repo.findById(id);
    }

    public boolean save(AttendanceRecord record) {
        try {
            repo.save(record);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving AttendanceRecord: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting AttendanceRecord: " + e.getMessage());
            return false;
        }
    }
}