package note.dk.community.community.interceptor;

import org.h2.engine.Session;
import org.h2.engine.SessionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {//添加拦截器功能
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");//.excludePathPatterns("/","/callback");
    }
}
