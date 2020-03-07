package note.dk.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name")String name, Model model){
        model.addAttribute("name",name);//给model赋值 model可看为一个map 有键和值
        return "hello";//此时会在摸板目录找hello
    }
}
