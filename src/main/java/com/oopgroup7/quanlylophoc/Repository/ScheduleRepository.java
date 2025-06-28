package com.oopgroup7.quanlylophoc.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oopgroup7.quanlylophoc.Model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByClassroom_Id(UUID classroomId);
}
