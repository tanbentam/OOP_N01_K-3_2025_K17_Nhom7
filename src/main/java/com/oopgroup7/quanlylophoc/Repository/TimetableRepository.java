package com.oopgroup7.quanlylophoc.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oopgroup7.quanlylophoc.Model.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findByClassroom_Id(UUID classroomId);
}