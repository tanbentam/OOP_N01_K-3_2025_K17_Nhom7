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

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Timetable;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Service.TimetableService;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private TimetableService timetableService;

    // Hiển thị danh sách lớp
    @GetMapping
    public String showClassList(Model model) {
        List<Classroom> classes = classroomRepository.findAll();
        model.addAttribute("classes", classes);
        return "timetable/timetable-class-list";
    }

    // Hiển thị thời khóa biểu của một lớp
    @GetMapping("/{classId}")
    public String viewClassTimetable(@PathVariable UUID classId, Model model) {
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

        return "timetable/class-timetable";
    }

    // Form thêm buổi học mới
    @GetMapping("/{classId}/add")
    public String showAddTimetableForm(@PathVariable UUID classId, Model model) {
        Timetable timetable = new Timetable();
        model.addAttribute("timetable", timetable); // Giữ tên schedule để tương thích với template
        model.addAttribute("classId", classId);
        return "timetable/add-timetable";
    }

    // Xử lý lưu buổi học mới
    @PostMapping("/{classId}/add")
    public String addTimetable(@PathVariable UUID classId, @ModelAttribute Timetable timetable) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        timetable.setClassroom(classroom);
        timetableService.saveTimetable(classId, timetable);
        return "redirect:/timetable/" + classId;
    }

    @GetMapping("/{classId}/edit/{timetableId}")
public String showEditTimetableForm(@PathVariable UUID classId,
                                    @PathVariable Long timetableId,
                                    Model model) {
    Timetable timetable = timetableService.getById(timetableId);
    if (timetable == null) {
        return "redirect:/timetable/" + classId;
    }

    model.addAttribute("timetable", timetable);
    model.addAttribute("classId", classId);

    return "timetable/edit-timetable"; // ✅ tên file mới
}
}
