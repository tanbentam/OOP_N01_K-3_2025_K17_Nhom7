package com.demo.OOP.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class AttendanceRecord implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate date;
    private boolean present;
    private boolean permission;

    // Constructor mặc định (cần cho Spring hoặc JPA)
    public AttendanceRecord() {
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

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isPermission() {
        return permission;
    }

    public boolean hasPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}