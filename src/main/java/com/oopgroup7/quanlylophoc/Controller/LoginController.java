package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Service.StudentService;
import com.oopgroup7.quanlylophoc.Service.TeacherService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.List;

@Controller
public class LoginController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private TeacherService teacherService;
    
    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // Nếu đã đăng nhập rồi thì chuyển đến dashboard
        if (session.getAttribute("currentUser") != null) {
            return "redirect:/dashboard";
        }
        return "Login/index";
    }
    
    // Xử lý đăng nhập
    // ...existing code...

@PostMapping("/login")
public String processLogin(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
    
    boolean authenticated = false;
    
    // Kiểm tra admin đặc biệt
    if ("admin".equals(username) && "admin123".equals(password) && "teacher".equals(role)) {
        session.setAttribute("currentUser", "Administrator");
        session.setAttribute("currentUserRole", "admin");
        session.setAttribute("currentUserId", "admin");
        session.setAttribute("currentUserName", "Administrator");
        return "redirect:/admin/dashboard";
    }
    
    if ("student".equals(role)) {
        // **SỬA ĐỔI: Sử dụng Optional<Student>**
        Optional<Student> studentOpt = studentService.findByUsername(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // Kiểm tra password (nếu có) hoặc dùng name tạm thời
            if ((student.getPassword() != null && student.getPassword().equals(password)) ||
                (student.getPassword() == null && student.getName().equals(password))) {
                
                session.setAttribute("currentUser", student);
                session.setAttribute("currentUserRole", "student");
                session.setAttribute("currentUserId", student.getId().toString());
                session.setAttribute("currentUserName", student.getName());
                authenticated = true;
                return "redirect:/student/dashboard";
            }
        }
    } else if ("teacher".equals(role)) {
        // **TƯƠNG TỰ CHO TEACHER NẾU CẦN**
        Teacher teacher = teacherService.findByUsername(username);
        if (teacher != null) {
            // Kiểm tra password (nếu có) hoặc dùng name tạm thời
            if ((teacher.getPassword() != null && teacher.getPassword().equals(password))) {
                
                session.setAttribute("currentUser", teacher);
                session.setAttribute("currentUserRole", "teacher");
                session.setAttribute("currentUserId", teacher.getId().toString());
                session.setAttribute("currentUserName", teacher.getName());
                authenticated = true;
                return "redirect:/teacher/dashboard";
            }
        }
    }
    
    if (!authenticated) {
        redirectAttributes.addFlashAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
        return "redirect:/login";
    }
    
    return "redirect:/login";
}

// ...existing code...
    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("success", "Đã đăng xuất thành công");
        return "redirect:/login";
    }
    
    // Dashboard cho học sinh
    @GetMapping("/student/dashboard")
    public String studentDashboard(HttpSession session, Model model) {
        if (!hasRole(session, "student")) {
            return "redirect:/login";
        }
        
        Student student = (Student) session.getAttribute("currentUser");
        model.addAttribute("currentUser", student);
        model.addAttribute("userName", student.getName());
        
        return "Dashboard/student";
    }
    
    // Dashboard cho giáo viên
    @GetMapping("/teacher/dashboard")
    public String teacherDashboard(HttpSession session, Model model) {
        if (!hasRole(session, "teacher")) {
            return "redirect:/login";
        }
        
        Teacher teacher = (Teacher) session.getAttribute("currentUser");
        model.addAttribute("currentUser", teacher);
        model.addAttribute("userName", teacher.getName());
        model.addAttribute("subject", teacher.getSubject());
        
        return "Dashboard/teacher";
    }
    
    // Dashboard cho admin
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!hasRole(session, "admin")) {
            return "redirect:/login";
        }
        
        // Thống kê cho admin
        List<Student> students = studentService.findAll();
        List<Teacher> teachers = teacherService.findAll();
        
        model.addAttribute("studentCount", students.size());
        model.addAttribute("teacherCount", teachers.size());
        model.addAttribute("userName", "Administrator");
        
        return "Admin/index";
    }
    
    // Kiểm tra quyền truy cập
    public static boolean hasRole(HttpSession session, String requiredRole) {
        String currentRole = (String) session.getAttribute("currentUserRole");
        return requiredRole.equals(currentRole);
    }
}