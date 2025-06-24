package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    // Hiển thị danh sách lớp học
    @GetMapping
    public String listClassrooms(Model model) {
        List<Classroom> classrooms = classroomService.findAll();
        model.addAttribute("classrooms", classrooms);
        return "classroom/index";
    }

    // Hiển thị form thêm lớp học
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "classroom/form";
    }

    // Xử lý thêm lớp học
    @PostMapping("/add")
    public String addClassroom(@ModelAttribute Classroom classroom) {
        classroomService.save(classroom);
        return "redirect:/classrooms";
    }

    // Hiển thị form sửa lớp học
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Classroom> classroom = classroomService.findById(id);
        classroom.ifPresent(value -> model.addAttribute("classroom", value));
        return "classroom/form";
    }

    // Xử lý sửa lớp học
    @PostMapping("/edit")
    public String editClassroom(@ModelAttribute Classroom classroom) {
        classroomService.save(classroom);
        return "redirect:/classrooms";
    }

    // Xóa lớp học
    @GetMapping("/delete/{id}")
    public String deleteClassroom(@PathVariable UUID id) {
        classroomService.delete(id);
        return "redirect:/classrooms";
    }
}