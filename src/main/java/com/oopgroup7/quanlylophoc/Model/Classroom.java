package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @OneToMany(mappedBy = "classroom")
    private List<ClassroomStudent> classroomStudents = new ArrayList<>();

    // Constructor mặc định
    public Classroom() {
        // Hibernate sẽ tự sinh id
    }

    // Constructor có tham số
    public Classroom(String className, Teacher teacher) {
        this.className = className;
        this.teacher = teacher;
    }

    // Getter và setter
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

    public List<ClassroomStudent> getClassroomStudents() {
        return classroomStudents;
    }

    public void setClassroomStudents(List<ClassroomStudent> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }

    // equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return id != null && id.equals(classroom.getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}