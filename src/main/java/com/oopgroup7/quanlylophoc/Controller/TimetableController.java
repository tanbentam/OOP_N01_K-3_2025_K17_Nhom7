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
import com.oopgroup7.quanlylophoc.Model.Schedule;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Service.ScheduleService;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ScheduleService scheduleService;

    // Hiển thị danh sách lớp
    @GetMapping
    public String showClassList(Model model) {
        List<Classroom> classes = classroomRepository.findAll();
        model.addAttribute("classes", classes);
        return "timetable/timetable-class-list";
    }

    // Hiển thị thời khóa biểu của một lớp
 @GetMapping("/{classId}")
public String viewClassSchedule(@PathVariable UUID classId, Model model) {
    Classroom classroom = classroomRepository.findById(classId).orElse(null);
    List<Schedule> scheduleList = scheduleService.getSchedulesByClassId(classId);

    // Tạo Map<Thứ, Map<Tiết, Schedule>> để tra nhanh
    Map<String, Map<Integer, Schedule>> scheduleMap = new HashMap<>();
    for (Schedule s : scheduleList) {
        String day = s.getDayOfWeek();    // "Thứ 2", ...
        int period = s.getPeriod();       // kiểu int

        scheduleMap
            .computeIfAbsent(day, k -> new HashMap<>())
            .put(period, s);              // OK: put(Integer, Schedule)
    }

    model.addAttribute("classroom", classroom);
    model.addAttribute("scheduleMap", scheduleMap); // ✅ Thêm dòng này để Thymeleaf dùng được
    model.addAttribute("daysOfWeek", List.of("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"));
    model.addAttribute("periods", List.of(1, 2, 3, 4, 5));

    return "timetable/class-timetable";
}


    // Form thêm buổi học mới
    @GetMapping("/{classId}/add")
    public String showAddScheduleForm(@PathVariable UUID classId, Model model) {
        Schedule schedule = new Schedule();
        model.addAttribute("schedule", schedule);
        model.addAttribute("classId", classId);
        return "timetable/add-schedule";
    }

    // Xử lý lưu buổi học mới
    @PostMapping("/{classId}/add")
    public String addSchedule(@PathVariable UUID classId, @ModelAttribute Schedule schedule) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        schedule.setClassroom(classroom);
        scheduleService.saveSchedule(classId, schedule);
        return "redirect:/timetable/" + classId;
    }
}

