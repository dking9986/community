package note.dk.community.community.interceptor;

import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import note.dk.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {//定义一个拦截器
    @Autowired
    private UserMapper userMapper;

    @Override     //对所有地址请求之前进行拦截 拦截之后执行以下代码 然后看return
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {//对所有地址请求之前进行拦截 拦截之后执行以下代码 然后看return
        Cookie[] cookies=request.getCookies();
        if(cookies!=null&&cookies.length>0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> user = userMapper.selectByExample(userExample);
                    if (user.size() != 0) {//即找到了数据库中存在的token 就将此token对应user放入session中
                        request.getSession().setAttribute("user", user.get(0));//request把user信息保存到session中
                        //return true;//为真的时候可以继续运行  假的时候不继续执行
                }
                    break;
                }
            }

        }
        return true;//这一步还没写好 以后再改
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
