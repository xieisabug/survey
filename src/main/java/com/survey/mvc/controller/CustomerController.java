package com.survey.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.survey.mvc.dto.UserDto;
import com.survey.mvc.service.CustomerService;

/**
 * 客户信息展示及条件查询
 * @author aisino_lzw
 *
 */
@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * 条件查询
	 * @param page 当前是第几页
	 * @param pageSize 每页显示多少条
	 * @param area 区域
	 * @param status 回访状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, value = "/customer")
	@ResponseBody
	public List getList(@RequestParam String page,
			@RequestParam String pageSize, @RequestParam String area,
			@RequestParam String status,@RequestParam String customerType,@RequestParam String city, HttpSession session) {
		int intPage = Integer.parseInt(page);
		int intPageSize = Integer.parseInt(pageSize);
		//查询客户信息
		UserDto user = (UserDto)session.getAttribute("user");
		int uid = user.getUid();
		//if(user.getType().equals("1")){
			uid = -1;
		//}
		List list = customerService.select(intPage,
				intPageSize, area, status,customerType,uid,city);
		//查询客户总数量
		int count = customerService.count(area, status,customerType,uid,city);
		int temp = count/intPageSize;
		if(count%intPageSize>0){
			temp = temp + 1;
		}
		//追加分页信息
		if(list.size()!=0){
			Map<String,String> map = new HashMap<String,String>();
			map.put("page", page);
			map.put("pageSize", pageSize);
			map.put("count",temp+"");
			
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/customers")
	public String getCustomersPage(){
		return "customers";
	}
}
