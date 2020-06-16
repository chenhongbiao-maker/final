package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Admin;
import com.atguigu.springboot.service.Adminservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    Adminservice adminservice;

    @PostMapping(value ="/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        Map<String,Object> map, HttpSession session){
            Admin admin=adminservice.login(username,password,role);
            //!StringUtils.isEmpty(username) && "123456".equals(password)
            if(admin!=null){
                session.setAttribute("username",username);
                if(role.equals("管理员")){
                    return "redirect:/main.html";
                }else{
                    return "redirect:/empee.html";
                }


            }
            else {
                map.put("msg","用户名或密码错误");
                return "login";
            }

    }

    /**
     * 退出登陆
     */
    @RequestMapping("/login/quit")
    public String quit(HttpSession session){
        session.invalidate();
        return "login";
    }
}
