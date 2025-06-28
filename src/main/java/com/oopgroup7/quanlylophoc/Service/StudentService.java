package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudentId;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Repository.ClassroomStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {
@Autowired
    private ClassroomRepository classroomRepository;
    
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Các phương thức cơ bản
    @Transactional(readOnly = true)
    public Optional<Student> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
        return Optional.empty();
    }
    
        try {
        // Tìm theo username
             List<Student> students = studentRepository.findAll();
             Student student = students.stream()
                .filter(s -> username.equals(s.getUsername()))
                .findFirst()
                .orElse(null);
        
            return Optional.ofNullable(student);
         } catch (Exception e) {
        System.err.println("Lỗi khi tìm kiếm học sinh theo username: " + e.getMessage());
        return Optional.empty();
        }
    }

     /**
     * Tìm học sinh theo ID lớp học
     */
    public List<Student> findStudentsByClassroomId(UUID classroomId) {
        return studentRepository.findByClassroomId(classroomId);
    }
    
    /**
     * Tìm học sinh theo tên lớp học
     */
    public List<Student> findStudentsByClassName(String className) {
        return studentRepository.findByClassName(className);
    }


public List<Student> findByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
        return List.of();
    }
    return studentRepository.findByEmailContaining(email.trim());
}
    
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Student> findById(UUID id) {
        return studentRepository.findById(id);
    }
    
    @Transactional(isolation = Isolation.SERIALIZABLE)
public Student save(Student student) {
    try {
        System.out.println("=== BẮT ĐẦU LƯU HỌC SINH ===");
        System.out.println("Thông tin học sinh: " + student.getName() + 
                         ", Mã: " + (student.getStudentCode() != null ? student.getStudentCode() : "Chưa có mã"));

        if (student.getVersion() == null) {
            student.setVersion(0L);
        }
        
        if (student.getId() == null) {
            student.setId(UUID.randomUUID());
            System.out.println("Đã tạo ID mới: " + student.getId());
        }
        
        Student savedStudent;
        
        if (student.getId() != null) {
            Optional<Student> existingStudentOpt = studentRepository.findById(student.getId());
            if (existingStudentOpt.isPresent()) {
                Student existingStudent = existingStudentOpt.get();
                
                Long currentVersion = existingStudent.getVersion();
                
                // Cập nhật các trường
                existingStudent.setName(student.getName());
                existingStudent.setAge(student.getAge());
                
                if (student.getStudentCode() != null) {
                    existingStudent.setStudentCode(student.getStudentCode());
                }
                
                if (student.getClassName() != null) {
                    existingStudent.setClassName(student.getClassName());
                }
                
                if (student.getPhoneNumber() != null) {
                    existingStudent.setPhoneNumber(student.getPhoneNumber());
                }
                
                if (student.getGender() != null) {
                    existingStudent.setGender(student.getGender());
                }
                
                if (student.getEmail() != null) {
                    existingStudent.setEmail(student.getEmail());
                }
                
                if (student.getAddress() != null) {
                    existingStudent.setAddress(student.getAddress());
                }
                
                if (student.getDateOfBirth() != null) {
                    existingStudent.setDateOfBirth(student.getDateOfBirth());
                }
                
                if (student.getScore() != null) {
                    existingStudent.setScore(student.getScore());
                }
                
                if (student.getAttendanceRecords() != null && !student.getAttendanceRecords().isEmpty()) {
                    existingStudent.setAttendanceRecords(student.getAttendanceRecords());
                }
                
                if (!currentVersion.equals(student.getVersion())) {
                    existingStudent.setVersion(currentVersion);
                }
                
                savedStudent = studentRepository.save(existingStudent);
                System.out.println("Cập nhật học sinh thành công: " + savedStudent.getName());
            } else {
                savedStudent = studentRepository.save(student);
                System.out.println("Lưu học sinh mới thành công: " + savedStudent.getName());
            }
        } else {
            savedStudent = studentRepository.save(student);
            System.out.println("Lưu học sinh mới thành công: " + savedStudent.getName());
        }
        
        // QUAN TRỌNG: Đảm bảo dữ liệu được ghi vào DB ngay lập tức
        studentRepository.flush();
        System.out.println("Đã flush dữ liệu học sinh vào database");
        
        // Kiểm tra xem học sinh có tồn tại trong DB không
        boolean exists = studentRepository.existsById(savedStudent.getId());
        System.out.println("Học sinh tồn tại trong DB: " + exists);
        
        return savedStudent;
    } catch (Exception e) {
        System.err.println("=== LỖI KHI LƯU HỌC SINH ===");
        e.printStackTrace();
        throw new RuntimeException("Lỗi khi lưu thông tin học sinh: " + e.getMessage(), e);
    }
}

