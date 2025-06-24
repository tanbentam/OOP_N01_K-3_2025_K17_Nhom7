package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "students")
public class Student implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id; // Mã định danh duy nhất cho học sinh
    
    @Column(nullable = false)
    private String name;
    private int age;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score; 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    /*@JoinTable(
    name = "student_attendance_records",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "attendance_records_id"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "attendance_records_id"})
)*/
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    @ManyToMany(mappedBy = "studentList")
    private List<Classroom> classrooms = new ArrayList<>();

    public Student() {
        //Để hibernate tạo UUID
    }

    public Student(String name, int age, String subject, double scoreValue) {
        this.name = name;
        this.age = age;
        if (subject != null && scoreValue >= 0) {
        this.score = new Score(null, subject, scoreValue);
        }
    }

    // Getter methods

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
