package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_student")
public class ClassroomStudent implements Serializable {

    @EmbeddedId
    private ClassroomStudentId id = new ClassroomStudentId();

    @ManyToOne
    @MapsId("classroomId")
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    // Có thể thêm các trường khác, ví dụ: ngày vào lớp, trạng thái...
    // private LocalDate joinDate;

    // getters, setters
    public ClassroomStudentId getId() { return id; }
    public void setId(ClassroomStudentId id) { this.id = id; }
    public Classroom getClassroom() { return classroom; }
    public void setClassroom(Classroom classroom) { this.classroom = classroom; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
}