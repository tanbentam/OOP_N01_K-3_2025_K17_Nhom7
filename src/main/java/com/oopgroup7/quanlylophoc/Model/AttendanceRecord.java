package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "attendance_records")
//bản ghi điểm danh
public class AttendanceRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(name = "attendance_date")
    private LocalDate date;
    
    @Column(name = "present")
    private boolean present;
    
    @Column(name = "permission")
    private boolean permission;
    
    // Quan hệ nhiều-một với Student
    // ...existing code...
@ManyToOne
@JoinColumn(name = "student_id", columnDefinition = "BINARY(16)")
private Student student;

// ...existing code...

    private String note;

public String getNote() {
    return note;
}

public void setNote(String note) {
    this.note = note;
}
    public AttendanceRecord() {
        // Default constructor for JPA
    }

    public AttendanceRecord(LocalDate date, boolean present, boolean permission) {
        this.date = date;
        this.present = present;
        this.permission = permission;
    }
    
    public AttendanceRecord(Student student, LocalDate date, boolean present, boolean permission) {
        this.student = student;
        this.date = date;
        this.present = present;
        this.permission = permission;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    // Thêm alias cho getDate() để phù hợp với controller
    public LocalDate getAttendanceDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    // Alias cho setDate()
    public void setAttendanceDate(LocalDate date) {
        this.date = date;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isPresent() {
        return present;
    }

    public boolean hasPermission() {
        return permission;
    }
    
    // Alias cho hasPermission() để phù hợp với controller
    public boolean isPermission() {
        return permission;
    }
    
    public void setPermission(boolean permission) {
        this.permission = permission;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    @Override
    public String toString() {
        return "AttendanceRecord [id=" + id + 
               ", student=" + (student != null ? student.getId() : "null") + 
               ", date=" + date + 
               ", present=" + present + 
               ", permission=" + permission + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}