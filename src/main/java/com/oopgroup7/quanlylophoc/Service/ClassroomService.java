package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Teacher;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudent;
import com.oopgroup7.quanlylophoc.Model.ClassroomStudentId;
import com.oopgroup7.quanlylophoc.Repository.ClassroomRepository;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import com.oopgroup7.quanlylophoc.Repository.ClassroomStudentRepository;
import com.oopgroup7.quanlylophoc.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClassroomService {
    private final ClassroomRepository repo;
    
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public ClassroomService(ClassroomRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Classroom> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Classroom> findById(UUID id) {
        return repo.findById(id);
    }

    @Transactional
    public boolean save(Classroom classroom) {
    try {
        // Nếu teacher có id nhưng chưa có version, tìm teacher từ DB
        if (classroom.getTeacher() != null && classroom.getTeacher().getId() != null 
                && classroom.getTeacher().getVersion() == null) {
            Optional<Teacher> teacherOpt = teacherRepository.findById(classroom.getTeacher().getId());
            if (teacherOpt.isPresent()) {
                classroom.setTeacher(teacherOpt.get()); // Dùng teacher từ DB có version đã được khởi tạo
            } else {
                // Nếu không tìm thấy, set version = 0
                classroom.getTeacher().setVersion(0L);
            }
        }
        repo.save(classroom);
        return true;
    } catch (Exception e) {
        System.err.println("Error Saving Classroom: " + e.getMessage());
        return false;
    }
}

    @Transactional
    public boolean delete(UUID id) {
        try {
            repo.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error Deleting Classroom: " + e.getMessage());
            return false;
        }
    }
    
    // Thêm các method để xử lý quan hệ với Student
    @Transactional(readOnly = true)
    public List<Student> getStudentsInClassroom(UUID classroomId) {
        Optional<Classroom> classroomOpt = repo.findById(classroomId);
        if (classroomOpt.isPresent()) {
            return classroomOpt.get().getClassroomStudents()
                .stream()
                .map(ClassroomStudent::getStudent)
                .toList();
        }
        return List.of();
    }
    
    @Transactional
    public boolean addStudentToClassroom(UUID classroomId, UUID studentId) {
        try {
            Optional<Classroom> classroomOpt = repo.findById(classroomId);
            Optional<Student> studentOpt = studentRepository.findById(studentId);
            
            if (classroomOpt.isPresent() && studentOpt.isPresent()) {
                Classroom classroom = classroomOpt.get();
                Student student = studentOpt.get();
                
                ClassroomStudent cs = new ClassroomStudent();
                cs.setClassroom(classroom);
                cs.setStudent(student);
                
                ClassroomStudentId id = new ClassroomStudentId();
                id.setClassroomId(classroomId);
                id.setStudentId(studentId);
                cs.setId(id);
                
                classroomStudentRepository.save(cs);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error adding student to classroom: " + e.getMessage());
            return false;
        }
    }
    
    @Transactional
    public boolean removeStudentFromClassroom(UUID classroomId, UUID studentId) {
        try {
            ClassroomStudentId id = new ClassroomStudentId();
            id.setClassroomId(classroomId);
            id.setStudentId(studentId);
            
            if (classroomStudentRepository.existsById(id)) {
                classroomStudentRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error removing student from classroom: " + e.getMessage());
            return false;
        }
    }
    
    @Transactional(readOnly = true)
    public Optional<Classroom> findByClassName(String className) {
        return repo.findByClassNameIgnoreCase(className);
    }
}