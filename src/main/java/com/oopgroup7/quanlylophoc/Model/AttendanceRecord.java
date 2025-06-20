package com.oopgroup7.quanlylophoc.Model;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.UUID;

@Entity
//bản ghi điểm danh
public class AttendanceRecord implements Serializable {


    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate date;
    private boolean present;
    private boolean permission;

public AttendanceRecord() {
        // Default constructor for JPA
    }

    public AttendanceRecord(LocalDate date, boolean present, boolean permission) {
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

    public void setDate(LocalDate date) {
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
    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}

