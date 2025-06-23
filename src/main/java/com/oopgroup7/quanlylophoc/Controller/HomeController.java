package com.oopgroup7.quanlylophoc.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(HttpSession session) {
        // Kiểm tra nếu đã đăng nhập thì chuyển đến dashboard
        if (session.getAttribute("currentUser") != null) {
            String role = (String) session.getAttribute("currentUserRole");
            if ("student".equals(role)) {
                return "redirect:/student/dashboard";
            } else if ("teacher".equals(role)) {
                return "redirect:/teacher/dashboard";
            } else if ("admin".equals(role)) {
                return "redirect:/admin/dashboard";
            }
        }
        // Nếu chưa đăng nhập thì chuyển đến trang đăng nhập
        return "redirect:/login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        // Kiểm tra đã đăng nhập chưa
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }
        
        String role = (String) session.getAttribute("currentUserRole");
        if ("student".equals(role)) {
            return "redirect:/student/dashboard";
        } else if ("teacher".equals(role)) {
            return "redirect:/teacher/dashboard";
        } else if ("admin".equals(role)) {
            return "redirect:/admin/dashboard";
        }
        
        return "redirect:/login";
    }
}