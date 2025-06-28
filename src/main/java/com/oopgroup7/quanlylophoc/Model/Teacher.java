package com.oopgroup7.quanlylophoc.Model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Version;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher", 
       uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Teacher implements Serializable {
    @Id
    @GeneratedValue
    private UUID id; // Unique identifier for the teacher
    private String name;
    private String subject;
    private String department;
    
    @Column(unique = true, nullable = false)
    private String username; // Thêm trường username
    
    private String password; // Thêm trường password
    

    //thêm version
    @Version
    private Long version;


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

    public Long getVersion() { // getter cho version
        return version;
    }

    public void setVersion(Long version) { // setter cho version
        this.version = version;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Display info
    /*public void printInfo() {
        System.out.println("Teacher ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Subject: " + subject);
    } */
}
