package note.dk.community.community.controller;

import note.dk.community.community.dto.QuestionDTO;
import note.dk.community.community.mapper.QuestionMapper;
import note.dk.community.community.model.Question;
import note.dk.community.community.model.User;
import note.dk.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public  String edit(@PathVariable(name = "id") Integer id,
                        Model model){//访问的页面的id是几下面参数id就等于几
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());//前面是传的参数名字， 后面是值
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());//model写在上面这样出现错误时候就能继续保留已经写了的
        model.addAttribute("id",question.getId());
        return "publish";//修改还是回到这个页面 只要将之前填写的信息传参即可
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")//点击发布之后就会直接跳到这个地方 变成post请求 直接访问是上面两个
    public String doPulish(@RequestParam(value = "title",required = false) String title,
                           @RequestParam(value ="description",required = false) String description,
                           @RequestParam(value ="tag",required = false) String tag,   //与html页面中的 id 相同 从前端页面接受这三个参数
                           @RequestParam(value ="id",required = false) Integer id,
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
        User user=(User)request.getSession().getAttribute("user");//从session中取出user字段 赋值成user

        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";//如果发生错误则回到此publish页面
        }

        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());

        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";//没有错误回到主页
    }

}
