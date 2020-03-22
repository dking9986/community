package note.dk.community.community.controller;

import note.dk.community.community.mapper.QuestionMapper;
import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.Question;
import note.dk.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPulish(@RequestParam("title") String title,
                           @RequestParam("description") String description,
                           @RequestParam("tag") String tag,   //与html页面中的 id 相同 从前端页面接受这三个参数
                           HttpServletRequest request,
                           Model model
                           ){
        model.addAttribute("title",title);//前面是传的参数名字， 后面是变量名字
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);//model写在上面这样出现错误时候就能继续保留已经写了的
        if (title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";//如果发生错误则回到此publish页面
        }
        if (description==null || description==""){
            model.addAttribute("error","描述不能为空");
            return "publish";//如果发生错误则回到此publish页面
        }
        if (tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";//如果发生错误则回到此publish页面
        }

        User user=null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);//request把user信息保存到session中
                    }
                    break;
                }
            }
        }
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";//如果发生错误则回到此publish页面
        }

        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtModified());
        questionMapper.create(question);
        return "redirect:/";//没有错误回到主页
    }

}
