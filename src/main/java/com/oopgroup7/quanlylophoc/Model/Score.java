package com.oopgroup7.quanlylophoc.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;


public class Score implements Serializable {
    
    @Id
    @GeneratedValue

    private UUID id; // Mã định danh duy nhất cho điểm số
    private String studentId; 
    private String subject; 
    private double scoreValue; 

    public Score() {
        // Default constructor for JPA
    }


    public Score(String studentId, String subject, double scoreValue){
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

    public String getStudentId() {
        return studentId;
    }
    public String getSubject() {
        return subject; 
    }
    public double getscoreValue() {
        return scoreValue; 
    }
    //set
    public void setStudentId(String studentId){
        this.studentId = studentId;
    }
    public void setsubject(String subject) {
        this.subject = subject; 
    }
    public void setscoreValue(double scoreValue) {
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
