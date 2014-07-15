package com.survey.mvc.service;

import java.util.List;

import com.survey.mvc.dao.UserDao;
import com.survey.mvc.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 登录业务逻辑处理
     * @param username 用户名
     * @param password 密码
     * @return 实例或null
     */
    public UserDto login(String username, String password) {
        return userDao.login(username, password);
    }
    
    public List<UserDto> getList(){
    	return userDao.getUserList();
    }
    
    public int changePwd(UserDto userDto) {
    	return userDao.changePwd(userDto);
    }

}
