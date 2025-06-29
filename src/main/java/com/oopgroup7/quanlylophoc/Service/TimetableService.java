package com.oopgroup7.quanlylophoc.Service;

import java.util.List;
import java.util.UUID;

import com.oopgroup7.quanlylophoc.Model.Timetable;

public interface TimetableService {
    List<Timetable> getTimetablesByClassId(UUID classId);
    void saveTimetable(UUID classId, Timetable timetable);

    Timetable getById(Long id);                  // ✅ thêm dòng này
    void save(Timetable timetable);
    void deleteById(Long id);                    // ✅ thêm phương thức delete
    
    // Kiểm tra xem có timetable nào trùng ngày và tiết không
    boolean existsByClassIdAndDayOfWeekAndPeriod(UUID classId, String dayOfWeek, int period);
    
    // Kiểm tra xem có timetable nào trùng ngày và tiết không (trừ timetable hiện tại khi edit)
    boolean existsByClassIdAndDayOfWeekAndPeriodAndIdNot(UUID classId, String dayOfWeek, int period, Long excludeId);
}