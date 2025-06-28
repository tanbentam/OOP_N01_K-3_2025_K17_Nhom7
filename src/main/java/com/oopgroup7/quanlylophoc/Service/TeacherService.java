package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class TeacherService {

    private static final Logger logger = Logger.getLogger(TeacherService.class.getName());
    
    @Autowired
    private TeacherRepository teacherRepository;

    // Tìm giáo viên theo username
    @Transactional(readOnly = true)
    public Teacher findByUsername(String username) {
        return teacherRepository.findByUsername(username).orElse(null);
    }
    
    // Lấy danh sách tất cả giáo viên
    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
    
    // Lấy danh sách giáo viên với phân trang
    @Transactional(readOnly = true)
    public Page<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }
    
    // Tìm giáo viên theo ID
    @Transactional(readOnly = true)
    public Optional<Teacher> findById(UUID id) {
        return teacherRepository.findById(id);
    }
    
    @Transactional
public Teacher saveNew(Teacher teacher) {
    try {
        // Tạo đối tượng mới hoàn toàn với ID mới
        Teacher newTeacher = new Teacher();
        newTeacher.setName(teacher.getName());
        newTeacher.setSubject(teacher.getSubject());
        newTeacher.setDepartment(teacher.getDepartment());
        newTeacher.setUsername(teacher.getUsername());
        newTeacher.setPassword(teacher.getPassword());
        
        // Set ID và version mới
        //newTeacher.setId(UUID.randomUUID());
        newTeacher.setVersion(0L);
        
        // Đảm bảo ID không trùng lặp
        /*while (teacherRepository.existsById(newTeacher.getId())) {
            newTeacher.setId(UUID.randomUUID());
        }*/
        
        // Lưu và trả về đối tượng mới
        return teacherRepository.save(newTeacher);
    } catch (Exception e) {
        logger.severe("Lỗi khi tạo mới giáo viên: " + e.getMessage());
        throw new RuntimeException("Lỗi khi tạo mới giáo viên: " + e.getMessage(), e);
    }
}

    /*  21:05 25/6/2025 LỖI VCL WTF
    // Lưu thông tin giáo viên
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Teacher saveNew(Teacher teacher) {
        try {
            // Nếu là giáo viên mới và chưa có ID, thì set ID mới
            if (teacher.getId() == null) {

                UUID newId = UUID.randomUUID();
                teacher.setId(newId);
                teacher.setVersion(0L); // Khởi tạo version cho giáo viên mới

                while (teacherRepository.existsById(teacher.getId())) {
                    teacher.setId(UUID.randomUUID()); // Tạo ID mới nếu ID đã tồn tại
                }
                return teacherRepository.save(teacher);

            } 

            // Kiểm tra xem giáo viên đã tồn tại chưa
            Optional<Teacher> existingOpt = teacherRepository.findById(teacher.getId());
        if (existingOpt.isEmpty()) {
            // Nếu ID được cung cấp nhưng không tồn tại, xử lý như bản ghi mới
            teacher.setId(UUID.randomUUID());
            teacher.setVersion(0L);
        } else if (teacher.getVersion() == null) {
            // Nếu cập nhật nhưng version là null, lấy từ DB
            teacher.setVersion(existingOpt.get().getVersion());
        }
            
            return teacherRepository.save(teacher);
        } catch (ObjectOptimisticLockingFailureException e) {
            logger.severe("Lỗi khi lưu thông tin giáo viên: " + e.getMessage());
            throw new RuntimeException("Dữ liệu đã bị cập nhật bởi người khác. Vui lòng tải lại trang và thử lại." + e.getMessage(), e);
        } catch (Exception e) {
            logger.severe("Lỗi khi lưu thông tin giáo viên: " + e.getMessage());
            throw new RuntimeException("Lỗi không xác định khi lưu thông tin giáo viên: " + e.getMessage(), e);
        }
    }*/
    
    // Xóa giáo viên
    @Transactional
    public boolean delete(UUID id) {
        try {
            // Kiểm tra xem giáo viên có tồn tại không
            if (!teacherRepository.existsById(id)) {
                logger.info("Không tìm thấy giáo viên với ID: " + id);
                return false;
            }
            
            Optional<Teacher> teacherOpt = teacherRepository.findById(id);
            String teacherName = teacherOpt.map(Teacher::getName).orElse("Unknown");
            
            logger.info("Đang xóa giáo viên: " + teacherName + " (ID: " + id + ")");
            
            // Xóa giáo viên
            teacherRepository.deleteById(id);
            
            boolean stillExists = teacherRepository.existsById(id);
            
            if (!stillExists) {
                logger.info("Xóa giáo viên '" + teacherName + "' thành công!");
                return true;
            } else {
                logger.severe("Lỗi: Giáo viên vẫn tồn tại sau khi xóa");
                return false;
            }
        } catch (Exception e) {
            logger.severe("Lỗi khi xóa giáo viên: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Tìm kiếm giáo viên theo các tiêu chí
    @Transactional(readOnly = true)
    public List<Teacher> searchTeachers(String name, String department, String subject) {
        return teacherRepository.searchTeachers(name, department, subject);
    }
    
    // Tìm giáo viên theo tên
    @Transactional(readOnly = true)
    public List<Teacher> findByNameContaining(String name) {
        return teacherRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Tìm giáo viên theo môn học
    @Transactional(readOnly = true)
    public List<Teacher> findBySubjectContaining(String subject) {
        return teacherRepository.findBySubjectContainingIgnoreCase(subject);
    }
    
    // Tìm giáo viên theo khoa/bộ môn
    @Transactional(readOnly = true)
    public List<Teacher> findByDepartmentContaining(String department) {
        return teacherRepository.findByDepartmentContainingIgnoreCase(department);
    }
}