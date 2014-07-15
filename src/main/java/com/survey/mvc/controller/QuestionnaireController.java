package com.survey.mvc.controller;

import java.io.UnsupportedEncodingException;

import com.survey.mvc.dto.UserDto;
import com.survey.mvc.service.AnswerService;
import com.survey.mvc.service.CustomerService;
import com.survey.mvc.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionnaireController {

	@Autowired
	public QuestionService questionService;
	@Autowired
	public CustomerService customerService;
	@Autowired
	public AnswerService answerService;

	/**
	 * 跳转到相应的问卷
	 * 
	 * @return 问卷页面
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/questionnaire/{cid}")
	public String questionnaireJump(@PathVariable int cid,
			@RequestParam String area, @RequestParam String status,@RequestParam String city,
			@RequestParam String curPage,@RequestParam String customerType, ModelMap model, HttpSession session) {
		model.addAttribute("questions", questionService.getAllQuestion());
		model.addAttribute("customer", customerService.getCustomerById(cid));
		try {
			area = new String(area.getBytes("iso8859-1"),"utf-8");
			city = new String(city.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		session.setAttribute("area", area);
		session.setAttribute("city", city);
		session.setAttribute("status", status);
		session.setAttribute("curPage", curPage);
		session.setAttribute("customerType", customerType);
		return "questionnaire";
	}

	/**
	 * 提交问卷，进行数据更新
	 * 
	 * @param cid
	 *            客户id
	 * @param qid
	 *            问题id列表，用|分隔
	 * @param answer
	 *            答案列表，用|分隔
	 * @param feedback
	 *            意见反馈列表，用|分隔
	 * @param status
	 *            提交状态
	 * @param reason
	 *            理由
	 * @param updatePerson
	 *            更新后的联系人
	 * @param updatePhone
	 *            更新后的电话
	 * @return 无
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/commit")
	@ResponseBody
	public String questionnaireCommit(@RequestParam int cid,
			@RequestParam String qid, @RequestParam String answer,
			@RequestParam String feedback, @RequestParam int status,
			@RequestParam String reason, @RequestParam String updatePerson,
			@RequestParam String updatePhone,@RequestParam String finalPhone, HttpSession session) {
		String[] answers = answer.split("\\|");
		String[] feedbacks = feedback.split("\\|");
		String[] qids = qid.split("\\|");
		UserDto user = (UserDto) session.getAttribute("user");
		int uid = user.getUid();
		if (status == 2) {
			answerService.questionnaireCommit(cid, qids, answers, feedbacks);
			customerService.questionaireUpdate(cid, updatePerson, updatePhone,
					status, reason,finalPhone, uid);
		}

		return "questionnaire";
	}

	/**
	 * 问卷回访失败提交，更新一些状态
	 * 
	 * @param cid
	 *            客户id
	 * @param qid
	 *            问题id列表，用|分隔
	 * @param answer
	 *            答案列表，用|分隔
	 * @param feedback
	 *            意见反馈列表，用|分隔
	 * @param status
	 *            提交状态
	 * @param reason
	 *            理由
	 * @param updatePerson
	 *            更新后的联系人
	 * @param updatePhone
	 *            更新后的电话
	 * @return 无
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/commitFail")
	@ResponseBody
	public String questionnaireCommitFail(@RequestParam int cid,
			@RequestParam String qid, @RequestParam String answer,
			@RequestParam String feedback, @RequestParam int status,
			@RequestParam String reason, @RequestParam String updatePerson,
			@RequestParam String updatePhone,HttpSession session) {
		UserDto user = (UserDto) session.getAttribute("user");
		int uid = user.getUid();
		if (status != 2) {
			customerService.statusUpdate(cid, status, reason,uid);
		}

		return "questionnaire";
	}

	/**
	 * 更新问卷时，获取问卷的答案信息
	 * 
	 * @param cid
	 *            客户id
	 * @param model
	 *            页面
	 * @return 跳转的页面
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/questionnaire/update/{cid}")
	public String questionnaireUpdate(@PathVariable int cid,
			@RequestParam String area, @RequestParam String status,@RequestParam String city,
			@RequestParam String curPage,@RequestParam String customerType, ModelMap model, HttpSession session) {
		try {
			area = new String(area.getBytes("iso8859-1"),"utf-8");
			city = new String(city.getBytes("iso8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("questionnaire", answerService.queryByCid(cid));
		model.addAttribute("questions", questionService.getAllQuestion());
		model.addAttribute("customer", customerService.getCustomerById(cid));
		session.setAttribute("area", area);
		session.setAttribute("city", city);
		session.setAttribute("status", status);
		session.setAttribute("curPage", curPage);
		session.setAttribute("customerType", customerType);
		return "questionnaire_update";
	}

	/**
	 * 提交问卷更改
	 * 
	 * @param cid
	 *            客户id
	 * @param qid
	 *            问题id列表，用|分隔
	 * @param answer
	 *            答案列表，用|分隔
	 * @param feedback
	 *            意见反馈列表，用|分隔
	 * @param status
	 *            提交状态
	 * @param reason
	 *            理由
	 * @param updatePerson
	 *            更新后的联系人
	 * @param updatePhone
	 *            更新后的电话
	 * @return 无
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/questionnaire/updateCommit")
	@ResponseBody
	public String questionnaireUpdateCommit(@RequestParam int cid,
			@RequestParam String qid, @RequestParam String answer,
			@RequestParam String feedback, @RequestParam int status,
			@RequestParam String reason, @RequestParam String updatePerson,
			@RequestParam String updatePhone,@RequestParam String finalPhone, HttpSession session) {
		String[] answers = answer.split("\\|");
		String[] feedbacks = feedback.split("\\|");
		String[] qids = qid.split("\\|");
		UserDto user = (UserDto) session.getAttribute("user");

		if (status == 2 && answerService.queryByCid(cid).size() != 0) {
			answerService.questionnaireUpdateCommit(cid, qids, answers,
					feedbacks);
			customerService.questionaireUpdate(cid, updatePerson, updatePhone,
					status, reason,finalPhone, user.getUid());
		} else if (status == 2) {
			answerService.questionnaireCommit(cid, qids, answers, feedbacks);
			customerService.questionaireUpdate(cid, updatePerson, updatePhone,
					status, reason,finalPhone, user.getUid());
		}

		return "questionnaire";
	}

}
