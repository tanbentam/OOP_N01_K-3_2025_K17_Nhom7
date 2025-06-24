package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository  // THÊM ANNOTATION NÀY
public interface StudentRepository extends JpaRepository<Student, UUID> {
    
    // Tìm học sinh theo username (nếu có chức năng đăng nhập)
    Optional<Student> findByUsername(String username);
    
    // Tìm học sinh theo mã học sinh (studentCode)
    Optional<Student> findByStudentCode(String studentCode);
    
    // Kiểm tra mã học sinh đã tồn tại chưa (loại trừ học sinh với id đã cho)
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.studentCode = :studentCode AND (:id IS NULL OR s.id <> :id)")
    boolean existsByStudentCodeAndIdNot(@Param("studentCode") String studentCode, @Param("id") UUID id);
    
    // Tìm học sinh theo tên - không phân biệt hoa thường
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findByNameContainingIgnoreCase(@Param("name") String name);
    
    // Tìm học sinh theo lớp
    @Query("SELECT s FROM Student s WHERE s.className = :className")
    List<Student> findByClassName(@Param("className") String className);
    
    // Tìm học sinh theo số điện thoại
    Optional<Student> findByPhoneNumber(String phoneNumber);
    
    // THÊM METHOD XÓA AN TOÀN
    @Modifying
    @Transactional
    @Query("DELETE FROM Student s WHERE s.id = :id")
    void deleteStudentById(@Param("id") UUID id);
    
    // THÊM METHOD KIỂM TRA QUAN HỆ
    @Query("SELECT COUNT(cs) FROM ClassroomStudent cs WHERE cs.student.id = :studentId")
    Long countClassroomRelationships(@Param("studentId") UUID studentId);
    
    // Tìm kiếm nâng cao với nhiều tiêu chí
    @Query("SELECT s FROM Student s WHERE " +
           "(:name IS NULL OR :name = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:className IS NULL OR :className = '' OR s.className = :className) AND " +
           "(:studentCode IS NULL OR :studentCode = '' OR s.studentCode = :studentCode) AND " +
           "(:gender IS NULL OR :gender = '' OR s.gender = :gender) AND " +
           "(:email IS NULL OR :email = '' OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:minAge IS NULL OR s.age >= :minAge) AND " +
           "(:maxAge IS NULL OR s.age <= :maxAge) AND " +
           "(:fromDate IS NULL OR s.dateOfBirth >= :fromDate) AND " +
           "(:toDate IS NULL OR s.dateOfBirth <= :toDate)")
    List<Student> advancedSearch(
            @Param("name") String name,
            @Param("className") String className,
            @Param("studentCode") String studentCode,
            @Param("gender") String gender,
            @Param("email") String email,
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);
    
    // Tìm kiếm đơn giản với 5 tiêu chí - CẬP NHẬT LẠI
    @Query("SELECT s FROM Student s WHERE " +
           "(:name IS NULL OR :name = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:className IS NULL OR :className = '' OR LOWER(s.className) LIKE LOWER(CONCAT('%', :className, '%'))) AND " +
           "(:studentCode IS NULL OR :studentCode = '' OR s.studentCode LIKE CONCAT('%', :studentCode, '%')) AND " +
           "(:gender IS NULL OR :gender = '' OR s.gender = :gender) AND " +
           "(:email IS NULL OR :email = '' OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%')))")
    List<Student> searchStudents(
            @Param("name") String name,
            @Param("className") String className,
            @Param("studentCode") String studentCode,
            @Param("gender") String gender,
            @Param("email") String email);
    
    // Tìm học sinh theo lớp và sắp xếp theo tên
    List<Student> findByClassNameOrderByNameAsc(String className);
    
    // Tìm học sinh theo độ tuổi
    List<Student> findByAgeBetween(int minAge, int maxAge);
    
    // Tìm học sinh theo tên và lớp
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
           "s.className = :className")
    List<Student> findByNameContainingIgnoreCaseAndClassName(
            @Param("name") String name, 
            @Param("className") String className);
    
    // Tìm học sinh theo giới tính
    List<Student> findByGender(String gender);
    
    // Tìm kiếm theo email
    @Query("SELECT s FROM Student s WHERE LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<Student> findByEmailContaining(@Param("email") String email);
    
    // Lấy học sinh với version hiện tại
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    Optional<Student> findWithCurrentVersion(@Param("id") UUID id);
    
    // Lấy danh sách các lớp học duy nhất
    @Query("SELECT DISTINCT s.className FROM Student s WHERE s.className IS NOT NULL AND s.className <> '' ORDER BY s.className")
List<String> findDistinctClassNames();

// Kiểm tra học sinh với version cụ thể
@Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.id = :id AND s.version = :version")
boolean existsByIdAndVersion(@Param("id") UUID id, @Param("version") Long version);

// THÊM METHOD HỖ TRỢ XÓA
@Query("SELECT s FROM Student s LEFT JOIN FETCH s.classroomStudents WHERE s.id = :id")
Optional<Student> findByIdWithRelations(@Param("id") UUID id);

}