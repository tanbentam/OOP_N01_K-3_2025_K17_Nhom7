package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "scores")
public class Score implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "value")
private Double valueDuplicate = 0.0; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;
    
    private String subject;
    @Column(name = "score_value")
    
    private double value;
    
    private String notes;
    
    public Score() {
        // Default constructor for JPA
    }

    public Double getValueDuplicate() {
    return valueDuplicate;
}

public void setValueDuplicate(Double valueDuplicate) {
    this.valueDuplicate = valueDuplicate;
}
    public Score(Student student, String subject, double value) {
        this.student = student;
        this.subject = subject;
        this.value = value;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public void setSubject(String subject) {
        this.subject = subject; 
    }

    public double getValue() {
        return value; 
    }

    public double getScoreValue() {
        return value;
    }

    public void setScoreValue(double scoreValue) {
        this.value = scoreValue;
    }

    public void setValue(double value) {
        this.value = value; 
        this.valueDuplicate = value; 
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", student=" + (student != null ? student.getName() : "null") +
                ", subject='" + subject + '\'' +
                ", value=" + value +
                '}';
    }
}