@Transactional
public Student saveNew(Student student) {
    try {
        System.out.println("=== BẮT ĐẦU THÊM HỌC SINH MỚI ===");
        
        Student newStudent = new Student();
        
        // Thiết lập các thông tin học sinh
        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        newStudent.setStudentCode(student.getStudentCode());
        newStudent.setClassName(student.getClassName());
        newStudent.setPhoneNumber(student.getPhoneNumber());
        newStudent.setGender(student.getGender());
        newStudent.setEmail(student.getEmail());
        newStudent.setAddress(student.getAddress());
        newStudent.setDateOfBirth(student.getDateOfBirth());
        newStudent.setUsername(student.getUsername());
        newStudent.setPassword(student.getPassword());

        // Thiết lập các trường bắt buộc
        newStudent.setId(null); // Để Hibernate tự tạo
        newStudent.setVersion(0L); // Version khởi tạo
        
        Student savedStudent = studentRepository.save(newStudent);

        // QUAN TRỌNG: Flush để đảm bảo dữ liệu được lưu vào DB
        studentRepository.flush();
        
        System.out.println("Đã thêm học sinh mới thành công: " + savedStudent.getName() + 
                         " [ID: " + savedStudent.getId() + "]");
        
        return savedStudent;
    } catch (Exception e) {
        System.err.println("=== LỖI KHI THÊM HỌC SINH MỚI ===");
        e.printStackTrace();
        throw new RuntimeException("Lỗi khi thêm học sinh mới: " + e.getMessage(), e);
    }
}

