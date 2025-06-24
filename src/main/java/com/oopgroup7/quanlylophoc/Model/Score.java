package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "scores")
public class Score implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id; // Mã định danh duy nhất cho điểm số
    
    @Column(columnDefinition = "BINARY(16)")
    private UUID studentId; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", referencedColumnName = "id", insertable = false, updatable = false)
    private Student student;
    
    private String subject; 
    private double scoreValue; 

    public Score() {
        // Default constructor for JPA
    }


    public Score(UUID studentId, String subject, double scoreValue){
        this.studentId = studentId;
        this.subject = subject;
        this.scoreValue = scoreValue; 
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStudentId() {
        return studentId;
    }
    
    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public String getSubject() {
        return subject; 
    }
    
    public double getScoreValue() {
        return scoreValue; 
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public void setScoreValue(double scoreValue) {
        this.scoreValue = scoreValue;
    }

       @Override
    public String toString() {
        return "Score{" +
                "studentId='" + studentId + '\'' +
                ", subject='" + subject + '\'' +
                ", scoreValue=" + scoreValue +
                '}';
    }

}
