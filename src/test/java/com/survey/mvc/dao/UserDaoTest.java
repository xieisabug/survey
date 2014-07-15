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
public class UserDaoTest {

    @Autowired
    public UserDao userDao;

    @Test
    public void testLogin() {
        System.out.println(userDao.login("admin", "admin"));
    }

    @Test
    public void testWrongPasswordLogin() {
        Assert.assertEquals(null,userDao.login("admin","123"));
    }

    @Test
    public void testNoUserLogin() {
        Assert.assertEquals(null,userDao.login("123","admin"));
    }
}