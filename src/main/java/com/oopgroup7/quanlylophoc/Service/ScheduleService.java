package com.oopgroup7.quanlylophoc.Service;

import java.util.List;
import java.util.UUID;

import com.oopgroup7.quanlylophoc.Model.Schedule;

public interface ScheduleService {
    List<Schedule> getSchedulesByClassId(UUID classId);
    void saveSchedule(UUID classId, Schedule schedule);
}

