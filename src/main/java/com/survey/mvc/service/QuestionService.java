package com.survey.mvc.service;

import com.survey.mvc.dao.QuestionDao;
import com.survey.mvc.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    /**
     * 获取到所有的问题
     * @return 实例list
     */
    public List<QuestionDto> getAllQuestion(){
        return questionDao.getAllQuestion();
    }

    /**
     * 更新数据库的所有问题
     * @param questions 插入的问题列表
     */
    public void updateQuestions(String questions) {
        String[] questionArray = questions.split("\\|");
        questionDao.deleteAllQuestion();
        for (int i = 0; i<questionArray.length; i++) {
            questionDao.insertQuestion(i+1, questionArray[i]);
        }
    }
}
