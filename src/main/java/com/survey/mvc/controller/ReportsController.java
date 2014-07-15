package com.survey.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.survey.mvc.dto.CustomerDto;
import com.survey.mvc.dto.PlaceDto;
import com.survey.mvc.dto.StatusReportDto;
import com.survey.mvc.dto.UserDto;
import com.survey.mvc.service.CustomerService;
import com.survey.mvc.service.PlaceService;
import com.survey.mvc.service.ReportService;
import com.survey.mvc.service.UserService;

/**
 * 信息统计
 * 
 * @author aisino_lzw
 * 
 */
@Controller
public class ReportsController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private UserService userService;
	@Autowired
	private PlaceService placeService;

	@RequestMapping(method = RequestMethod.GET, value = "/reportsPage")
	public String reports(ModelMap modelMap) {
		List<UserDto> list = userService.getList();
		modelMap.addAttribute("userList", list);
		return "reports";
	}

	/**
	 * 调查问卷查询
	 * 
	 * @param area
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reports")
	@ResponseBody
	public List<CustomerDto> getList(@RequestParam String page,
			@RequestParam String pageSize, @RequestParam String area,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String status,@RequestParam String customerType, 
			@RequestParam String city,@RequestParam String uid,@RequestParam String answer) {
		
		int intPage = Integer.parseInt(page);
		int intPageSize = Integer.parseInt(pageSize);
		List list = customerService.getReportData(
				intPage, intPageSize, area,
				startDate, endDate, status,customerType, uid,city,answer);
		//查询客户总数量
		int count = customerService.allCount(area, status,startDate,endDate,customerType,uid,city,answer);
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
			map.put("allCount",count+"");
			list.add(map);
		}
		return list;
	}

	/**
	 * 回访情况统计项
	 * 
	 * @param area
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reports/answer")
	@ResponseBody
	public Map<String, Object> getAnswerReport(@RequestParam String area,
			@RequestParam String startDate, @RequestParam String endDate,
			@RequestParam String city,
			@RequestParam String status,@RequestParam String customerType, @RequestParam String uid) {
		List<CustomerDto> customerList = customerService.getReportData(-1,-1,area,
				startDate, endDate, status,customerType, uid,city,"0");
		return reportService.reportForAnswers(customerList);
	}

	/**
	 * 回访状态统计项
	 * 
	 * @param area
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reports/status")
	@ResponseBody
	public Map<String, Object> getStatusReport(@RequestParam String area,
			@RequestParam String startDate, @RequestParam String endDate,@RequestParam String city,
			@RequestParam String status,@RequestParam String customerType, @RequestParam String uid) {
		int count = customerService.allCount(area, status, startDate, endDate,customerType, uid,city,"0");
		List<StatusReportDto> list = reportService.statusReport(area, startDate, endDate, status,customerType, uid,city);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerCount", count);
		map.put("list", list);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/reports/area")
	@ResponseBody
	public List<PlaceDto> getArea(@RequestParam String pid){
		return placeService.getPlaceById(Integer.valueOf(pid));
	}
	
}
