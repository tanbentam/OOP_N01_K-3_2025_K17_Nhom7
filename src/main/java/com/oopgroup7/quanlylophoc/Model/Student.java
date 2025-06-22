package com.oopgroup7.quanlylophoc.Model;

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
    private UUID id; // Mã định danh duy nhất cho học sinh
    
    private String name;
    private int age;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Score score; 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public Student() {
        // Default constructor for JPA
        this.id = UUID.randomUUID(); // Tạo mã định danh duy nhất khi khởi tạo
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

    public int getAge() {
        return age;
    }

    public Score getScore() {
        return score; 
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<AttendanceRecord> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
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
       public void printInfo() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Score: " + score);
    }
}
