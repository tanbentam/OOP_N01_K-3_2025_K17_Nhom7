package com.oopgroup7.quanlylophoc.Controller;

import java.util.List;
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
import com.oopgroup7.quanlylophoc.Repository.ScheduleRepository;

@Controller
@RequestMapping("/timetable")
public class TimetableController {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    // Hiển thị danh sách lớp
    @GetMapping
    public String showClassList(Model model) {
        List<Classroom> classes = classroomRepository.findAll();
        model.addAttribute("classes", classes);
        return "timetable-class-list";
    }

    // Hiển thị thời khóa biểu của một lớp
    @GetMapping("/{classId}")
    public String viewClassSchedule(@PathVariable UUID classId, Model model) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        List<Schedule> scheduleList = scheduleRepository.findByClassroom_Id(classId);
        model.addAttribute("classroom", classroom);
        model.addAttribute("schedules", scheduleList);
        return "class-timetable";
    }

    // Form thêm buổi học mới
    @GetMapping("/{classId}/add")
    public String showAddScheduleForm(@PathVariable UUID classId, Model model) {
        Schedule schedule = new Schedule();
        model.addAttribute("schedule", schedule);
        model.addAttribute("classId", classId);
        return "add-schedule";
    }

    // Xử lý lưu buổi học mới
    @PostMapping("/{classId}/add")
    public String addSchedule(@PathVariable UUID classId, @ModelAttribute Schedule schedule) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        schedule.setClassroom(classroom);
        scheduleRepository.save(schedule);
        return "redirect:/timetable/" + classId;
    }
}

