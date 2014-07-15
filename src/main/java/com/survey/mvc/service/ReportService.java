package com.survey.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.mvc.dao.CustomerDao;
import com.survey.mvc.dao.QuestionDao;
import com.survey.mvc.dao.StatusReportDao;
import com.survey.mvc.dto.AnswerReportDto;
import com.survey.mvc.dto.AnswersDto;
import com.survey.mvc.dto.CustomerDto;
import com.survey.mvc.dto.StatusReportDto;

@Service
public class ReportService {
	
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private StatusReportDao statusReportDao;
	@Autowired
	private CustomerDao customerDao;

	/**
	 * 回访情况统计项
	 * @param customerList
	 * @return
	 */
	public Map<String,Object> reportForAnswers(List<CustomerDto> customerList){
		Map<String,Object> map = new HashMap<String,Object>();
		int count = customerList.size();
		
		int length = questionDao.getQuestionCount();
		AnswerReportDto [] answerReportDto = new AnswerReportDto[length+1];
		
		for (CustomerDto customerDto : customerList) {
			List<AnswersDto> list = customerDto.getAnswersList();
			if(list.size()==0){
				count--;
				continue;
			}
			int i = 0;
			for (AnswersDto answersDto : list) {
				if(answerReportDto[i]==null){
					answerReportDto[i] = new AnswerReportDto();
				}
				if(answerReportDto[i].getTitle()==null){
					answerReportDto[i].setTitle(answersDto.getTitle());
				}
				if("非常满意".equals(answersDto.getAnswer())){
					answerReportDto[i].setLevel1();
				}else if("满意".equals(answersDto.getAnswer())){
					answerReportDto[i].setLevel2();
				}else if("基本满意".equals(answersDto.getAnswer())){
					answerReportDto[i].setLevel3();
				}else if("不满意".equals(answersDto.getAnswer())){
					answerReportDto[i].setLevel4();
				}else if("不了解".equals(answersDto.getAnswer())){
					answerReportDto[i].setLevel5();
				}
				i++;
			}
			
		}
		if(answerReportDto[0]!=null){
			AnswerReportDto report = new AnswerReportDto();
			answerReportDto[length] = report;
			report.setTitle("<strong>小计</strong>");
			int level1 = 0;
			int level2 = 0;
			int level3 = 0;
			int level4 = 0;
			for (int i = 0; i < answerReportDto.length-1; i++) {
				level1 +=  answerReportDto[i].getLevel1();
				level2 +=  answerReportDto[i].getLevel2();
				level3 +=  answerReportDto[i].getLevel3();
				level4 +=  answerReportDto[i].getLevel4();
			}
			report.setLevel1(level1);
			report.setLevel2(level2);
			report.setLevel3(level3);
			report.setLevel4(level4);
		}
		
		map.put("customerCount", count);
		map.put("answerReportDto", answerReportDto);
		return map;
	}
	
	/**
	 * 回访状态统计项
	 * @param customerList
	 * @return
	 */
	public List<StatusReportDto> statusReport(String area, String startDate,
			String endDate, String status,String customerType, String uid,String city) {
		List<StatusReportDto> list = statusReportDao.statusReport(area, startDate, endDate, status,customerType, uid,city); 
		int status1 = 0;
		int status2 = 0;
		int status3 = 0;
		int status4 = 0;
		int sum = 0;
		for (StatusReportDto statusReportDto : list) {
			status1 += statusReportDto.getStatus1();
			status2 += statusReportDto.getStatus2();
			status3 += statusReportDto.getStatus3();
			status4 += statusReportDto.getStatus4();
			sum += statusReportDto.getSum();
		}
		StatusReportDto report = new StatusReportDto();
		report.setArea("<strong>小计</strong>");
		report.setStatus1(status1);
		report.setStatus2(status2);
		report.setStatus3(status3);
		report.setStatus4(status4);
		report.setSum(sum);
		list.add(report);
		return list;
	}
}
