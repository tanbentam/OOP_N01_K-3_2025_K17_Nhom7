/*package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    // Đổi từ Optional sang List để tránh lỗi
    List<Classroom> findByClassNameIgnoreCase(String className);
    
    // Thêm phương thức để tìm lớp đầu tiên (để dùng khi cần một kết quả duy nhất)
    default Optional<Classroom> findFirstByClassNameIgnoreCase(String className) {
        List<Classroom> results = findByClassNameIgnoreCase(className);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}*/
package com.oopgroup7.quanlylophoc.Repository;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
    // Đảm bảo phương thức này trả về List<Classroom> để khớp với AttendanceService
    List<Classroom> findByClassNameIgnoreCase(String className);
    
    // Phương thức tiện ích để tìm lớp đầu tiên theo tên khi cần một kết quả duy nhất
    default Optional<Classroom> findFirstByClassNameIgnoreCase(String className) {
        List<Classroom> results = findByClassNameIgnoreCase(className);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    
    // Các phương thức tìm kiếm khác nếu cần
    @Query("SELECT c FROM Classroom c WHERE LOWER(c.className) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Classroom> searchByClassName(@Param("keyword") String keyword);
}