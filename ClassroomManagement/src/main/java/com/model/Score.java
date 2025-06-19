package com.model;
import java.io.Serializable;

public class Score implements Serializable {
    private String StudentId; 
    private String subject; 
    private double scoreValue; 

    public Score( String StudentId, String subject, double scoreValue){
        this.StudentId = StudentId;
        this.subject = subject;
        this.scoreValue = scoreValue; 
    }
    //get
    public String getStudentId() {
        return StudentId;
    }
    public String getsubject() {
        return subject; 
    }
    public double getscoreValue() {
        return scoreValue; 
    }
    //set
    public void setStudentId(String StudentId){
        this.StudentId = StudentId;
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
                "studentId='" + StudentId + '\'' +
                ", subject='" + subject + '\'' +
                ", scoreValue=" + scoreValue +
                '}';
    }

}
