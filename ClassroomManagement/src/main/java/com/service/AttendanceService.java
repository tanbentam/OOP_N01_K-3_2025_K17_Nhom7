package com.service;

import com.model.ClassManager;
import com.model.Student;
import org.springframework.stereotype.Service;
import com.model.FileManager;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    private final ClassManager classManager;

    public AttendanceService() {
        // Giả sử bạn đã có logic load dữ liệu từ file
        this.classManager = FileManager.loadData("data.dat");
    }

    public List<String> getClassroomNames() {
    return classManager.getClassroomNames();
}

    public List<Student> getPresentStudents(String className, LocalDate date) {
        return classManager.getPresentStudents(className, date);
    }

    public List<Student> getAbsentStudents(String className, LocalDate date) {
        return classManager.getAbsentStudents(className, date);
    }
}