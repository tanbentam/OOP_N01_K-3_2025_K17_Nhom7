package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;


@Entity
@Table(name = "students")

public class Student implements Serializable {
private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name = "version")
    private Long version = 0L;

    // Getter và setter 
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", columnDefinition = "BINARY(16)")
   private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    private int age;
    
    @Column(name = "date_of_birth")
private LocalDate dateOfBirth;

    
    @Column(unique = true)
    private String username;
    
    private String password;

    @Column(name = "address", length = 500)
private String address;

    @Column(name = "email")
private String email;

    
    @Column(name = "gender")
private String gender;

    @Column(name = "student_code", unique = true)
    private String studentCode;
    
    @Column(name = "class_name")
    private String className;
    
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "score_id")
    private Score score; 

@OneToMany(mappedBy = "student")
    private List<ClassroomStudent> classroomStudents = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id")
    
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    public Student() {

    }

    // Constructor cập nhật với các trường mới
    public Student(String name, int age, String subject, double scoreValue, 
                  String studentCode, String className, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.studentCode = studentCode;
        this.className = className;
        this.phoneNumber = phoneNumber;
        
        if (subject != null && scoreValue >= 0) {
            this.score = new Score(null, subject, scoreValue);
        }
    }

    // Constructor cũ giữ lại để tương thích
    public Student(String name, int age, String subject, double scoreValue) {
        this.name = name;
        this.age = age;
        if (subject != null && scoreValue >= 0) {
            this.score = new Score(null, subject, scoreValue);
        }
    }

    public List<ClassroomStudent> getClassroomStudents() {
        return classroomStudents;
    }

    public void setClassroomStudents(List<ClassroomStudent> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }

    public LocalDate getDateOfBirth() {
    return dateOfBirth;
}

public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
}

    public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}
    public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

    public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter và Setter methods đã có
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
    if (this.score == null) {
        this.score = new Score(null, subject, scoreValue);
    } else {
        this.score.setSubject(subject);
        this.score.setScoreValue(scoreValue);
    }
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
        return false; 
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
    System.out.println("Student Code: " + studentCode);
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
    System.out.println("Class: " + className);
    System.out.println("Phone Number: " + phoneNumber);
    if (score != null) {
        System.out.println("Subject: " + score.getSubject());
        System.out.println("Score: " + score.getScoreValue());
    } else {
        System.out.println("Score: Not available");
    }
}

    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentCode='" + studentCode + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", className='" + className + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
