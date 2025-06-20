package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // Hiển thị form điểm danh tại /attendance/form
    @GetMapping("/form")
public String showForm(Model model) {
    model.addAttribute("classNames", attendanceService.getClassroomNames());
    return "attendance/form";
}

// Xóa hoặc không dùng GET /attendance/report nếu không cần

    // Hiển thị form điểm danh tại /attendance/report (có thể dùng cho link khác)
    @GetMapping("/report")
    public String showReportForm(Model model) {
        model.addAttribute("classNames", attendanceService.getClassroomNames());
        return "attendance/form";
    }

    // Xử lý xuất báo cáo điểm danh
    @PostMapping("/report")
    public String showReport(
            @RequestParam String className,
            @RequestParam String date,
            @RequestParam String type,
            Model model) {
        LocalDate localDate = LocalDate.parse(date);
        List<Student> students;
        if ("present".equals(type)) {
            students = attendanceService.getPresentStudents(className, localDate);
        } else {
            students = attendanceService.getAbsentStudents(className, localDate);
        }
        model.addAttribute("students", students);
        model.addAttribute("type", type);
        model.addAttribute("className", className);
        model.addAttribute("date", date);
        return "attendance/report";
    }
}