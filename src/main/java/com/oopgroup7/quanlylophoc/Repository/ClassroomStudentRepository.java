package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassroomStudentRepository extends JpaRepository<ClassroomStudent, ClassroomStudentId> {
    
    // Tìm tất cả ClassroomStudent theo studentId
    @Query("SELECT cs FROM ClassroomStudent cs WHERE cs.student.id = :studentId")
    List<ClassroomStudent> findByStudentId(@Param("studentId") UUID studentId);
    
    // Tìm tất cả ClassroomStudent theo classroomId
    @Query("SELECT cs FROM ClassroomStudent cs WHERE cs.classroom.id = :classroomId")
    List<ClassroomStudent> findByClassroomId(@Param("classroomId") UUID classroomId);
    
    // Kiểm tra xem mối quan hệ giữa student và classroom đã tồn tại chưa
    @Query("SELECT COUNT(cs) > 0 FROM ClassroomStudent cs WHERE cs.student.id = :studentId AND cs.classroom.id = :classroomId")
    boolean existsByStudentIdAndClassroomId(@Param("studentId") UUID studentId, @Param("classroomId") UUID classroomId);
    
    // Đếm số lớp học mà một học sinh tham gia
    @Query("SELECT COUNT(cs) FROM ClassroomStudent cs WHERE cs.student.id = :studentId")
    long countClassroomsByStudentId(@Param("studentId") UUID studentId);
    
    // Đếm số học sinh trong một lớp
    @Query("SELECT COUNT(cs) FROM ClassroomStudent cs WHERE cs.classroom.id = :classroomId")
    long countStudentsByClassroomId(@Param("classroomId") UUID classroomId);
    
    // Xóa tất cả các liên kết của một học sinh
    @Modifying
    @Query("DELETE FROM ClassroomStudent cs WHERE cs.student.id = :studentId")
    void deleteAllByStudentId(@Param("studentId") UUID studentId);
    
    // Xóa tất cả các liên kết của một lớp học
    @Modifying
    @Query("DELETE FROM ClassroomStudent cs WHERE cs.classroom.id = :classroomId")
    void deleteAllByClassroomId(@Param("classroomId") UUID classroomId);
    
    // Xóa một liên kết cụ thể giữa học sinh và lớp học
    @Modifying
    @Query("DELETE FROM ClassroomStudent cs WHERE cs.student.id = :studentId AND cs.classroom.id = :classroomId")
    void deleteByStudentIdAndClassroomId(@Param("studentId") UUID studentId, @Param("classroomId") UUID classroomId);
}