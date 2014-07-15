package com.survey.mvc.dao;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class AnswerDaoTest {

    @Autowired
    public AnswerDao answerDao;

    @Test
    public void testInsertAnswer() throws Exception {
        //Assert.assertEquals(true, answerDao.insertAnswer(1,1,3,"123123"));
    }

}