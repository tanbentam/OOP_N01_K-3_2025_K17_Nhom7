package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Score;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import com.oopgroup7.quanlylophoc.Service.ScoreService;
import com.oopgroup7.quanlylophoc.Service.StudentService;

import java.util.UUID;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;
    // Hiển thị danh sách
    @GetMapping
public String index(
        @RequestParam(name = "page", defaultValue = "1") int page,
        Model model) {
    // Lấy tất cả điểm
    List<Score> scores = scoreService.findAll();
    
    model.addAttribute("scores", scores);
    
    // Thêm thống kê 
    long excellent = 0, good = 0, average = 0, poor = 0;
    for (Score score : scores) {
        if (score.getValue() >= 8.5) {
            excellent++;
        } else if (score.getValue() >= 7.0) {
            good++;
        } else if (score.getValue() >= 5.0) {
            average++;
        } else {
            poor++;
        }
    }
    
    model.addAttribute("excellentCount", excellent);
    model.addAttribute("goodCount", good);
    model.addAttribute("averageCount", average);
    model.addAttribute("poorCount", poor);
    
    return "score/index";
}


    //Hiển thị điểm cho học sinh
    @GetMapping("/student")
    public String viewStudentScores(HttpSession session, Model model) {
    // Lấy ID của học sinh từ session
    String studentIdStr = (String) session.getAttribute("currentUserId");
    
    // Nếu không có studentId trong session, chuyển hướng đến trang đăng nhập
    if (studentIdStr == null) {
        return "redirect:/login";
    }
    UUID studentId = UUID.fromString(studentIdStr);
    // Lấy danh sách điểm của học sinh
    List<Score> scores = scoreService.findByStudentId(studentId);
    model.addAttribute("scores", scores);
    
    // Thêm thống kê 
    long excellent = 0, good = 0, average = 0, poor = 0;
    for (Score score : scores) {
        if (score.getValue() >= 8.5) {
            excellent++;
        } else if (score.getValue() >= 7.0) {
            good++;
        } else if (score.getValue() >= 5.0) {
            average++;
        } else {
            poor++;
        }
    }
    
    model.addAttribute("excellentCount", excellent);
    model.addAttribute("goodCount", good);
    model.addAttribute("averageCount", average);
    model.addAttribute("poorCount", poor);
    
    return "score/index-student";
}
    
    // Hiển thị form thêm điểm
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("score", new Score());
        return "score/form";
    }
    
    
    // Hiển thị form sửa điểm riêng
@GetMapping("/edit/{id}")
public String showEditScoreForm(@PathVariable("id") String id, Model model) {
    try {
        UUID scoreId = UUID.fromString(id);
        Score score = scoreService.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy điểm"));
                
        model.addAttribute("score", score);
        return "score/edit"; // Trang HTML riêng cho sửa điểm
    } catch (Exception e) {
        return "redirect:/scores";
    }
}

// Xử lý yêu cầu sửa điểm
@PostMapping("/edit")
public String processEditScore(
        @RequestParam("scoreId") String scoreId,
        @RequestParam("value") Double value,
        @RequestParam(value = "subject", required = false) String subject,
        @RequestParam(value = "notes", required = false) String notes,
        RedirectAttributes redirectAttributes) {
    
    try {
        // Sửa điểm
        UUID id = UUID.fromString(scoreId);
        scoreService.editScore(id, value, subject, notes);
        
        redirectAttributes.addFlashAttribute("success", "Sửa điểm thành công!");
        return "redirect:/scores";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", 
            "Lỗi khi sửa điểm: " + e.getMessage());
        return "redirect:/scores/edit/" + scoreId;
    }
}
    // Xử lý lưu điểm mới
    @PostMapping("/save")
public String saveScore(
        @RequestParam("studentCode") String studentCode,  
        @RequestParam("subject") String subject,
        @RequestParam("value") double value,
        @RequestParam(value = "notes", required = false) String notes,
        RedirectAttributes redirectAttributes) {
    
    try {
        // Tìm học sinh theo mã
        Optional<Student> studentOpt = studentRepository.findByStudentCode(studentCode);
        
        if (studentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", 
                "Không tìm thấy học sinh với mã " + studentCode + ". Vui lòng kiểm tra lại.");
            return "redirect:/scores/form";
        }
        
        // Tạo và lưu điểm mới
        Score score = new Score();
        score.setStudent(studentOpt.get());
        score.setSubject(subject);
        score.setValue(value);
        score.setNotes(notes);
        
        scoreService.save(score);
        
        redirectAttributes.addFlashAttribute("success", "Thêm điểm thành công!");
        return "redirect:/scores";
        
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm điểm: " + e.getMessage());
        return "redirect:/scores/form";
    }
}
    // Xử lý cập nhật điểm

    // Thêm endpoint để hiển thị danh sách học sinh theo môn học
