package com.oopgroup7.quanlylophoc.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Timetable;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Service.TimetableService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TimetableService timetableService;


    private boolean hasPermission(HttpSession session, String... allowedRoles) {
        String userRole = (String) session.getAttribute("currentUserRole");
        if (userRole == null) return false;
        
        for (String role : allowedRoles) {
            if (role.equalsIgnoreCase(userRole)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStudent(HttpSession session) {
        String role = (String) session.getAttribute("currentUserRole");
        return "student".equals(role);
    }

    // Hiển thị danh sách lớp
    @GetMapping
    public String showClassList(Model model, HttpSession session) {
        // Tất cả các role đều có thể xem danh sách lớp
        if (!hasPermission(session, "teacher", "admin", "student")) {
            return "redirect:/login";
        }
        
        List<Classroom> classes = classroomRepository.findAll();
        model.addAttribute("classes", classes);
        model.addAttribute("userRole", session.getAttribute("currentUserRole"));
        return "timetable/timetable-class-list";
    }

    // Hiển thị thời khóa biểu của một lớp
    @GetMapping("/{classId}")
    public String viewClassTimetable(@PathVariable UUID classId, Model model, HttpSession session) {
        // Kiểm tra quyền truy cập
        if (!hasPermission(session, "teacher", "admin", "student")) {
            return "redirect:/login";
        }
        
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        List<Timetable> timetableList = timetableService.getTimetablesByClassId(classId);

        // Tạo Map<Thứ, Map<Tiết, Timetable>> để tra nhanh
        Map<String, Map<Integer, Timetable>> timetableMap = new HashMap<>();
        for (Timetable t : timetableList) {
            String day = t.getDayOfWeek();    // "Thứ 2", ...
            int period = t.getPeriod();       // kiểu int

            timetableMap
                .computeIfAbsent(day, k -> new HashMap<>())
                .put(period, t);              // OK: put(Integer, Timetable)
        }

        model.addAttribute("classroom", classroom);
        model.addAttribute("scheduleMap", timetableMap); // Giữ tên scheduleMap để tương thích với template
        model.addAttribute("daysOfWeek", List.of("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"));
        model.addAttribute("periods", List.of(1, 2, 3, 4, 5));
        model.addAttribute("userRole", session.getAttribute("currentUserRole"));

        // Nếu là học sinh, chuyển hướng đến template chỉ đọc
        if (isStudent(session)) {
            return "timetable/class-timetable-student";
        }
        
        return "timetable/class-timetable";
    }

    // Form thêm buổi học mới - CHỈ TEACHER/ADMIN
    @GetMapping("/{classId}/add")
    public String showAddTimetableForm(@PathVariable UUID classId, Model model, HttpSession session) {
        // Chỉ teacher và admin mới được thêm
        if (!hasPermission(session, "teacher", "admin")) {
            return "redirect:/timetable/" + classId + "?error=permission";
        }
        
        Timetable timetable = new Timetable();
        model.addAttribute("timetable", timetable); // Giữ tên schedule để tương thích với template
        model.addAttribute("classId", classId);
        return "timetable/add-timetable";
    }

    // Xử lý lưu buổi học mới - CHỈ TEACHER/ADMIN
    @PostMapping("/{classId}/add")
    public String addTimetable(@PathVariable UUID classId, @ModelAttribute Timetable timetable, 
                               HttpSession session, RedirectAttributes redirectAttributes) {
        // Chỉ teacher và admin mới được thêm
        if (!hasPermission(session, "teacher", "admin")) {
            return "redirect:/timetable/" + classId + "?error=permission";
        }
        
        // Kiểm tra trùng lặp ngày và tiết
        if (timetableService.existsByClassIdAndDayOfWeekAndPeriod(classId, timetable.getDayOfWeek(), timetable.getPeriod())) {
            redirectAttributes.addFlashAttribute("error", 
                "Đã có môn học khác trong " + timetable.getDayOfWeek() + " - Tiết " + timetable.getPeriod() + 
                ". Vui lòng chọn thời gian khác!");
            return "redirect:/timetable/" + classId;
        }
        
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        timetable.setClassroom(classroom);
        timetableService.saveTimetable(classId, timetable);
        
        redirectAttributes.addFlashAttribute("success", "Thêm tiết học thành công!");
        return "redirect:/timetable/" + classId;
    }

    @GetMapping("/{classId}/edit/{timetableId}")
    public String showEditTimetableForm(@PathVariable UUID classId,
                                        @PathVariable Long timetableId,
                                        Model model, HttpSession session) {
        // Chỉ teacher và admin mới được sửa
        if (!hasPermission(session, "teacher", "admin")) {
            return "redirect:/timetable/" + classId + "?error=permission";
        }
        
        Timetable timetable = timetableService.getById(timetableId);
        if (timetable == null) {
            return "redirect:/timetable/" + classId;
        }

        model.addAttribute("timetable", timetable);
        model.addAttribute("classId", classId);

        return "timetable/edit-timetable"; // ✅ tên file mới
    }

    // Xử lý cập nhật timetable - CHỈ TEACHER/ADMIN
    @PostMapping("/{classId}/edit/{timetableId}")
    public String updateTimetable(@PathVariable UUID classId,
                                  @PathVariable Long timetableId,
                                  @ModelAttribute Timetable timetable, HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        // Chỉ teacher và admin mới được sửa
        if (!hasPermission(session, "teacher", "admin")) {
            return "redirect:/timetable/" + classId + "?error=permission";
        }
        
        // Lấy timetable hiện tại từ database
        Timetable existingTimetable = timetableService.getById(timetableId);
        if (existingTimetable == null) {
            return "redirect:/timetable/" + classId;
        }
        
        // Kiểm tra trùng lặp ngày và tiết (trừ bản ghi hiện tại)
        if (timetableService.existsByClassIdAndDayOfWeekAndPeriodAndIdNot(
                classId, timetable.getDayOfWeek(), timetable.getPeriod(), timetableId)) {
            redirectAttributes.addFlashAttribute("error", 
                "Đã có môn học khác trong " + timetable.getDayOfWeek() + " - Tiết " + timetable.getPeriod() + 
                ". Vui lòng chọn thời gian khác!");
            return "redirect:/timetable/" + classId;
        }
        
        // Cập nhật các trường
        existingTimetable.setDayOfWeek(timetable.getDayOfWeek());
        existingTimetable.setPeriod(timetable.getPeriod());
        existingTimetable.setSubject(timetable.getSubject());
        existingTimetable.setTeacherName(timetable.getTeacherName());
        
        // Lưu lại
        timetableService.save(existingTimetable);
        
        redirectAttributes.addFlashAttribute("success", "Cập nhật tiết học thành công!");
        return "redirect:/timetable/" + classId;
    }

    // Xóa timetable - CHỈ TEACHER/ADMIN
    @PostMapping("/{classId}/delete/{timetableId}")
    public String deleteTimetable(@PathVariable UUID classId,
                                  @PathVariable Long timetableId, 
                                  HttpSession session, RedirectAttributes redirectAttributes) {
        // Chỉ teacher và admin mới được xóa
        if (!hasPermission(session, "teacher", "admin")) {
            return "redirect:/timetable/" + classId + "?error=permission";
        }
        
        timetableService.deleteById(timetableId);
        redirectAttributes.addFlashAttribute("success", "Xóa tiết học thành công!");
        return "redirect:/timetable/" + classId;
    }

}
