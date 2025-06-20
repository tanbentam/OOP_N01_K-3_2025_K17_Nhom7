package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<AttendanceRecord, UUID> {
}