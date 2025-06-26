package com.oopgroup7.quanlylophoc.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Schedule;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public List<Schedule> getSchedulesByClassId(UUID classId) {
        return scheduleRepository.findByClassroom_Id(classId);
    }

    @Override
    public void saveSchedule(UUID classId, Schedule schedule) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        if (classroom != null) {
            schedule.setClassroom(classroom);
            scheduleRepository.save(schedule);
        } else {
            throw new IllegalArgumentException("Classroom not found with ID: " + classId);
        }
    }
}  
