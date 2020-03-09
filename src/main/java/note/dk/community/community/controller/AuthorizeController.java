package note.dk.community.community.controller;

import note.dk.community.community.dto.AccessTokenDTO;
import note.dk.community.community.dto.GithubUser;
import note.dk.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state")String state){//request用于完成两个参数的接受
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
