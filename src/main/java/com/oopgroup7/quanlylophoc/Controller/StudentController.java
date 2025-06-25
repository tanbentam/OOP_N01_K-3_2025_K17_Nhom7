package com.oopgroup7.quanlylophoc.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oopgroup7.quanlylophoc.Model.Classroom;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import com.oopgroup7.quanlylophoc.Service.ClassroomService;
import com.oopgroup7.quanlylophoc.Service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentRepository studentRepository;

    // ✅ Hiển thị danh sách học sinh
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "student/index";
    }

    // ✅ Tìm kiếm nâng cao
    @GetMapping("/search")
    public String searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className,
            @RequestParam(required = false) String studentCode,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String email,
            Model model) {

        List<Student> students;

        if ((name == null || name.isEmpty()) &&
            (className == null || className.isEmpty()) &&
            (studentCode == null || studentCode.isEmpty()) &&
            (gender == null || gender.isEmpty()) &&
            (email == null || email.isEmpty())) {
            students = studentService.findAll();
        } else if ((name == null || name.isEmpty()) &&
                   (className == null || className.isEmpty()) &&
                   (gender == null || gender.isEmpty()) &&
                   (email == null || email.isEmpty())) {
            Optional<Student> student = studentService.findByStudentCode(studentCode);
            students = student.map(List::of).orElse(List.of());
        } else if ((studentCode == null || studentCode.isEmpty()) &&
                   (className == null || className.isEmpty()) &&
                   (gender == null || gender.isEmpty()) &&
                   (email == null || email.isEmpty())) {
            students = studentService.findByName(name);
        } else if ((name == null || name.isEmpty()) &&
                   (studentCode == null || studentCode.isEmpty()) &&
                   (gender == null || gender.isEmpty()) &&
                   (email == null || email.isEmpty())) {
            students = studentService.findByClassName(className);
        } else if ((name == null || name.isEmpty()) &&
                   (className == null || className.isEmpty()) &&
                   (studentCode == null || studentCode.isEmpty()) &&
                   (gender == null || gender.isEmpty())) {
            students = studentService.findByEmail(email);
        } else if ((name == null || name.isEmpty()) &&
                   (className == null || className.isEmpty()) &&
                   (studentCode == null || studentCode.isEmpty()) &&
                   (email == null || email.isEmpty())) {
            students = studentService.findByGender(gender);
        } else {
            students = studentService.searchStudents(name, className, studentCode, gender, email);
        }

        model.addAttribute("students", students);
        model.addAttribute("name", name);
        model.addAttribute("className", className);
        model.addAttribute("studentCode", studentCode);
        model.addAttribute("gender", gender);
        model.addAttribute("email", email);
        return "student/index";
    }

    // ✅ Form thêm học sinh
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Student student = new Student();
        student.setDateOfBirth(LocalDate.now());
        student.setVersion(0L);
        model.addAttribute("student", student);
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("genderOptions", List.of("Nam", "Nữ", "Khác"));
        model.addAttribute("isAdd", true);
        return "student/form";
    }

    // ✅ Xử lý thêm học sinh
    @PostMapping("/add")
    public String addStudent(
            @ModelAttribute Student student,
            @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestParam(value = "classroomId", required = false) UUID classroomId,
            RedirectAttributes redirectAttributes) {
        try {
            if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên không được để trống!");
                return "redirect:/students/add";
            }

            if (studentService.findByStudentCode(student.getStudentCode()).isPresent()) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên đã tồn tại!");
                return "redirect:/students/add";
            }

            if (birthDate != null) student.setDateOfBirth(birthDate);

            student.setId(null);
            student.setVersion(null);
            Student savedStudent = studentRepository.save(student);

            if (classroomId != null) {
                boolean added = studentService.addStudentToClassroom(savedStudent.getId(), classroomId);
                redirectAttributes.addFlashAttribute("success",
                        added ? "Thêm học sinh và gán vào lớp thành công!" :
                                "Thêm học sinh thành công nhưng không thể gán vào lớp!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Thêm học sinh thành công!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm học sinh: " + e.getMessage());
            return "redirect:/students/add";
        }

        return "redirect:/students";
    }

    // ✅ Form sửa học sinh
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isEmpty()) return "redirect:/students";

        Student student = studentOpt.get();
        List<Classroom> classrooms = classroomService.findAll();
        List<Classroom> currentClassrooms = studentService.getClassroomsOfStudent(id);

        model.addAttribute("student", student);
        model.addAttribute("classrooms", classrooms);
        model.addAttribute("currentClassrooms", currentClassrooms);
        model.addAttribute("genderOptions", List.of("Nam", "Nữ", "Khác"));
        model.addAttribute("isAdd", false);
        return "student/form";
    }

    // ✅ Xử lý cập nhật học sinh
    @PostMapping("/edit")
    public String editStudent(
            @ModelAttribute Student student,
            @RequestParam(value = "birthDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate,
            @RequestParam(value = "classroomId", required = false) UUID classroomId,
            RedirectAttributes redirectAttributes) {
        try {
            if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên không được để trống!");
                return "redirect:/students/edit/" + student.getId();
            }

            Optional<Student> dbOpt = studentService.findById(student.getId());
            if (dbOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy học sinh!");
                return "redirect:/students";
            }

            Student dbStudent = dbOpt.get();

            Optional<Student> existing = studentService.findByStudentCode(student.getStudentCode());
            if (existing.isPresent() && !existing.get().getId().equals(student.getId())) {
                redirectAttributes.addFlashAttribute("error", "Mã sinh viên đã tồn tại!");
                return "redirect:/students/edit/" + student.getId();
            }

            dbStudent.setName(student.getName());
            dbStudent.setAge(student.getAge());
            dbStudent.setStudentCode(student.getStudentCode());
            dbStudent.setClassName(student.getClassName());
            dbStudent.setPhoneNumber(student.getPhoneNumber());
            dbStudent.setGender(student.getGender());
            dbStudent.setEmail(student.getEmail());
            dbStudent.setAddress(student.getAddress());
            if (birthDate != null) dbStudent.setDateOfBirth(birthDate);

            studentService.save(dbStudent);

            if (classroomId != null) {
                studentService.getClassroomsOfStudent(student.getId()).forEach(c ->
                        studentService.removeStudentFromClassroom(student.getId(), c.getId()));
                studentService.addStudentToClassroom(student.getId(), classroomId);
            }

            redirectAttributes.addFlashAttribute("success", "Cập nhật học sinh thành công!");

        } catch (OptimisticLockingFailureException e) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu bị thay đổi. Tải lại và thử lại.");
            return "redirect:/students/edit/" + student.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi cập nhật học sinh: " + e.getMessage());
        }

        return "redirect:/students";
    }

    // ✅ Xóa học sinh
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

    // ✅ Chi tiết học sinh
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

    // ✅ Tìm nhanh theo mã sinh viên
    @GetMapping("/search-by-code")
    public String searchStudentsByCode(@RequestParam(required = false) String studentCode, Model model) {
        if (studentCode != null && !studentCode.isEmpty()) {
            Optional<Student> student = studentService.findByStudentCode(studentCode);
            model.addAttribute("students", student.map(List::of).orElse(List.of()));
        } else {
            model.addAttribute("students", studentService.findAll());
        }
        return "student/index";
    }

    // ✅ API: lấy danh sách lớp học (AJAX)
    @GetMapping("/api/classrooms")
    @ResponseBody
    public List<Classroom> getClassrooms() {
        return classroomService.findAll();
    }

    // ✅ API: thêm học sinh vào lớp (AJAX)
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

    // ✅ API: xóa học sinh khỏi lớp (AJAX)
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
