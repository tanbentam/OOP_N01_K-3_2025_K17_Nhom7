package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "class_name")
    private String className;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // Danh sách học sinh theo mối quan hệ nhiều-nhiều
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "classroom_student",
        joinColumns = @JoinColumn(name = "classroom_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"classroom_id", "student_id"})
    )
    private List<Student> studentList = new ArrayList<>();

    // Danh sách liên kết classroom - student (để lưu thêm thông tin nếu cần)
    @OneToMany(mappedBy = "classroom")
    private List<ClassroomStudent> classroomStudents = new ArrayList<>();

    // Mối quan hệ với thời khóa biểu
    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timetable> timetables = new ArrayList<>();

    // Constructor mặc định
    public Classroom() {}

    // Constructor có tham số
    public Classroom(String className, Teacher teacher) {
        this.className = className;
        this.teacher = teacher;
    }

    // Getter và Setter
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
        this.studentList.add(student);
    }

    public void removeStudent(Student student) {
        this.studentList.remove(student);
    }

    public List<ClassroomStudent> getClassroomStudents() {
        return classroomStudents;
    }

    public void setClassroomStudents(List<ClassroomStudent> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }

    public List<Timetable> getSchedules() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}

