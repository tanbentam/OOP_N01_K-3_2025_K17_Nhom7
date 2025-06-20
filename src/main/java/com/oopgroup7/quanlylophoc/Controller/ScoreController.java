package com.oopgroup7.quanlylophoc.Controller;

import com.oopgroup7.quanlylophoc.Model.Score;
import com.oopgroup7.quanlylophoc.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    // Hiển thị danh sách điểm
    @GetMapping
    public String listScores(Model model) {
        List<Score> scores = scoreService.findAll();
        model.addAttribute("scores", scores);
        return "score/index";
    }

    // Hiển thị form thêm điểm
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("score", new Score());
        return "score/form";
    }

    // Xử lý thêm điểm
    @PostMapping("/add")
    public String addScore(@ModelAttribute Score score) {
        scoreService.save(score);
        return "redirect:/scores";
    }

    // Hiển thị form sửa điểm
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Optional<Score> score = scoreService.findById(id);
        score.ifPresent(value -> model.addAttribute("score", value));
        return "score/form";
    }

    // Xử lý sửa điểm
    @PostMapping("/edit")
    public String editScore(@ModelAttribute Score score) {
        scoreService.save(score);
        return "redirect:/scores";
    }

    // Xóa điểm
    @GetMapping("/delete/{id}")
    public String deleteScore(@PathVariable UUID id) {
        scoreService.delete(id);
        return "redirect:/scores";
    }
}