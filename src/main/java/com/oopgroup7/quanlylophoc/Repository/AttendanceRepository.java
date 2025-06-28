package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, UUID> {

    // Lấy danh sách ngày điểm danh theo lớp (bảng trung gian)
    @Query("SELECT DISTINCT a.date FROM AttendanceRecord a WHERE a.student.id IN (SELECT s.id FROM Student s JOIN s.classroomStudents cs WHERE cs.classroom.id = :classroomId)")
    List<LocalDate> findDistinctDatesByClassroomId(@Param("classroomId") UUID classroomId);

    @Query("SELECT a FROM AttendanceRecord a WHERE a.student.id IN (" +
       "SELECT cs.student.id FROM ClassroomStudent cs WHERE cs.classroom.id = :classroomId" +
       ") AND a.date = :date")
List<AttendanceRecord> findByClassroomIdAndDate(@Param("classroomId") UUID classroomId, @Param("date") LocalDate date);
    // Xóa tất cả điểm danh của một lớp trong một ngày (native query cho bảng trung gian)
    @Modifying
    @Query(value = "DELETE ar FROM attendance_records ar " +
                   "JOIN classroom_student cs ON ar.student_id = cs.student_id " +
                   "WHERE cs.classroom_id = :classroomId AND ar.attendance_date = :date", nativeQuery = true)
    int deleteByClassroomIdAndDate(@Param("classroomId") UUID classroomId, @Param("date") LocalDate date);

    List<AttendanceRecord> findByStudentIdAndDate(UUID studentId, LocalDate date);
    List<AttendanceRecord> findByStudentIdAndDateBetween(UUID studentId, LocalDate startDate, LocalDate endDate);
    List<AttendanceRecord> findByDate(LocalDate date);
}