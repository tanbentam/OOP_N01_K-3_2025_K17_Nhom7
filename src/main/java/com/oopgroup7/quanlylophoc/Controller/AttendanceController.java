package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;
import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Service.AttendanceService;
import com.oopgroup7.quanlylophoc.Service.ClassroomService;
import com.oopgroup7.quanlylophoc.Service.StudentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;
    
    @Autowired
    private ClassroomService classroomService;
    
    @Autowired
    private StudentService studentService;

    /**
     * Kiểm tra quyền truy cập
     */
    private boolean hasRole(HttpSession session, String requiredRole) {
        String currentRole = (String) session.getAttribute("currentUserRole");
        return requiredRole.equals(currentRole);
    }
    
    private boolean isTeacherOrAdmin(HttpSession session) {
        String role = (String) session.getAttribute("currentUserRole");
        return "teacher".equals(role) || "admin".equals(role);
    }
    
    /**
     * Trang chủ điểm danh - hiển thị form chọn lớp hoặc chuyển hướng học sinh
     */
    @GetMapping
    public String index(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra nếu là học sinh thì chuyển đến trang xem điểm danh của học sinh
        if (hasRole(session, "student")) {
            return "redirect:/attendance/student";
        }
        
        // Chỉ giáo viên và admin có thể truy cập chức năng quản lý điểm danh
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập chức năng này");
            return "redirect:/login";
        }
        
        List<Classroom> classrooms = classroomService.findAll();
        model.addAttribute("classrooms", classrooms);
        model.addAttribute("today", LocalDate.now());
        return "attendance/index";
    }

    
     //Hiển thị form điểm danh sau khi chọn lớp
     
    @RequestMapping(value = "/form", method = {RequestMethod.GET, RequestMethod.POST})
    public String takeAttendanceForm(
            @RequestParam(required = false) UUID classId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
            
        // Chỉ giáo viên và admin có thể truy cập chức năng điểm danh
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập chức năng này");
            return "redirect:/login";
        }
        
        // Code hiện tại giữ nguyên
        if (classId == null) {
            return "redirect:/attendance";
        }
        
        LocalDate attendanceDate = date != null ? date : LocalDate.now();
        
        Optional<Classroom> classroomOpt = classroomService.findById(classId);
        if (!classroomOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lớp học");
            return "redirect:/attendance";
        }
        
        Classroom classroom = classroomOpt.get();
        List<Student> students = studentService.findStudentsByClassroomId(classId);
        
        if (students.isEmpty()) {
            model.addAttribute("warningMessage", "Lớp học này chưa có học sinh nào");
        }
        
        Map<UUID, Boolean> attendanceStatus = new HashMap<>();
        Map<UUID, Boolean> permissionStatus = new HashMap<>();
        
        for (Student student : students) {
            attendanceStatus.put(student.getId(), attendanceService.isStudentPresentOnDate(student.getId(), attendanceDate));
            permissionStatus.put(student.getId(), attendanceService.hasStudentPermissionOnDate(student.getId(), attendanceDate));
        }
        
        model.addAttribute("classroom", classroom);
        model.addAttribute("students", students);
        model.addAttribute("attendanceDate", attendanceDate);
        model.addAttribute("attendanceStatus", attendanceStatus);
        model.addAttribute("permissionStatus", permissionStatus);
        
        return "attendance/form";
    }
    
    /**
     * Xử lý lưu kết quả điểm danh
     */
    @PostMapping("/save")
    public String saveAttendance(
            @RequestParam UUID classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "studentIds", required = false) List<UUID> presentStudentIds,
            @RequestParam(value = "permissions", required = false) List<UUID> permissionStudentIds,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền thực hiện điểm danh");
            return "redirect:/login";
        }
        
        try {
            List<Student> allStudents = studentService.findStudentsByClassroomId(classId);
            
            for (Student student : allStudents) {
                boolean present = presentStudentIds != null && presentStudentIds.contains(student.getId());
                boolean permission = !present && permissionStudentIds != null && permissionStudentIds.contains(student.getId());
                
                attendanceService.markAttendance(student.getId(), date, present, permission);
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Đã lưu điểm danh thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi lưu điểm danh: " + e.getMessage());
        }
        
        return "redirect:/attendance";
    }
    
    
     // Hiển thị form báo cáo điểm danh
     
    

    // Hiển thị form chọn lớp để xem các ngày đã điểm danh
// Lấy danh sách lớp
@GetMapping("/report")
    public String showClassrooms(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập chức năng này");
            return "redirect:/login";
        }
        
        List<Classroom> classrooms = classroomService.findAll();
        model.addAttribute("classrooms", classrooms);
        return "attendance/report";
    }


// Lấy các ngày đã điểm danh của lớp
@GetMapping("/report/{classroomId}")
    public String showAttendanceDates(@PathVariable UUID classroomId, Model model, 
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập chức năng này");
            return "redirect:/login";
        }
        
        List<LocalDate> dates = attendanceService.getAttendanceDatesByClassroom(classroomId);
        Classroom classroom = classroomService.findById(classroomId).orElse(null);
        model.addAttribute("dates", dates);
        model.addAttribute("classroom", classroom);
        model.addAttribute("classroomId", classroomId);
        return "attendance/dates";
    }

