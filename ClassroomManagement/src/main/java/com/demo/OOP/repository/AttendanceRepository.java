package com.demo.OOP.repository;

import com.demo.OOP.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, UUID> {
}