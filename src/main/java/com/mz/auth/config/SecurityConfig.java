package com.mz.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法的细粒度控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;


    //配置类 定制请求的授权规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/goRegPage",//跳转注册页
                        "/main",//后台首页
                        "/teacher/regTeacher",//注册老师
                        "/file/uploadFile",//上传头像
                        "/front/index",//前台首页放行
                        "/front/login",//前台登录页
                        "/front/getCode",
                        "/front/gotoRegPage",//前台的注册页
                        "/paperindex/listpage",
                        "/stu/regStu",//前台注册保存功能
                        "/stu/logOut",//退出功能
                        "/stu/login",//登录方法
                        "/exam/popPaper/*",//弹出考试页面
                        "/stu/examPaper",//保存记录请求
                        "/stu/queryScorePage",//跳转学生查询成绩页面
                        "/stu/queryScoreData",//学生查询成绩
                        "/stu/queryAllScorePage",//查询学生所有科目成绩
                        "/stu/examDetailRecords",//学生考试的明细
                        "/favicon.ico",
                        "/static/**")//对静态资源的放行，背景图，css等资源
                .permitAll() //表示不需要认证，允许的请求，即允许上面 .antMatchers(。。。。。。。）里面的请求
                .anyRequest().authenticated()//除开放行的请求,其他请求就要经过认证
                .and().formLogin().and()//表示表单登录
                .csrf().disable() //关闭网络的攻击 xss
                .formLogin().loginPage("/login") //表示跳转到自定义的登录界面，被拦截下来跳转的请求
                .loginProcessingUrl("/form")//出来form表单登录请求

                .successHandler(myAuthenctiationSuccessHandler)
                .failureUrl("/fail").permitAll();//登录失败请求

        //退出登录
        http.logout().logoutSuccessUrl("/login").invalidateHttpSession(true);

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
                String header = req.getHeader("X-Requested-With");
                if("XMLHttpRequest".equals(header)){
                    resp.getWriter().println("{\"errorMsg\":\"不好意思你无权访问\"}");
                }else{
                    req.getRequestDispatcher("/error403").forward(req,resp);
                }
            }
        });

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //通过传入自定义 userDetailsService 添加身份验证
        /**
         * 配置内存的方式进行登录认证
         */
//         auth.inMemoryAuthentication()
//                        .passwordEncoder(new BCryptPasswordEncoder())
//                        .withUser("admin")
//                        .password(new BCryptPasswordEncoder().encode("123"))
//                        .roles("USER");
       auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}