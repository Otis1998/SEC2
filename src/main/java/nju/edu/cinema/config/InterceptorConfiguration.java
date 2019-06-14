package nju.edu.cinema.config;

import nju.edu.cinema.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login", "/index", "/signUp", "/register", "/signIn", "/error", "/user/movie", "/movie/search", "/user/movieDetail", "/schedule/search/audience", "/movie/{id}/{userId}", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.jpeg", "/font/**").addPathPatterns("/**");
    }
}
