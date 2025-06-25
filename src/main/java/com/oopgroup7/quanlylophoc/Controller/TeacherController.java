package com.oopgroup7.quanlylophoc.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Service.TeacherService;

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

    // Tìm kiếm giáo viên
    @GetMapping("/search")
    public String searchTeachers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String subject,
            Model model) {
        
        List<Teacher> teachers;
        
        if ((name != null && !name.isEmpty()) || 
            (department != null && !department.isEmpty()) || 
            (subject != null && !subject.isEmpty())) {
            teachers = teacherService.searchTeachers(name, department, subject);
        } else {
            teachers = teacherService.findAll();
        }
        
        model.addAttribute("teachers", teachers);
        model.addAttribute("name", name);
        model.addAttribute("department", department);
        model.addAttribute("subject", subject);
        return "teacher/index";
    }

    // Hiển thị form thêm giáo viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("isAdd", true);
        return "teacher/form";
    }

    // Xử lý thêm giáo viên
    @PostMapping("/add")
    public String addTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra username
            if (teacher.getUsername() != null && !teacher.getUsername().trim().isEmpty()) {
                Teacher existingTeacher = teacherService.findByUsername(teacher.getUsername());
                if (existingTeacher != null) {
                    redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
                    return "redirect:/teachers/add";
                }
            }
            
            // Set ID mới cho giáo viên mới
            //teacher.setId(null);
            
            Teacher savedTeacher = teacherService.saveNew(teacher);
            if (savedTeacher != null) {
                redirectAttributes.addFlashAttribute("success", "Thêm giáo viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể lưu thông tin giáo viên!");
            }
            return "redirect:/teachers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm giáo viên: " + e.getMessage());
            return "redirect:/teachers/add";
        }
    }

    // Hiển thị form sửa giáo viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Teacher> teacherOpt = teacherService.findById(id);
        if (teacherOpt.isPresent()) {
            model.addAttribute("teacher", teacherOpt.get());
            model.addAttribute("isAdd", false);
            return "teacher/form";
        } else {
            return "redirect:/teachers";
        }
    }

    // Xử lý sửa giáo viên
    @PostMapping("/edit")
    public String editTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra giáo viên có tồn tại không
            Optional<Teacher> dbTeacherOpt = teacherService.findById(teacher.getId());
            if (!dbTeacherOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy giáo viên để cập nhật!");
                return "redirect:/teachers";
            }
            
            // Kiểm tra username có bị trùng không
            if (teacher.getUsername() != null && !teacher.getUsername().trim().isEmpty()) {
                Teacher existingTeacher = teacherService.findByUsername(teacher.getUsername());
                if (existingTeacher != null && !existingTeacher.getId().equals(teacher.getId())) {
                    redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại!");
                    return "redirect:/teachers/edit/" + teacher.getId();
                }
            }
            
            Teacher savedTeacher = teacherService.saveNew(teacher);
            redirectAttributes.addFlashAttribute("success", "Cập nhật giáo viên thành công!");
            return "redirect:/teachers";
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Dữ liệu đã bị cập nhật bởi người khác")) {
                redirectAttributes.addFlashAttribute("error", "Dữ liệu đã bị cập nhật bởi người khác. Vui lòng tải lại trang và thử lại.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Lỗi không xác định khi cập nhật giáo viên: " + e.getMessage());
            }
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật giáo viên: " + e.getMessage());
            return "redirect:/teachers";
        }
    }

    // Xóa giáo viên
    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = teacherService.delete(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("success", "Xóa giáo viên thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa giáo viên!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa giáo viên: " + e.getMessage());
        }
        return "redirect:/teachers";
    }
    
    // Xem chi tiết giáo viên
    @GetMapping("/details/{id}")
    public String showTeacherDetails(@PathVariable UUID id, Model model) {
        Optional<Teacher> teacherOpt = teacherService.findById(id);
        if (teacherOpt.isPresent()) {
            model.addAttribute("teacher", teacherOpt.get());
            return "teacher/details";
        } else {
            return "redirect:/teachers";
        }
    }
}