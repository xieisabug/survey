package com.survey.mvc.controller;

import com.survey.mvc.dto.QuestionDto;
import com.survey.mvc.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    public QuestionService questionService;

    /**
     * 设置问题界面
     * @param modelMap 页面参数
     * @return 跳转页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/question/set")
    public String set(ModelMap modelMap) {
        List<QuestionDto> questionDtos = questionService.getAllQuestion();
        modelMap.addAttribute("questions",questionDtos);
        return "question_set";
    }

    /**
     * 提交更新的问题
     * @param questions 提交的问题
     * @return 无
     */
    @RequestMapping(method = RequestMethod.POST, value = "/question/commit")
    @ResponseBody
    public String commit(@RequestParam String questions) {
        questionService.updateQuestions(questions);
        return "question_commit";
    }
}