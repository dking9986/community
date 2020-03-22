package note.dk.community.community.controller;

import note.dk.community.community.dto.PaginationDTO;
import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import note.dk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profire(HttpServletRequest request,
                          @PathVariable(name = "action") String action,//与上面相同 将前端页面路径里的action作为参数传进来
                          Model model,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,//从前端接受的参数页码 不传就是0
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        Cookie[] cookies=request.getCookies();
        User user=null;
        if(cookies!=null&&cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                     user = userMapper.findByToken(token);
                    if (user != null) {//即找到了数据库中存在的token 就将此token对应user放入session中
                        request.getSession().setAttribute("user", user);//request把user信息保存到session中
                    }
                    break;
                }
            }
        }
        if (user==null){
            return "redirect:/";
        }
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","回复");
        }
        PaginationDTO pagination=questionService.list(user.getAccountId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
