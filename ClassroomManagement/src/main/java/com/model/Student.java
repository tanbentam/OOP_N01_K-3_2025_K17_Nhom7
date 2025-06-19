package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String name;
    private int age;
    private Score score; 

    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public Student(String id, String name, int age, String subject, double scoreValue) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = new Score(id, subject, scoreValue); 
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public Score getscore() {
        return score; 
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void setscore(String subject, double scoreValue) {
        this.score.setsubject(subject);
        this.score.setscoreValue(scoreValue);
    }
    //thêm điểm danh
    public void markAttendance(LocalDate date, boolean isPresent, boolean hasPermission) {
        attendanceRecords.add(new AttendanceRecord(date, isPresent, hasPermission));
    }

    // Display info
    public void printInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Score: " + score);
    }

    //ktra hsinh có mặt vào ngày nào
    public boolean isPresentOnDate(LocalDate date) {
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getDate().equals(date)) {
                return record.isPresent();
            }
        }
        return false; // nếu không có bản ghi thì xem như nghỉ
    }

    //néu nghỉ học thì có phép không
    public boolean hasPermissionOnDate(LocalDate date) {
        for (AttendanceRecord record : attendanceRecords) {
            if (record.getDate().equals(date)) {
                return record.hasPermission();
            }
        }
        return false;
    }
}