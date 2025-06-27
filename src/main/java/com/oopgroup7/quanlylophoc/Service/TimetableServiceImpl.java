package com.oopgroup7.quanlylophoc.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Timetable;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Repository.TimetableRepository;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public List<Timetable> getTimetablesByClassId(UUID classId) {
        return timetableRepository.findByClassroom_Id(classId);
    }

    @Override
    public void saveTimetable(UUID classId, Timetable timetable) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        if (classroom != null) {
            timetable.setClassroom(classroom);
            timetableRepository.save(timetable);
        } else {
            throw new IllegalArgumentException("Classroom not found with ID: " + classId);
        }
    }
}