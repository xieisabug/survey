package com.survey.mvc.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.survey.mvc.dto.UserDto;
import com.survey.mvc.service.PlaceService;
import com.survey.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    public UserService userService;
    @Autowired
	private PlaceService placeService;
    
    //public Map<String,UserDto> map = new HashMap<String, UserDto>();

    /**
     * 首页界面
     * @return 跳转到login.jsp
     */
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String welcome() {
		return "login";
	}

    /**
     * 登录逻辑，如果登录成功，则跳转到客户列表，不成功则返回登录页面
     * @param username 用户名
     * @param password 密码
     * @param model 页面model
     * @return 跳转的页面
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String login(String username, String password, ModelMap model,HttpSession session) {
    	
        UserDto userDto = userService.login(username, password);
        
        if (userDto != null) {
            model.addAttribute("user",userDto);
            if(session.getAttribute("placeMap")==null){
    			session.setAttribute("placeMap", placeService.getMap());
    		}
            return "customers";
        } else {
            return "login";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(ModelMap model) {
       return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public String home(UserDto userDto) {
        if (userDto == null) {
            return "login";
        } else {
            return "customers";
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/loginOut")
    public String loginOut(ModelMap model){
    	model.addAttribute("user", new UserDto());
    	return "login";
    }
}