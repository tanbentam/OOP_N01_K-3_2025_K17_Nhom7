package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // Hiển thị danh sách giáo viên
    @GetMapping
    public String listTeachers(Model model) {
        List<Teacher> teachers = teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "teacher/index";
    }

    // Hiển thị form thêm giáo viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/form";
    }

    // Xử lý thêm giáo viên
    @PostMapping("/add")
    public String addTeacher(@ModelAttribute Teacher teacher) {
        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    // Hiển thị form sửa giáo viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Teacher> teacher = teacherService.findById(id);
        teacher.ifPresent(value -> model.addAttribute("teacher", value));
        return "teacher/form";
    }

    // Xử lý sửa giáo viên
    @PostMapping("/edit")
    public String editTeacher(@ModelAttribute Teacher teacher) {
        teacherService.save(teacher);
        return "redirect:/teachers";
    }

    // Xóa giáo viên
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable UUID id) {
        teacherService.delete(id);
        return "redirect:/teachers";
    }
}