package com.atguigu.springboot.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession(false);
        if(session==null){
            response.sendRedirect(request.getContextPath()+"/index.html");

            return false;
        }
        Object username=request.getSession(true).getAttribute("username");

        if(username == null){
            request.setAttribute("msg","没有权限请先登陆");
            //request.getRequestDispatcher("index").forward(request,response);
            response.sendRedirect(request.getContextPath()+"/index.html");

            return false;
        }

        else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
