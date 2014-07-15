package com.survey.mvc.interceptor;

import com.survey.mvc.dto.UserDto;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	request.setCharacterEncoding("utf-8");
    	response.setCharacterEncoding("utf-8");
        UserDto userDto = (UserDto) request.getSession().getAttribute("user");
        if (request.getRequestURI().contains("login") || request.getRequestURI().contains("css") || request.getRequestURI().contains("js")){
        }else {
            if (userDto == null || userDto.getUid()==0) {
                response.sendRedirect("/survey/login");
            }
        }
        return super.preHandle(request, response, handler);
    }
}
