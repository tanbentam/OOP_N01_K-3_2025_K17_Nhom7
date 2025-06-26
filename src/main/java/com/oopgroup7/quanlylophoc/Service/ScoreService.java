package com.oopgroup7.quanlylophoc.Service;

import com.oopgroup7.quanlylophoc.Model.Score;
import com.oopgroup7.quanlylophoc.Model.Student;
import com.oopgroup7.quanlylophoc.Repository.ScoreRepository;
import com.oopgroup7.quanlylophoc.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScoreService {
    
    @Autowired
    private ScoreRepository scoreRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Lấy tất cả điểm
    @Transactional(readOnly = true)
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }
    
    // Lấy điểm với phân trang
    @Transactional(readOnly = true)
    public Page<Score> findAll(Pageable pageable) {
        return scoreRepository.findAll(pageable);
    }
    
    // Tìm điểm theo ID
    @Transactional(readOnly = true)
    public Optional<Score> findById(UUID id) {
        return scoreRepository.findById(id);
    }
    
    // Lưu hoặc cập nhật điểm
    @Transactional
    public Score save(Score score) {
        return scoreRepository.save(score);
    }
    
    // Xóa điểm
    @Transactional
    public void delete(UUID id) {
        scoreRepository.deleteById(id);
    }
    
    public List<String> findAllDistinctSubjects() {
        return scoreRepository.findDistinctSubjects();
    }
    
    //  lấy điểm theo môn học
    public List<Score> findAllBySubject(String subject) {
        return scoreRepository.findAllBySubjectOrderByValueDesc(subject);
    }
    // Tìm điểm theo mã học sinh
    @Transactional(readOnly = true)
    public List<Score> findByStudentCode(String studentCode) {
        Optional<Student> studentOpt = studentRepository.findByStudentCode(studentCode);
        return studentOpt.map(scoreRepository::findByStudent).orElse(List.of());
    }
    
    
    // Tìm điểm theo tên học sinh
    @Transactional(readOnly = true)
    public List<Score> findByStudentName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.of();
        }
        return scoreRepository.findByStudentNameContaining(name.trim());
    }
    
    // Tìm điểm theo môn học
    @Transactional(readOnly = true)
    public List<Score> findBySubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            return List.of();
        }
        return scoreRepository.findBySubjectContaining(subject.trim());
    }
    
    // Tìm kiếm kết hợp
    @Transactional(readOnly = true)
    public Page<Score> search(String studentName, String subject, Pageable pageable) {
        if ((studentName == null || studentName.trim().isEmpty()) && 
            (subject == null || subject.trim().isEmpty())) {
            return findAll(pageable);
        } else if (subject == null || subject.trim().isEmpty()) {
            return scoreRepository.findByStudentNameContaining(studentName.trim(), pageable);
        } else if (studentName == null || studentName.trim().isEmpty()) {
            return scoreRepository.findBySubjectContaining(subject.trim(), pageable);
        }
        
        return scoreRepository.findAll(pageable);
    }
    
    // Tìm Student bằng studentCode
    @Transactional(readOnly = true)
    public Optional<Student> findStudentByCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }
    
    // Tính toán thống kê
    @Transactional(readOnly = true)
    public Map<String, Long> getStatistics() {
        Map<String, Long> stats = new HashMap<>();
        List<Score> allScores = scoreRepository.findAll();
        
        long excellentCount = allScores.stream().filter(s -> s.getValue() >= 8.5).count();
        long goodCount = allScores.stream().filter(s -> s.getValue() >= 7.0 && s.getValue() < 8.5).count();
        long averageCount = allScores.stream().filter(s -> s.getValue() >= 5.0 && s.getValue() < 7.0).count();
        long poorCount = allScores.stream().filter(s -> s.getValue() < 5.0).count();
        
        stats.put("excellentCount", excellentCount);
        stats.put("goodCount", goodCount);
        stats.put("averageCount", averageCount);
        stats.put("poorCount", poorCount);
        
        return stats;
    }

    @Transactional(readOnly = true)
    public List<Score> findByStudentId(UUID studentId) {
        return scoreRepository.findByStudentId(studentId);
}

}