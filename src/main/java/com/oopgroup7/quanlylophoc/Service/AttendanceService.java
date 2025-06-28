package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.AttendanceRecord;
import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.AttendanceRepository;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final AttendanceRepository repo;

    @Autowired
    private ClassroomRepository classroomRepository;
    
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    public List<String> getClassroomNames() {
        List<Classroom> classrooms = classroomRepository.findAll();
        List<String> names = new ArrayList<>();
        for (Classroom c : classrooms) {
            names.add(c.getClassName());
        }
        return names;
    }

   public List<Student> getPresentStudents(String className, LocalDate date) {
    // Sử dụng phương thức findByClassNameIgnoreCase thay vì findFirstByClassNameIgnoreCase
    List<Classroom> classrooms = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> present = new ArrayList<>();
    
    if (!classrooms.isEmpty()) {
        // Lấy lớp học đầu tiên từ danh sách
        Classroom classroom = classrooms.get(0);
        for (ClassroomStudent cs : classroom.getClassroomStudents()) {
            Student s = cs.getStudent();
            if (isStudentPresentOnDate(s.getId(), date)) {
                present.add(s);
            }
        }
    }
    return present;
}

public List<Student> getAbsentStudents(String className, LocalDate date) {
    // Sử dụng phương thức findByClassNameIgnoreCase thay vì findFirstByClassNameIgnoreCase
    List<Classroom> classrooms = classroomRepository.findByClassNameIgnoreCase(className);
    List<Student> absent = new ArrayList<>();
    
    if (!classrooms.isEmpty()) {
        // Lấy lớp học đầu tiên từ danh sách
        Classroom classroom = classrooms.get(0);
        for (ClassroomStudent cs : classroom.getClassroomStudents()) {
            Student s = cs.getStudent();
            if (!isStudentPresentOnDate(s.getId(), date)) {
                absent.add(s);
            }
        }
    }
    return absent;
}


public List<LocalDate> getAttendanceDatesByClassroom(UUID classroomId) {
    return repo.findDistinctDatesByClassroomId(classroomId);
}

public List<AttendanceRecord> getAttendanceByClassroomAndDate(UUID classroomId, LocalDate date) {
    return repo.findByClassroomIdAndDate(classroomId, date);
}

public List<LocalDate> findAttendanceDatesByClass(UUID classId) {
    return repo.findDistinctDatesByClassroomId(classId);
}

public List<AttendanceRecord> findByClassAndDate(UUID classId, LocalDate date) {
    return repo.findByClassroomIdAndDate(classId, date);
}

public Optional<AttendanceRecord> findById(UUID id) {
    return repo.findById(id);
}

@Transactional
public AttendanceRecord updateAttendance(UUID id, boolean present, String note) {
    AttendanceRecord attendance = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy điểm danh với ID: " + id));
    attendance.setPresent(present);
    attendance.setNote(note);
    return repo.save(attendance);
}

public boolean deleteById(UUID id) {
    try {
        repo.deleteById(id);
        return true;
    } catch (Exception e) {
        System.err.println("Error Deleting AttendanceRecord: " + e.getMessage());
        return false;
    }
}
@Transactional
public int deleteByClassAndDate(UUID classId, LocalDate date) {
    return repo.deleteByClassroomIdAndDate(classId, date);
}

    /**
     * Kiểm tra học sinh có mặt vào ngày cụ thể
     */
    public boolean isStudentPresentOnDate(UUID studentId, LocalDate date) {
        List<AttendanceRecord> records = findByStudentIdAndDate(studentId, date);
        return !records.isEmpty() && records.get(0).isPresent();
    }
    
    /**
     * Kiểm tra học sinh vắng có phép vào ngày cụ thể
     */
    public boolean hasStudentPermissionOnDate(UUID studentId, LocalDate date) {
        List<AttendanceRecord> records = findByStudentIdAndDate(studentId, date);
        return !records.isEmpty() && !records.get(0).isPresent() && records.get(0).hasPermission();
    }
    
    /**
     * Tìm bản ghi điểm danh theo ID học sinh và ngày
     */
    private List<AttendanceRecord> findByStudentIdAndDate(UUID studentId, LocalDate date) {
        return repo.findByStudentIdAndDate(studentId, date);
    }
    
    /**
     * Đánh dấu điểm danh cho một học sinh
     */
    public void markAttendance(UUID studentId, LocalDate date, boolean present, boolean permission) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            
            // Tìm bản ghi điểm danh hiện có (nếu có)
            List<AttendanceRecord> existingRecords = findByStudentIdAndDate(studentId, date);
            
            if (!existingRecords.isEmpty()) {
                // Cập nhật bản ghi hiện có
                AttendanceRecord record = existingRecords.get(0);
                record.setPresent(present);
                record.setPermission(permission);
                repo.save(record);
            } else {
                // Tạo bản ghi mới
                AttendanceRecord newRecord = new AttendanceRecord(student, date, present, permission);
                repo.save(newRecord);
            }
        }
    }


/**
 * Lấy tất cả bản ghi điểm danh của một học sinh
 */
public List<AttendanceRecord> getAttendanceByStudentId(UUID studentId) {
    return repo.findByStudentIdOrderByDateDesc(studentId);
}


    /**
     * Lấy tổng số buổi vắng của học sinh
     */
    public int getAbsentCount(UUID studentId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = repo.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        return (int) records.stream()
                .filter(record -> !record.isPresent())
                .count();
    }
    
    /**
     * Lấy tổng số buổi vắng có phép của học sinh
     */
    public int getPermissionCount(UUID studentId, LocalDate startDate, LocalDate endDate) {
        List<AttendanceRecord> records = repo.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        return (int) records.stream()
                .filter(record -> !record.isPresent() && record.hasPermission())
                .count();
    }

    // Phương thức cơ bản khác
    public List<AttendanceRecord> findAll() {
        return repo.findAll();
    }

    

    public boolean save(AttendanceRecord record) {
        try {
            repo.save(record);
            return true;
        } catch (Exception e) {
            System.err.println("Error Saving AttendanceRecord: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting AttendanceRecord: " + e.getMessage());
            return false;
        }
    }
}