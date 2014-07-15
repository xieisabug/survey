package com.survey.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.survey.mvc.dto.UserDto;
import com.survey.mvc.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/manage")
	public String enter(ModelMap modelMap){
		List<UserDto> list = userService.getList();
		modelMap.addAttribute("userList",list);
		return "password";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/changePwd")
	@ResponseBody
	public String changePwd(@RequestParam String password, @RequestParam String uid){
		String msg = "fail";
		UserDto userDto = new UserDto();
		userDto.setPassword(password);
		userDto.setUid(Integer.valueOf(uid));
		if(userService.changePwd(userDto)>0){
			msg = "success";
		}
		return msg;
	}
}
