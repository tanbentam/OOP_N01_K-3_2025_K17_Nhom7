package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Score;
import com.oopgroup7.quanlylophoc.Model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {
    
@Query("SELECT DISTINCT s.subject FROM Score s ORDER BY s.subject")
    List<String> findDistinctSubjects();
    
   // lấy danh sách điểm theo môn học
    List<Score> findAllBySubjectOrderByValueDesc(String subject);
    

    // Tìm điểm theo học sinh
    List<Score> findByStudent(Student student);
    
    // Tìm điểm theo môn học
    List<Score> findBySubjectContaining(String subject);
    
    // Tìm điểm theo học sinh và môn học
    List<Score> findByStudentAndSubject(Student student, String subject);
    
    // Tìm điểm theo tên học sinh (bao gồm tìm kiếm mờ)
    @Query("SELECT s FROM Score s WHERE LOWER(s.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    List<Score> findByStudentNameContaining(String studentName);
    
    // Phân trang kết quả
    Page<Score> findAll(Pageable pageable);
    
    // Phân trang và tìm kiếm theo môn học
    Page<Score> findBySubjectContaining(String subject, Pageable pageable);
    
    // Phân trang và tìm kiếm theo tên học sinh
    @Query("SELECT s FROM Score s WHERE LOWER(s.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))")
    Page<Score> findByStudentNameContaining(String studentName, Pageable pageable);

    // Tìm điểm theo ID học sinh
    List<Score> findByStudentId(UUID studentId);

}