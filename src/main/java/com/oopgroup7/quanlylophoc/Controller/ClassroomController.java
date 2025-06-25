package com.oopgroup7.quanlylophoc.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Service.ClassroomService;
import com.oopgroup7.quanlylophoc.Service.StudentService;
import com.oopgroup7.quanlylophoc.Service.TeacherService;


@Controller
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @Autowired
    public ClassroomController(ClassroomService classroomService,
                               TeacherService teacherService,
                               StudentService studentService){
        this.classroomService = classroomService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    // Hiển thị danh sách lớp học
    @GetMapping
    public String listClassrooms(Model model) {
        List<Classroom> classrooms = classroomService.findAll();
        model.addAttribute("classrooms", classrooms);
        return "classroom/index";
    }

    @GetMapping("/{id}/student")
    public String showStudentsInClassroom(@PathVariable UUID id, Model model) {
        Optional<Classroom> classroom = classroomService.findById(id);
        if (classroom.isPresent()) {
            model.addAttribute("classroom", classroom.get());
            model.addAttribute("student", classroom.get().getStudentList());
            return "classroom/student"; // 👈 Trả về file student.html trong /templates/classroom/
        } else {
            return "redirect:/classrooms";
        }
    }

@GetMapping("/{id}/add-student")
public String showAddStudentForm(@PathVariable UUID id, Model model) {
    Optional<Classroom> classroom = classroomService.findById(id);
    if (classroom.isEmpty()) {
        return "redirect:/classrooms";
    }

    model.addAttribute("classroom", classroom.get());
    model.addAttribute("teacher", teacherService.findAll());
    model.addAttribute("student", studentService.findAll());
    return "classroom/form"; // hoặc một form riêng nếu bạn muốn tách
}

    // Hiển thị form thêm lớp học
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("classroom", new Classroom());
        model.addAttribute("teacher", teacherService.findAll());
        model.addAttribute("student", studentService.findAll());
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
        if (classroom.isPresent()) {
            model.addAttribute("classroom", classroom.get());
        } else {
            return "redirect:/classrooms";
        }
        model.addAttribute("teacher", teacherService.findAll());
        model.addAttribute("student", studentService.findAll());
        return "classroom/form";
    }

    // Xử lý sửa lớp học
    @PostMapping("/edit")
    public String editClassroom(@ModelAttribute Classroom classroom) {
        //classroomService.save(classroom);

    Optional<Classroom> existingOpt = classroomService.findById(classroom.getId());

    if (existingOpt.isPresent()) {
        Classroom existing = existingOpt.get();

        // Cập nhật tên lớp và giáo viên
        existing.setClassName(classroom.getClassName());
        existing.setTeacher(classroom.getTeacher());

        // Gộp học sinh cũ + học sinh vừa được chọn (tránh mất học sinh cũ)
        List<Student> updatedList = new ArrayList<>(existing.getStudentList());
        updatedList.addAll(
            classroom.getStudentList().stream()
                .filter(s -> !updatedList.contains(s))
                .collect(Collectors.toList())
        );
        existing.setStudentList(updatedList);

        classroomService.save(existing);
    }

        return "redirect:/classrooms";
    }

    // Xóa lớp học
    @GetMapping("/delete/{id}")
    public String deleteClassroom(@PathVariable UUID id) {
        classroomService.delete(id);
        return "redirect:/classrooms";
    }
}
