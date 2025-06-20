package com.demo.OOP.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Score implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private String studentId;
    private String subject;
    private double scoreValue;

    // Constructor mặc định (cần cho Spring/JPA)
    public Score() {
    }

    public Score(String studentId, String subject, double scoreValue) {
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

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public double getScoreValue() {
        return scoreValue;
    }

    // Setter
    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", subject='" + subject + '\'' +
                ", scoreValue=" + scoreValue +
                '}';
    }
}