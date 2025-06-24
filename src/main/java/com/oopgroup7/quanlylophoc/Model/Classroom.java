package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Classroom implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String className; // Tên lớp học, ví dụ: "10A1"

    @ManyToOne
    private Teacher teacher; // Giáo viên chủ nhiệm



@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
@JoinTable(
    name = "classroom_student",
    joinColumns = @JoinColumn(name = "classroom_id"),
    inverseJoinColumns = @JoinColumn(name = "student_id"),
    uniqueConstraints = @UniqueConstraint(columnNames = {"classroom_id", "student_id"})
)

    private List<Student> studentList = new ArrayList<>();

    // 👉 Mối quan hệ mới với thời khóa biểu
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public Classroom() {}

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
        return new ArrayList<>(studentList);
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = new ArrayList<>(studentList);
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void removeStudent(Student student) {
        studentList.remove(student);
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
