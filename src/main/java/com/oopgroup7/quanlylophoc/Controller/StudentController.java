package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Service.StudentService;
import com.oopgroup7.quanlylophoc.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentRepository studentRepository;

    // Hiển thị danh sách học sinh
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student/index";
    }

    
    // Phương thức tìm kiếm chính - tìm kiếm nâng cao với nhiều tiêu chí
    @GetMapping("/search")
public String searchStudents(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String className,
        @RequestParam(required = false) String studentCode,
        @RequestParam(required = false) String gender,
        @RequestParam(required = false) String email,
        Model model) {

    List<Student> students;
    // Nếu không có tiêu chí tìm kiếm, hiển thị tất cả
    if ((name == null || name.isEmpty()) &&
        (className == null || className.isEmpty()) &&
        (studentCode == null || studentCode.isEmpty()) &&
        (gender == null || gender.isEmpty()) &&
        (email == null || email.isEmpty())) {
        students = studentService.findAll();
    }
    // Nếu chỉ tìm theo mã học sinh
    else if ((name == null || name.isEmpty()) &&
             (className == null || className.isEmpty()) &&
             (gender == null || gender.isEmpty()) &&
             (email == null || email.isEmpty())) {
        Optional<Student> student = studentService.findByStudentCode(studentCode);
        students = student.map(List::of).orElse(List.of());
    }
    // Nếu chỉ tìm theo tên
    else if ((studentCode == null || studentCode.isEmpty()) &&
             (className == null || className.isEmpty()) &&
             (gender == null || gender.isEmpty()) &&
             (email == null || email.isEmpty())) {
        students = studentService.findByName(name);
    }
    // Nếu chỉ tìm theo lớp
    else if ((name == null || name.isEmpty()) &&
             (studentCode == null || studentCode.isEmpty()) &&
             (gender == null || gender.isEmpty()) &&
             (email == null || email.isEmpty())) {
        students = studentService.findByClassName(className);
    }
    // Nếu chỉ tìm theo email
    else if ((name == null || name.isEmpty()) &&
             (className == null || className.isEmpty()) &&
             (studentCode == null || studentCode.isEmpty()) &&
             (gender == null || gender.isEmpty()) &&
             (email != null && !email.isEmpty())) {
        students = studentService.findByEmail(email);
    }
    // Nếu chỉ tìm theo giới tính
    else if ((name == null || name.isEmpty()) &&
             (className == null || className.isEmpty()) &&
             (studentCode == null || studentCode.isEmpty()) &&
             (email == null || email.isEmpty()) &&
             (gender != null && !gender.isEmpty())) {
        students = studentService.findByGender(gender);
    }
    // Tìm kiếm kết hợp nhiều tiêu chí
    else {
        students = studentService.searchStudents(name, className, studentCode, gender, email);
    }

    model.addAttribute("students", students);
    model.addAttribute("name", name);
    model.addAttribute("className", className);
    model.addAttribute("studentCode", studentCode);
    model.addAttribute("gender", gender);
    model.addAttribute("email", email);
    model.addAttribute("searchTerm", studentCode);

    return "student/index";
}
    // Hiển thị form thêm học sinh (có combobox chọn lớp)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Student student = new Student();
        student.setDateOfBirth(LocalDate.now());
        student.setVersion(0L);
        
        // Lấy danh sách lớp học cho combobox
        List<Classroom> classrooms = classroomService.findAll();
        
        model.addAttribute("student", student);
        model.addAttribute("classrooms", classrooms);
        model.addAttribute("isAdd", true);
        model.addAttribute("genderOptions", List.of("Nam", "Nữ", "Khác"));
        return "student/form";
    }

    // Phương thức addStudent được cải tiến với khả năng thêm vào lớp học
    @PostMapping("/add")
