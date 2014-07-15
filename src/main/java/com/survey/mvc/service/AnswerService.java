package com.survey.mvc.service;

import com.survey.mvc.dao.AnswerDao;
import com.survey.mvc.dao.CustomerDao;
import com.survey.mvc.dto.AnswersDto;
import com.survey.mvc.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    public AnswerDao answerDao;

    public void questionnaireCommit(int cid, String[] qids, String[] answers, String[] feedbacks) {
        for (int i = 0; i < qids.length; i++) {
//            System.out.println(qids[i]);
//            System.out.println(answers[i]);
//            System.out.println(feedbacks[i]);
            answerDao.insertAnswer(Integer.parseInt(qids[i]), cid, Integer.parseInt(answers[i]), feedbacks[i]);
        }
    }

    public List<AnswersDto> queryByCid(int cid){
        return answerDao.queryByCid(cid);
    }

    public void questionnaireUpdateCommit(int cid, String[] qids, String[] answers, String[] feedbacks) {
        for (int i = 0; i < qids.length; i++) {
//            System.out.println(qids[i]);
//            System.out.println(answers[i]);
//            System.out.println(feedbacks[i]);
            answerDao.updateAnswer(Integer.parseInt(qids[i]), cid, Integer.parseInt(answers[i]), feedbacks[i]);
        }
    }
}
