package com.oopgroup7.quanlylophoc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Timetable;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Service.TimetableService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TimetableValidationTest {

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Test
    public void testDuplicateTimetableValidation() {
        // Tạo lớp học test
        Classroom testClass = new Classroom();
        testClass.setClassName("Test Class Validation");
        testClass = classroomRepository.save(testClass);

        // Tạo timetable đầu tiên
        Timetable timetable1 = new Timetable();
        timetable1.setDayOfWeek("Thứ 2");
        timetable1.setPeriod(1);
        timetable1.setSubject("Toán");
        timetable1.setTeacherName("Nguyễn Văn A");
        timetable1.setClassroom(testClass);

        // Lưu timetable đầu tiên
        timetableService.saveTimetable(testClass.getId(), timetable1);

        // Kiểm tra validation - không được phép trùng
        boolean exists = timetableService.existsByClassIdAndDayOfWeekAndPeriod(
                testClass.getId(), "Thứ 2", 1);

        assertTrue(exists, "Validation should detect duplicate timetable");

        // Kiểm tra không trùng với ngày khác
        boolean notExists = timetableService.existsByClassIdAndDayOfWeekAndPeriod(
                testClass.getId(), "Thứ 3", 1);

        assertFalse(notExists, "Validation should allow different day");

        // Kiểm tra không trùng với tiết khác
        boolean notExistsPeriod = timetableService.existsByClassIdAndDayOfWeekAndPeriod(
                testClass.getId(), "Thứ 2", 2);

        assertFalse(notExistsPeriod, "Validation should allow different period");

        // Cleanup
        classroomRepository.delete(testClass);
    }
}
