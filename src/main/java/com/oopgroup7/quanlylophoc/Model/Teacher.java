package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Entity
public class Teacher implements Serializable {
    @Id
    @GeneratedValue
    private UUID id; // Unique identifier for the teacher
    private String name;
    private String subject;
    private String department;
    
    

    public Teacher() {
        // Default constructor for JPA
    }

    public Teacher(String name, String subject, String department) {
        this.name = name;
        this.subject = subject;
        this.department = department;
    }

    // Getter methods
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() { //thêm phương thức getDepartment
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Display info
    /*public void printInfo() {
        System.out.println("Teacher ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Subject: " + subject);
    } */
}
