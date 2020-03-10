package note.dk.community.community.controller;

import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/")//代表根目录
    public String index(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                String token=cookie.getValue();
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);//request把user信息保存到session中
                }
                break;
            }
        }
        return "index";//此时会在摸板目录找
    }
}
