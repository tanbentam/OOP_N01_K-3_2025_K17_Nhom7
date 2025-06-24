package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Embeddable;

@Embeddable
public class ClassroomStudentId implements Serializable {
    private UUID classroomId;
    private UUID studentId;

    // getters, setters, equals, hashCode
    public UUID getClassroomId() { return classroomId; }
    public void setClassroomId(UUID classroomId) { this.classroomId = classroomId; }
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassroomStudentId)) return false;
        ClassroomStudentId that = (ClassroomStudentId) o;
        return classroomId.equals(that.classroomId) && studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return classroomId.hashCode() + studentId.hashCode();
    }
}