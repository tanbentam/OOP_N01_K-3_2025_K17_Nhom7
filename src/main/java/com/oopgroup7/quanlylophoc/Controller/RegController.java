package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Service.StudentService;
import com.oopgroup7.quanlylophoc.Service.TeacherService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Registration Controller quản lý đăng ký của học sinh và giáo viên
 * và cung cấp các chức năng để thêm, xóa, tìm kiếm và quản lý đăng ký.
 */
@Controller
@RequestMapping("/register")
public class RegController {
    
    private static final Logger logger = LoggerFactory.getLogger(RegController.class);
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;
    
    /**
     * Hiển thị trang đăng ký chính
     */
    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("teacher", new Teacher());
        return "register/index";
    }
    
    /**
     * Hiển thị form đăng ký học sinh
     */
    @GetMapping("/student")
    public String showStudentRegistration(Model model) {
        model.addAttribute("student", new Student());
        return "register/student-form";
    }
    
    /**
     * Hiển thị form đăng ký giáo viên
     */
    @GetMapping("/teacher")
    public String showTeacherRegistration(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "register/teacher-form";
    }
    
    @PostMapping("/student")
    public String registerStudent(@Valid @ModelAttribute Student student, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register/student-form";
        }
    
    try {
        // Đảm bảo có username - nếu không có thì dùng studentCode hoặc tạo tự động
        if (student.getUsername() == null || student.getUsername().trim().isEmpty()) {
            if (student.getStudentCode() != null && !student.getStudentCode().trim().isEmpty()) {
                student.setUsername(student.getStudentCode());
            } else {
                // Tạo username tự động từ tên
                String username = student.getName().toLowerCase()
                    .replaceAll("\\s+", "")
                    .replaceAll("[^a-zA-Z0-9]", "");
                student.setUsername(username + System.currentTimeMillis() % 1000);
            }
        }
        
        // Kiểm tra username trùng lặp
        Optional<Student> existingStudent = studentService.findByUsername(student.getUsername());
        if (existingStudent.isPresent()) {
            result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
            return "register/student-form";
        }

        // Đặt password mặc định nếu không có
        if (student.getPassword() == null || student.getPassword().trim().isEmpty()) {
            String password = student.getName()
                .replaceAll("\\s+", "")
                .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .toLowerCase();

            if (password.length() < 3) {
                password = password + "123"; // Thêm số để đảm bảo đủ độ dài
            }
            student.setPassword(password);
        }

        student.setId(null);
        student.setVersion(null);
        
        Student savedStudent = studentService.saveNew(student);
        logger.info("Đã đăng ký thành công học sinh: {} với username: {}", 
                   savedStudent.getName(), savedStudent.getUsername());
        
        redirectAttributes.addFlashAttribute("success", 
            "Đăng ký học sinh thành công! Username: " + savedStudent.getUsername() + 
            ", Password: " + (savedStudent.getPassword() != null ? savedStudent.getPassword() : savedStudent.getName()));
        
        return "redirect:/login";
    } catch (Exception e) {
        logger.error("Lỗi khi đăng ký học sinh: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
        return "redirect:/register/student";
    }
}


    /** PHIÊN BẢN CŨ 
     * Xử lý đăng ký học sinh
     
    @PostMapping("/student")
    public String registerStudent(@Valid @ModelAttribute Student student, 
                                  BindingResult result, 
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register/student-form";
        }
        
        try {
            // Kiểm tra username trùng lặp nếu có
            if (student.getUsername() != null && studentService.findByUsername(student.getUsername()) != null) {
                result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
                return "register/student-form";
            }
            
            studentService.save(student);
            logger.info("Đã đăng ký thành công học sinh: {}", student.getName());
            redirectAttributes.addFlashAttribute("success", "Đăng ký học sinh thành công!");
            return "redirect:/login";
        } catch (Exception e) {
            logger.error("Lỗi khi đăng ký học sinh: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
            return "redirect:/register/student";
        }
    }*/
    
    /**
     * Xử lý đăng ký giáo viên
     * Tạo username tự động từ tên, mật khẩu mặc định, và kiểm tra trùng lặp username.
     */

    @PostMapping("/teacher")
    public String registerTeacher(@Valid @ModelAttribute Teacher teacher, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
    
    // Kiểm tra validation ban đầu
    if (result.hasErrors()) {
        // Loại bỏ lỗi username và password nếu chúng ta sẽ tự tạo
        if ((teacher.getUsername() == null || teacher.getUsername().trim().isEmpty()) &&
            (teacher.getPassword() == null || teacher.getPassword().trim().isEmpty())) {
            
            result.getFieldErrors().removeIf(error -> 
                "username".equals(error.getField()) || "password".equals(error.getField()));
            
            if (result.hasErrors()) {
                return "register/teacher-form";
            }
        } else {
            return "register/teacher-form";
        }
    }
    
    try {
        // Tạo username nếu chưa có
        if (teacher.getUsername() == null || teacher.getUsername().trim().isEmpty()) {
            String username = teacher.getName().toLowerCase()
                .replaceAll("\\s+", "")
                .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .replaceAll("[^a-zA-Z0-9]", "");
            
            username = "gv" + username + (System.currentTimeMillis() % 1000);
            teacher.setUsername(username);
        }
        
        // Kiểm tra username trùng lặp
        if (teacherService.findByUsername(teacher.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("error", 
                "Username '" + teacher.getUsername() + "' đã tồn tại. Vui lòng chọn username khác.");
            redirectAttributes.addFlashAttribute("teacher", teacher);
            return "redirect:/register/teacher";
        }
        
        // Tạo password nếu chưa có
        if (teacher.getPassword() == null || teacher.getPassword().trim().isEmpty()) {
            String password = teacher.getName()
                .replaceAll("\\s+", "")
                .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                .replaceAll("[ìíịỉĩ]", "i")
                .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                .replaceAll("[ùúụủũưừứựửữ]", "u")
                .replaceAll("[ỳýỵỷỹ]", "y")
                .replaceAll("[đ]", "d")
                .toLowerCase();
            
            if (password.length() < 3) {
                password = password + "123";
            }
            
            teacher.setPassword(password);
        }
        
        teacherService.saveNew(teacher);
        logger.info("Đã đăng ký thành công giáo viên: {} với username: {} và password: {}", 
                   teacher.getName(), teacher.getUsername(), teacher.getPassword());
        
        redirectAttributes.addFlashAttribute("success", 
            "Đăng ký giáo viên thành công!<br>" +
            "Username: " + teacher.getUsername() + "<br>" +
            "Password: " + teacher.getPassword() + "<br>" +
            "Vui lòng ghi nhớ thông tin đăng nhập này!");
        
        return "redirect:/login";
        
    } catch (Exception e) {
        logger.error("Lỗi khi đăng ký giáo viên: {}", e.getMessage(), e);
        redirectAttributes.addFlashAttribute("error", 
            "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
        redirectAttributes.addFlashAttribute("teacher", teacher);
        return "redirect:/register/teacher";
    }
}



    /**
     * Xử lý đăng ký giáo viên - Phiên bản cũ, không có tạo username từ tên và mật khẩu. ở đây mình sẽ cập nhật lại đăng ký giáo viên
     * với các tính năng tương tự như học sinh, bao gồm tạo username tự động từ tên, mật khẩu mặc định,
     * và kiểm tra trùng lặp username.
     
    @PostMapping("/teacher")
    public String registerTeacher(@Valid @ModelAttribute Teacher teacher, 
                                  BindingResult result, 
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register/teacher-form";
        }
        
        try {
            // Kiểm tra username trùng lặp nếu có
            if (teacher.getUsername() != null && teacherService.findByUsername(teacher.getUsername()) != null) {
                result.rejectValue("username", "error.user", "Tên đăng nhập đã tồn tại");
                return "register/teacher-form";
            }
            Teacher savedTeacher = teacherService.saveNew(teacher);
        
        logger.info("Đã đăng ký thành công giáo viên: {}", savedTeacher.getName());
        redirectAttributes.addFlashAttribute("success", "Đăng ký giáo viên thành công!");
        return "redirect:/login";
    } catch (Exception e) {
        logger.error("Lỗi khi đăng ký giáo viên: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đăng ký: " + e.getMessage());
        return "redirect:/register/teacher";
        }
    }*/
    
    /**
     * API để kiểm tra tính khả dụng của tên đăng nhập
     */
    @GetMapping("/check-username")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username, @RequestParam String role) {
        boolean isAvailable = true;
        
        if ("student".equals(role)) {
            isAvailable = studentService.findByUsername(username) == null;
        } else if ("teacher".equals(role)) {
            isAvailable = teacherService.findByUsername(username) == null;
        }
        
        return ResponseEntity.ok(isAvailable);
    }
    
    /**
     * Trang quản trị - liệt kê danh sách người dùng đã đăng ký
     */
    @GetMapping("/admin/users")
    public String listRegisteredUsers(Model model, 
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        // Phân trang cho học sinh
        Pageable pageable = PageRequest.of(page, size);
        List<Student> students = studentService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        
        model.addAttribute("students", students);
        model.addAttribute("teachers", teachers);
        model.addAttribute("studentCount", students.size());
        model.addAttribute("teacherCount", teachers.size());
        model.addAttribute("currentPage", page);
        
        return "register/admin/users";
    }
    
    /**
     * API tìm kiếm học sinh theo tên
     */
    @GetMapping("/api/search/students")
    @ResponseBody
    public ResponseEntity<List<Student>> findStudentsByName(@RequestParam String name) {
        try {
            List<Student> students = studentService.findAll();
            List<Student> result = students.stream()
                .filter(s -> s.getName() != null && s.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm học sinh: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * API tìm kiếm giáo viên theo tên
     */
    @GetMapping("/api/search/teachers")
    @ResponseBody
    public ResponseEntity<List<Teacher>> findTeachersByName(@RequestParam String name) {
        try {
            List<Teacher> teachers = teacherService.findAll();
            List<Teacher> result = teachers.stream()
                .filter(t -> t.getName() != null && t.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm giáo viên: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * API tìm kiếm giáo viên theo môn học
     */
    @GetMapping("/api/search/teachers/by-subject")
    @ResponseBody
    public ResponseEntity<List<Teacher>> findTeachersBySubject(@RequestParam String subject) {
        try {
            List<Teacher> teachers = teacherService.findAll();
            List<Teacher> result = teachers.stream()
                .filter(t -> t.getSubject() != null && t.getSubject().equalsIgnoreCase(subject))
                .toList();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Lỗi khi tìm kiếm giáo viên theo môn học: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Xóa đăng ký học sinh (quyền admin)
     */
    @PostMapping("/admin/student/remove/{id}")
    public String removeStudent(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            if (studentService.delete(id)) {
                redirectAttributes.addFlashAttribute("success", "Đã xóa học sinh thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa học sinh");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa học sinh: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa học sinh: " + e.getMessage());
        }
        return "redirect:/register/admin/users";
    }
    
    /**
     * Xóa đăng ký giáo viên (quyền admin)
     */
    @PostMapping("/admin/teacher/remove/{id}")
    public String removeTeacher(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            if (teacherService.delete(id)) {
                redirectAttributes.addFlashAttribute("success", "Đã xóa giáo viên thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa giáo viên");
            }
        } catch (Exception e) {
            logger.error("Lỗi khi xóa giáo viên: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa giáo viên: " + e.getMessage());
        }
        return "redirect:/register/admin/users";
    }
}