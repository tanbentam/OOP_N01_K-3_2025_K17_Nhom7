package com.demo.OOP.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Classroom implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String className;

    @ManyToOne
    private Teacher teacher; // Lưu đối tượng Teacher

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> studentList = new ArrayList<>();

    // Constructor mặc định (cần cho Spring/JPA)
    public Classroom() {
    }

    public Classroom(String className, Teacher teacher) {
        this.className = className;
        this.teacher = teacher;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    // Không cần printClassInfo() nếu dùng Spring MVC, thay vào đó dùng View (HTML)
}