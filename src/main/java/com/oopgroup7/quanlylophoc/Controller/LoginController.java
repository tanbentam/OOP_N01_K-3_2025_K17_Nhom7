//Controller cho chức năng đăng nhập với học sinh vào giáo viên

package com.oopgroup7.quanlylophoc.Controller;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Model.Student;
import java.util.HashMap;
import java.util.Map;

public class LoginController {
    // This class is currently empty, but it can be used to handle login-related logic in the future.
    // For example, you might want to add methods for user authentication, session management, etc.
    
    private Map<String, String> userCredentials;
    private Map<String, Object> userObjects;
    private String currentUserId;
    private String currentUserRole; //student hoac teacher
    // Placeholder for future implementation
    public LoginController() {
        userCredentials = new HashMap<>();
        userObjects = new HashMap<>();
        currentUserId = null;
        currentUserRole = null;
    }

    
}