package note.dk.community.community.controller;

import note.dk.community.community.dto.PaginationDTO;
import note.dk.community.community.dto.QuestionDTO;
import note.dk.community.community.mapper.QuestionMapper;
import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.Question;
import note.dk.community.community.model.User;
import note.dk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")//代表根目录 当请求访问根目录时 执行以下
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,//从前端接受的参数页码 不传就是0
                        @RequestParam(name = "size",defaultValue = "5") Integer size){


        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {//即找到了数据库中存在的token 就将此token对应user放入session中
                        request.getSession().setAttribute("user", user);//request把user信息保存到session中
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";//此时会在摸板目录找 返回此页面
    }
}
