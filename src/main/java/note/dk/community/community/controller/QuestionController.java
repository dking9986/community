package note.dk.community.community.controller;

import note.dk.community.community.dto.QuestionDTO;
import note.dk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO= questionService.getById(id);
        model.addAttribute("question",questionDTO);//question是键 前端可以访问这个键
        return "question";
    }

}
