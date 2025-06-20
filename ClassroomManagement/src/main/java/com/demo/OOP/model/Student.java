package com.demo.OOP.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private Score score;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    // Constructor mặc định (cần cho Spring/JPA)
    public Student() {
    }

    public Student(String name, int age, String subject, double scoreValue) {
        this.name = name;
        this.age = age;
        this.score = new Score(null, subject, scoreValue);
    }

    // Getter methods
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    // Thêm điểm danh
    public void markAttendance(LocalDate date, boolean isPresent, boolean hasPermission) {
        attendanceRecords.add(new AttendanceRecord(date, isPresent, hasPermission));
    }

    // Kiểm tra học sinh có mặt vào ngày nào
    public boolean isPresentOnDate(LocalDate date) {
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getDate().equals(date)) {
                return record.isPresent();
            }
        }
        return false; // nếu không có bản ghi thì xem như nghỉ
    }

    // Nếu nghỉ học thì có phép không
    public boolean hasPermissionOnDate(LocalDate date) {
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getDate().equals(date)) {
                return record.isPermission();
            }
        }
        return false;
    }

    // Hiển thị thông tin học sinh
    public void printInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Score: " + score);
    }
}