package note.dk.community.community.controller;

import note.dk.community.community.dto.PaginationDTO;
import note.dk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")//代表根目录 当请求访问根目录时 执行以下
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,//从前端接受的参数页码 不传就是0
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        PaginationDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";//此时会在前端摸板目录找 返回此页面
    }
}