// Hiển thị kết quả điểm danh theo lớp và ngày
@GetMapping("/result")
    public String showAttendanceResult(@RequestParam("classId") UUID classId,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      Model model,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập chức năng này");
            return "redirect:/login";
        }
        
        List<AttendanceRecord> records = attendanceService.getAttendanceByClassroomAndDate(classId, date);
        Classroom classroom = classroomService.findById(classId).orElse(null);
        model.addAttribute("records", records);
        model.addAttribute("classroom", classroom);
        model.addAttribute("date", date);
        return "attendance/result";
    }




// Sửa điểm danh
@GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa điểm danh");
            return "redirect:/login";
        }
        
        AttendanceRecord attendance = attendanceService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID điểm danh không hợp lệ"));
        model.addAttribute("attendance", attendance);

        UUID classId = attendance.getStudent().getClassroomStudents().get(0).getClassroom().getId();
        model.addAttribute("classId", classId);

        return "attendance/edit";
    }
// Cập nhật điểm danh
@PostMapping("/update")
    public String updateAttendance(
            @RequestParam("id") UUID id,
            @RequestParam("classId") UUID classId,
            @RequestParam("status") String status,
            @RequestParam("note") String note,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa điểm danh");
            return "redirect:/login";
        }
        
        AttendanceRecord attendance = attendanceService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID điểm danh không hợp lệ"));

        // Xử lý trạng thái
        if ("present".equals(status)) {
            attendance.setPresent(true);
            attendance.setPermission(false);
        } else if ("absent".equals(status)) {
            attendance.setPresent(false);
            attendance.setPermission(false);
        } else if ("permission".equals(status)) {
            attendance.setPresent(false);
            attendance.setPermission(true);
        }

        attendance.setNote(note);
        attendanceService.save(attendance);

        redirectAttributes.addFlashAttribute("success", "Cập nhật điểm danh thành công!");
        return "redirect:/attendance/result?classId=" + classId + "&date=" + attendance.getDate();
    }

// Xóa điểm danh theo ngày


// Xóa tất cả điểm danh của một lớp trong một ngày
@PostMapping("/deleteByClassAndDate")
    public String deleteByClassAndDate(@RequestParam("classId") UUID classId,
                                      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      RedirectAttributes redirectAttributes,
                                      HttpSession session) {
        // Kiểm tra quyền
        if (!isTeacherOrAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Bạn không có quyền xóa điểm danh");
            return "redirect:/login";
        }
        
        int count = attendanceService.deleteByClassAndDate(classId, date);
        if (count > 0) {
            redirectAttributes.addFlashAttribute("success", "Đã xóa " + count + " bản ghi điểm danh!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không có bản ghi nào để xóa!");
        }
        return "redirect:/attendance/report/" + classId;
    }

    @GetMapping("/student")
    public String showStudentAttendance(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        // Kiểm tra nếu không phải là học sinh
        if (!hasRole(session, "student")) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập với tư cách học sinh để xem thông tin điểm danh");
            return "redirect:/login";
        }
        
        // Lấy thông tin học sinh từ session
        String studentIdStr = (String) session.getAttribute("currentUserId");
        if (studentIdStr == null) {
            redirectAttributes.addFlashAttribute("error", "Không thể xác định thông tin học sinh. Vui lòng đăng nhập lại.");
            return "redirect:/login";
        }
        
        UUID studentId = UUID.fromString(studentIdStr);
        Student student = studentService.findById(studentId).orElse(null);
        
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin học sinh");
            return "redirect:/login";
        }
        
        // Lấy danh sách điểm danh của học sinh
        List<AttendanceRecord> attendanceRecords = attendanceService.getAttendanceByStudentId(studentId);
        
        // Thống kê điểm danh
        int totalDays = attendanceRecords.size();
        int presentDays = 0;
        int absentDays = 0;
        int permissionDays = 0;
        
        for (AttendanceRecord record : attendanceRecords) {
            if (record.isPresent()) {
                presentDays++;
            } else if (record.hasPermission()) {
                permissionDays++;
            } else {
                absentDays++;
            }
        }
        
        model.addAttribute("student", student);
        model.addAttribute("attendanceRecords", attendanceRecords);
        model.addAttribute("totalDays", totalDays);
        model.addAttribute("presentDays", presentDays);
        model.addAttribute("absentDays", absentDays);
        model.addAttribute("permissionDays", permissionDays);
        
        return "attendance/index-student";
    }




     //Xử lý xuất báo cáo điểm danh
     
    /*@PostMapping("/report")
    public String generateReport(
            @RequestParam String className,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String type,
            Model model) {
        
        try {
            List<Student> students = new ArrayList<>();
            
            if ("present".equals(type)) {
                students = attendanceService.getPresentStudents(className, date);
                model.addAttribute("reportType", "Danh sách học sinh có mặt");
            } else {
                students = attendanceService.getAbsentStudents(className, date);
                model.addAttribute("reportType", "Danh sách học sinh vắng mặt");
            }
            
            if (students.isEmpty()) {
                model.addAttribute("infoMessage", "Không có học sinh nào " + 
                                ("present".equals(type) ? "có mặt" : "vắng mặt") + 
                                " trong lớp này vào ngày " + date);
            }
            
            model.addAttribute("students", students);
            model.addAttribute("className", className);
            model.addAttribute("date", date);
            model.addAttribute("formattedDate", date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            return "attendance/result";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tạo báo cáo: " + e.getMessage());
            List<String> classNames = attendanceService.getClassroomNames();
            model.addAttribute("classNames", classNames);
            model.addAttribute("today", LocalDate.now());
            return "attendance/report";
        }
    }*/
}