@GetMapping("/by-subject")
public String getStudentsBySubject(
        @RequestParam(name = "subject", required = false) String subject,
        Model model) {
    
    // Lấy tất cả các môn học 
    List<String> allSubjects = scoreService.findAllDistinctSubjects();
    model.addAttribute("allSubjects", allSubjects);
    
    if (subject != null && !subject.isEmpty()) {
       
        List<Score> subjectScores = scoreService.findAllBySubject(subject);
        model.addAttribute("subjectScores", subjectScores);
        model.addAttribute("selectedSubject", subject);
        
        long excellent = 0, good = 0, average = 0, poor = 0;
        double totalValue = 0;
        
        for (Score score : subjectScores) {
            double value = score.getValue();
            totalValue += value;
            
            if (value >= 8.5) excellent++;
            else if (value >= 7.0) good++;
            else if (value >= 5.0) average++;
            else poor++;
        }
        
        // Tính điểm trung bình của môn học
        double averageScore = subjectScores.isEmpty() ? 0 : totalValue / subjectScores.size();
        model.addAttribute("averageScore", Math.round(averageScore * 100.0) / 100.0);
        model.addAttribute("excellentCount", excellent);
        model.addAttribute("goodCount", good);
        model.addAttribute("averageCount", average);
        model.addAttribute("poorCount", poor);
        model.addAttribute("totalStudents", subjectScores.size());
    }
    
    return "score/list";
}
    
    // Tìm kiếm điểm theo tên học sinh hoặc lớp
@GetMapping("/search")
public String searchScores(
        @RequestParam(name = "studentName", required = false) String studentName,
        @RequestParam(name = "className", required = false) String className,
        Model model) {
    
    List<Score> searchResults = new ArrayList<>();
    
    if ((studentName == null || studentName.isEmpty()) && 
        (className == null || className.isEmpty())) {
        
        searchResults = scoreService.findAll();
    } else {
        
        if (studentName != null && !studentName.isEmpty()) {
            searchResults = scoreService.findAll().stream()
                .filter(score -> score.getStudent().getName() != null && 
                        score.getStudent().getName().toLowerCase()
                        .contains(studentName.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        }
        
        
        if (className != null && !className.isEmpty()) {
            
            if (!searchResults.isEmpty()) {
                searchResults = searchResults.stream()
                    .filter(score -> score.getStudent().getClassName() != null && 
                            score.getStudent().getClassName().contains(className))
                    .collect(java.util.stream.Collectors.toList());
            } else {
                
                searchResults = scoreService.findAll().stream()
                    .filter(score -> score.getStudent().getClassName() != null && 
                            score.getStudent().getClassName().contains(className))
                    .collect(java.util.stream.Collectors.toList());
            }
        }
    }
    
    model.addAttribute("scores", searchResults);
    model.addAttribute("studentName", studentName);
    model.addAttribute("className", className);
    
    // Thống kê
    long excellent = 0, good = 0, average = 0, poor = 0;
    for (Score score : searchResults) {
        if (score.getValue() >= 8.5) excellent++;
        else if (score.getValue() >= 7.0) good++;
        else if (score.getValue() >= 5.0) average++;
        else poor++;
    }
    
    model.addAttribute("excellentCount", excellent);
    model.addAttribute("goodCount", good);
    model.addAttribute("averageCount", average);
    model.addAttribute("poorCount", poor);
    
    return "score/index";
}
    // Xóa điểm
    @GetMapping("/delete/{id}")
    public String deleteScore(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            scoreService.delete(java.util.UUID.fromString(id));
            redirectAttributes.addFlashAttribute("success", "Xóa điểm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa điểm: " + e.getMessage());
        }
        
        return "redirect:/scores";
    }
    
    // API tìm học sinh theo mã
    @GetMapping("/api/student")
    @ResponseBody
    public ResponseEntity<?> findStudentByCode(@RequestParam("studentCode") String studentCode) {
        try {
            Student student = studentService.findByStudentCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với mã: " + studentCode));
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
