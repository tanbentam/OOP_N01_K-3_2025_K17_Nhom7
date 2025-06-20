package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Classroom implements Serializable {

    @Id
    @GeneratedValue
    private UUID id; // Mã định danh duy nhất cho lớp học
    private String className;

    @ManyToOne
    private Teacher teacher; // Lưu đối tượng Teacher
   
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Student> studentList = new ArrayList<>(); // Lưu danh sách đối tượng Student
    public Classroom() {
        // Default constructor for JPA
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

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public Teacher getTeacher() { // Trả về đối tượng Teacher
        return teacher;
    }

    public void setTeacher(Teacher teacher) { // Nhận đối tượng Teacher
        this.teacher = teacher;
    }

    public void addStudent(Student student) { // Nhận đối tượng Student
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    public ArrayList<Student> getStudentList() {
        return new ArrayList<>(studentList); // Trả về bản sao để bảo vệ dữ liệu
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = new ArrayList<>(studentList); // Bảo vệ dữ liệu bằng cách tạo bản sao
    }
    
    
}