@Transactional
public boolean delete(UUID id) {
    try {
        // Kiểm tra xem học sinh có tồn tại không
        if (!studentRepository.existsById(id)) {
            System.out.println(" Không tìm thấy học sinh với ID: " + id);
            return false;
        }
        
        // Lấy thông tin học sinh để log
        Optional<Student> studentOpt = studentRepository.findById(id);
        String studentName = studentOpt.map(Student::getName).orElse("Unknown");
        
        System.out.println(" Đang xóa học sinh: " + studentName + " (ID: " + id + ")");
        
        try {
            
            classroomStudentRepository.deleteAll(
                classroomStudentRepository.findAll().stream()
                    .filter(cs -> cs.getStudent().getId().equals(id))
                    .toList()
            );
        } catch (Exception e) {
            System.out.println(" Lỗi khi xóa quan hệ lớp học: " + e.getMessage());
        }
        
        // Xóa học sinh
        studentRepository.deleteById(id);
        
        try {
            studentRepository.flush();
        } catch (Exception e) {
            System.out.println(" Flush warning: " + e.getMessage());
        }
        
        boolean stillExists = studentRepository.existsById(id);
        
        if (!stillExists) {
            System.out.println(" Xóa học sinh '" + studentName + "' thành công!");
            return true;
        } else {
            System.out.println(" Lỗi: Học sinh vẫn tồn tại sau khi xóa");
            return false;
        }
        
    } catch (Exception e) {
        System.err.println(" Lỗi khi xóa học sinh: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
    // Các phương thức tìm kiếm mới
    @Transactional(readOnly = true)
    public Optional<Student> findByStudentCode(String studentCode) {
    if (studentCode == null || studentCode.trim().isEmpty()) {
        return Optional.empty();
    }
    
    try {
        List<Student> students = studentRepository.findAll();
        Student student = students.stream()
            .filter(s -> studentCode.equals(s.getStudentCode()))
            .findFirst()
            .orElse(null);
        
        return Optional.ofNullable(student);
    } catch (Exception e) {
        System.err.println("Lỗi khi tìm kiếm học sinh theo mã: " + e.getMessage());
        return Optional.empty();
    }
}
    
    @Transactional(readOnly = true)
    public List<Student> findByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

// Các phương thức tìm kiếm mới
@Transactional(readOnly = true)
public List<Student> findByEmailExact(String email) {
    if (email == null || email.trim().isEmpty()) {
        return List.of();
    }
    return studentRepository.findByEmail(email.trim());
}

@Transactional(readOnly = true)
public List<Student> findByGender(String gender) {
    if (gender == null || gender.trim().isEmpty()) {
        return List.of();
    }
    return studentRepository.findByGender(gender.trim());
}




@Transactional(readOnly = true)
public List<Student> findByClassName(String className) {
    return studentRepository.findByClassName(className);
}

@Transactional(readOnly = true)
public List<Student> searchStudents(String name, String className, String studentCode, String gender, String email) {
    return studentRepository.searchStudents(name, className, studentCode, gender, email);
}

// THÊM METHOD OVERLOAD CHO TƯƠNG THÍCH NGƯỢC
@Transactional(readOnly = true)
public List<Student> searchStudents(String name, String className, String studentCode) {
    
    return studentRepository.searchStudents(name, className, studentCode, null, null);
}
    @Transactional(readOnly = true)
    public List<Student> findStudentsInClassOrderByName(String className) {
        return studentRepository.findByClassNameOrderByNameAsc(className);
    }
    
    @Transactional(readOnly = true)
    public List<Student> findByNameAndClass(String name, String className) {
        return studentRepository.findByNameContainingIgnoreCaseAndClassName(name, className);
    }

    @Transactional
public boolean addStudentToClassroom(UUID studentId, UUID classroomId) {
    try {
        System.out.println("=== BẮT ĐẦU THÊM HỌC SINH VÀO LỚP ===");
        System.out.println("ID học sinh: " + studentId);
        System.out.println("ID lớp học: " + classroomId);
        
        // Kiểm tra xem liên kết đã tồn tại chưa
        ClassroomStudentId checkId = new ClassroomStudentId();
        checkId.setStudentId(studentId);
        checkId.setClassroomId(classroomId);
        
        if (classroomStudentRepository.existsById(checkId)) {
            System.out.println("Học sinh đã thuộc lớp học này!");
            return true; // Đã tồn tại liên kết này rồi nên vẫn trả về thành công
        }
        
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Classroom> classroomOpt = classroomRepository.findById(classroomId);
        
        if (studentOpt.isPresent() && classroomOpt.isPresent()) {
            Student student = studentOpt.get();
            Classroom classroom = classroomOpt.get();
            
            System.out.println("Tìm thấy học sinh: " + student.getName());
            System.out.println("Tìm thấy lớp: " + classroom.getClassName());
            
            // Tạo mối quan hệ
            ClassroomStudent cs = new ClassroomStudent();
            cs.setStudent(student);
            cs.setClassroom(classroom);
            
            ClassroomStudentId id = new ClassroomStudentId();
            id.setStudentId(studentId);
            id.setClassroomId(classroomId);
            cs.setId(id);
            
            // Lưu mối quan hệ
            classroomStudentRepository.save(cs);
            
            // QUAN TRỌNG: Cập nhật className trong Student 
            student.setClassName(classroom.getClassName());
            studentRepository.save(student);
            
            // Đảm bảo các thay đổi được ghi ngay lập tức
            classroomStudentRepository.flush();
            studentRepository.flush();
            
            System.out.println("Đã thêm học sinh " + student.getName() + 
                              " vào lớp " + classroom.getClassName() + " thành công!");
            System.out.println("Đã cập nhật className cho học sinh: " + classroom.getClassName());
            
            return true;
        } else {
            if (!studentOpt.isPresent()) {
                System.out.println("Không tìm thấy học sinh với ID: " + studentId);
            }
            if (!classroomOpt.isPresent()) {
                System.out.println("Không tìm thấy lớp học với ID: " + classroomId);
            }
            return false;
        }
    } catch (Exception e) {
        System.err.println("=== LỖI KHI THÊM HỌC SINH VÀO LỚP ===");
        e.printStackTrace();
        return false;
    }
}
    @Transactional
    public boolean removeStudentFromClassroom(UUID studentId, UUID classroomId) {
        try {
            ClassroomStudentId id = new ClassroomStudentId();
            id.setStudentId(studentId);
            id.setClassroomId(classroomId);
            
            if (classroomStudentRepository.existsById(id)) {
                classroomStudentRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Transactional(readOnly = true)
public List<Classroom> getClassroomsOfStudent(UUID studentId) {
    try {
        System.out.println("Lấy danh sách lớp học của học sinh có ID: " + studentId);
        
        // Sử dụng ClassroomStudentRepository trực tiếp thay vì phụ thuộc vào lazy loading
        List<ClassroomStudent> classroomStudents = classroomStudentRepository.findByStudentId(studentId);
        
        if (classroomStudents.isEmpty()) {
            System.out.println("Học sinh không thuộc lớp học nào");
            return List.of();
        }
        
        List<Classroom> classrooms = classroomStudents.stream()
            .map(ClassroomStudent::getClassroom)
            .toList();
        
        System.out.println("Số lượng lớp học: " + classrooms.size());
        for (Classroom c : classrooms) {
            System.out.println("- " + c.getClassName() + " (ID: " + c.getId() + ")");
        }
        
        return classrooms;
    } catch (Exception e) {
        System.err.println("Lỗi khi lấy danh sách lớp học của học sinh: " + e.getMessage());
        e.printStackTrace();
        return List.of();
    }
}
}
