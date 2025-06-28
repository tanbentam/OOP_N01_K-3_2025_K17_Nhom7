package com.oopgroup7.quanlylophoc.Service;

import java.util.List;
import java.util.UUID;

import com.oopgroup7.quanlylophoc.Model.Timetable;

public interface TimetableService {
    List<Timetable> getTimetablesByClassId(UUID classId);
    void saveTimetable(UUID classId, Timetable timetable);
}