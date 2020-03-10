package note.dk.community.community.controller;

import note.dk.community.community.dto.AccessTokenDTO;
import note.dk.community.community.dto.GithubUser;
import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import note.dk.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired//自动将已经写好Component注解的实例加载到当前的上下文
    private GithubProvider githubProvider;
    @Value("${github.client_id}")//会去配置文件中读取此key的值赋值给下面的变量
    private String clientid;
    @Value("${github.client_secret}")//会去配置文件中读取此key的值赋值给下面的变量
    private String clientsecret;
    @Value("${github.redirect_uri}")//会去配置文件中读取此key的值赋值给下面的变量
    private String redirecturi;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){//RequestParam用于完成两个参数的接受 HttpServletRequest用于将上下文中的request返回到此处供我们使用  response同前面
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser=githubProvider.getUser(accessToken);
        if (githubuser!=null){
            //登录成功 写cookies 和session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);//UUID 的目的，是让分布式系统中的所有元素，都能有唯一的辨识资讯，而不需要透过中央控制端来做辨识资讯的指定。如此一来，每个人都可以建立不与其它人冲突的 UUID。在这样的情况下，就不需考虑数据库建立时的名称重复问题。
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());//获取创建时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);//对数据库实物的存储代替了session的写入
            //response可以把token存到cookie里面
            response.addCookie(new Cookie("token",token));

            return "redirect:/";//意思是跳转回主页面 重定向；
        }
        else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
