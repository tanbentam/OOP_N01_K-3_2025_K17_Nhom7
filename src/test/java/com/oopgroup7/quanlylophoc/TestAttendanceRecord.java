package com.oopgroup7.quanlylophoc;

import org.junit.Test;
import static org.junit.Assert.*;
import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;

import java.time.LocalDate;

public class TestAttendanceRecord {

    @Test
    public void testAttendanceRecordDefaultConstructor() {
        AttendanceRecord record = new AttendanceRecord();
        assertNotNull(record);
    }


    @Test
    public void testSetAndGetDate() {
        AttendanceRecord record = new AttendanceRecord();
        LocalDate date = LocalDate.of(2025, 7, 1);
        record.setDate(date);
        assertEquals(date, record.getDate());
    }

    @Test
    public void testSetAndGetPresent() {
        AttendanceRecord record = new AttendanceRecord();
        record.setPresent(true);
        assertTrue(record.isPresent());
        record.setPresent(false);
        assertFalse(record.isPresent());
    }

    @Test
    public void testDefaultConstructor() {
        // Kiểm tra khởi tạo không tham số
        AttendanceRecord record = new AttendanceRecord();
        assertNotNull(record);
        
    }

    @Test
    public void testChangeAllFields() {
        AttendanceRecord record = new AttendanceRecord();
        // Không dùng setStudentId vì không tồn tại
        LocalDate date = LocalDate.of(2025, 8, 15);
        record.setDate(date);
        record.setPresent(true);

        assertEquals(date, record.getDate());
        assertTrue(record.isPresent());
    }
}