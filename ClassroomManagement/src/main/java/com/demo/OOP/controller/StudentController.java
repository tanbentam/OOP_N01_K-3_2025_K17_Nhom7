package com.demo.OOP.controller;

import com.demo.OOP.model.Student;
import com.demo.OOP.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Hiển thị danh sách học sinh
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student/index";
    }

    // Hiển thị form thêm học sinh
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form";
    }

    // Xử lý thêm học sinh
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // Hiển thị form sửa học sinh
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Student> student = studentService.findById(id);
        student.ifPresent(value -> model.addAttribute("student", value));
        return "student/form";
    }

    // Xử lý sửa học sinh
    @PostMapping("/edit")
    public String editStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";
    }

    // Xóa học sinh
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable UUID id) {
        studentService.delete(id);
        return "redirect:/students";
    }
}