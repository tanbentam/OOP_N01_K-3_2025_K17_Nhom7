package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;
import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.AttendanceRepository;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;

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
    Optional<Classroom> classroomOpt = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> present = new ArrayList<>();
    if (classroomOpt.isPresent()) {
        Classroom classroom = classroomOpt.get();
        for (ClassroomStudent cs : classroom.getClassroomStudents()) {
            Student s = cs.getStudent();
            if (s.isPresentOnDate(date)) {
                present.add(s);
            }
        }
    }
    return present;
}

public List<Student> getAbsentStudents(String className, LocalDate date) {
    Optional<Classroom> classroomOpt = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> absent = new ArrayList<>();
    if (classroomOpt.isPresent()) {
        Classroom classroom = classroomOpt.get();
        for (ClassroomStudent cs : classroom.getClassroomStudents()) {
            Student s = cs.getStudent();
            if (!s.isPresentOnDate(date)) {
                absent.add(s);
            }
        }
    }
    return absent;
}


// ...existing code...
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