package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//thiết kế lại file teacherRepository.java
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    
    // Tìm giáo viên theo username
    Optional<Teacher> findByUsername(String username);
    
    // Tìm kiếm giáo viên theo tên (không phân biệt hoa thường)
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Teacher> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Tìm giáo viên theo môn học
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Teacher> findBySubjectContainingIgnoreCase(@Param("subject") String subject);
    
    // Tìm kiếm giáo viên theo khoa/bộ môn
    @Query("SELECT t FROM Teacher t WHERE LOWER(t.department) LIKE LOWER(CONCAT('%', :department, '%'))")
    List<Teacher> findByDepartmentContainingIgnoreCase(@Param("department") String department);
    
    // Tìm kiếm nâng cao với nhiều tiêu chí
    @Query("SELECT t FROM Teacher t WHERE " +
           "(:name IS NULL OR :name = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:department IS NULL OR :department = '' OR LOWER(t.department) LIKE LOWER(CONCAT('%', :department, '%'))) AND " +
           "(:subject IS NULL OR :subject = '' OR LOWER(t.subject) LIKE LOWER(CONCAT('%', :subject, '%')))")
    List<Teacher> searchTeachers(
            @Param("name") String name, 
            @Param("department") String department, 
            @Param("subject") String subject);
}