public String addStudent(
        @ModelAttribute Student student, 
        @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
        @RequestParam(value = "classroomId", required = false) UUID classroomId,
        RedirectAttributes redirectAttributes) {
    try {
        // Kiểm tra mã sinh viên
        if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Mã sinh viên không được để trống!");
            return "redirect:/students/add";
        }
        
        // Kiểm tra xem mã sinh viên đã tồn tại chưa
        if (studentService.findByStudentCode(student.getStudentCode()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Mã sinh viên đã tồn tại!");
            return "redirect:/students/add";
        }

        // Xử lý ngày sinh
        if (birthDate != null) {
            student.setDateOfBirth(birthDate);
        }

        student.setId(null);
        student.setVersion(null);

        Student savedStudent = studentRepository.save(student);
        
        if (savedStudent != null) {
            // Nếu có chọn lớp học, thêm học sinh vào lớp
            if (classroomId != null) {
                boolean addedToClass = studentService.addStudentToClassroom(savedStudent.getId(), classroomId);
                if (addedToClass) {
                    redirectAttributes.addFlashAttribute("success", "Thêm học sinh và gán vào lớp thành công!");
                } else {
                    redirectAttributes.addFlashAttribute("success", "Thêm học sinh thành công nhưng không thể gán vào lớp!");
                }
            } else {
                redirectAttributes.addFlashAttribute("success", "Thêm học sinh thành công!");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể lưu thông tin học sinh!");
        }
        return "redirect:/students";
        
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm học sinh: " + e.getMessage());
        return "redirect:/students/add";
    }
}
    // Hiển thị form sửa học sinh (có combobox chọn lớp)
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            
            // Lấy danh sách lớp học cho combobox
            List<Classroom> classrooms = classroomService.findAll();
            
            // Lấy danh sách lớp học hiện tại của học sinh
            List<Classroom> currentClassrooms = studentService.getClassroomsOfStudent(id);
            
            model.addAttribute("student", student);
            model.addAttribute("classrooms", classrooms);
            model.addAttribute("currentClassrooms", currentClassrooms);
            model.addAttribute("isAdd", false);
            model.addAttribute("genderOptions", List.of("Nam", "Nữ", "Khác"));
            return "student/form";
        } else {
            return "redirect:/students";
        }
    }

    // Xử lý sửa học sinh
    @PostMapping("/edit")
    public String editStudent(
            @ModelAttribute Student student, 
            @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestParam(value = "classroomId", required = false) UUID classroomId,
            RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra mã sinh viên
            if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên không được để trống!");
                return "redirect:/students/edit/" + student.getId();
            }
            
            // Kiểm tra xem có tồn tại học sinh với ID này không
            Optional<Student> dbStudentOpt = studentService.findById(student.getId());
            if (!dbStudentOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy học sinh để cập nhật!");
                return "redirect:/students";
            }
            
            Student dbStudent = dbStudentOpt.get();
            
            // Kiểm tra xem mã có bị trùng không (loại trừ chính bản ghi này)
            Optional<Student> existingStudent = studentService.findByStudentCode(student.getStudentCode());
            if (existingStudent.isPresent() && !existingStudent.get().getId().equals(student.getId())) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên đã tồn tại!");
                return "redirect:/students/edit/" + student.getId();
            }
            
            // Sử dụng đối tượng từ DB để cập nhật
            dbStudent.setName(student.getName());
            dbStudent.setAge(student.getAge());
            dbStudent.setStudentCode(student.getStudentCode());
            dbStudent.setClassName(student.getClassName());
            dbStudent.setPhoneNumber(student.getPhoneNumber());
            dbStudent.setGender(student.getGender());
            dbStudent.setEmail(student.getEmail());
            dbStudent.setAddress(student.getAddress());
            
            if (birthDate != null) {
                dbStudent.setDateOfBirth(birthDate);
            }
            
            // Lưu đối tượng đã được cập nhật từ DB
            studentService.save(dbStudent);
            
            // Xử lý thay đổi lớp học nếu có
            if (classroomId != null) {
                // Xóa khỏi tất cả lớp cũ và thêm vào lớp mới
                List<Classroom> currentClassrooms = studentService.getClassroomsOfStudent(student.getId());
                for (Classroom classroom : currentClassrooms) {
                    studentService.removeStudentFromClassroom(student.getId(), classroom.getId());
                }
                
                // Thêm vào lớp mới
                studentService.addStudentToClassroom(student.getId(), classroomId);
            }
            
            redirectAttributes.addFlashAttribute("success", "Cập nhật học sinh thành công!");
            
        } catch (OptimisticLockingFailureException e) {
            redirectAttributes.addFlashAttribute("error", 
                "Dữ liệu đã bị cập nhật bởi người dùng khác. Vui lòng tải lại trang và thử lại.");
            return "redirect:/students/edit/" + student.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật học sinh: " + e.getMessage());
        }
        return "redirect:/students";
    }

    // Xóa học sinh
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            studentService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa học sinh thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa học sinh: " + e.getMessage());
        }
        return "redirect:/students";
    }
    
    // Phương thức tìm kiếm nhanh chỉ theo mã sinh viên
    @GetMapping("/search-by-code")
    public String searchStudentsByCode(@RequestParam(required = false) String studentCode, Model model) {
        if (studentCode != null && !studentCode.isEmpty()) {
            Optional<Student> student = studentService.findByStudentCode(studentCode);
            List<Student> students = student.map(List::of).orElse(List.of());
            model.addAttribute("students", students);
            model.addAttribute("searchTerm", studentCode);
        } else {
            model.addAttribute("students", studentService.findAll());
        }
        return "student/index";
    }
    
    // Xem chi tiết học sinh
    @GetMapping("/details/{id}")
    public String showStudentDetails(@PathVariable UUID id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            List<Classroom> classrooms = studentService.getClassroomsOfStudent(id);
            
            model.addAttribute("student", student);
            model.addAttribute("classrooms", classrooms);
            return "student/details";
        } else {
            return "redirect:/students";
        }
    }

    // API endpoint để lấy danh sách lớp học cho combobox (AJAX)
    @GetMapping("/api/classrooms")
    @ResponseBody
    public List<Classroom> getClassrooms() {
        return classroomService.findAll();
    }

    // Thêm học sinh vào lớp học (AJAX endpoint)
    @PostMapping("/api/{studentId}/classrooms/{classroomId}")
    @ResponseBody
    public String addStudentToClassroom(@PathVariable UUID studentId, @PathVariable UUID classroomId) {
        try {
            boolean success = studentService.addStudentToClassroom(studentId, classroomId);
            return success ? "success" : "failed";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    // Xóa học sinh khỏi lớp học (AJAX endpoint)
    @DeleteMapping("/api/{studentId}/classrooms/{classroomId}")
    @ResponseBody
    public String removeStudentFromClassroom(@PathVariable UUID studentId, @PathVariable UUID classroomId) {
        try {
            boolean success = studentService.removeStudentFromClassroom(studentId, classroomId);
            return success ? "success" : "failed";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
}