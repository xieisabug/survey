package com.survey.mvc.dao;

import com.survey.mvc.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("userDao")
public class UserDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	/**
	 * 登录逻辑，通过用户名和密码登录，登录成功返回实例，登录失败返回null
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 如果登录成功则返回实例，失败则返回null
	 */
	public UserDto login(String username, String password) {
		UserDto userDto;
		try {
			userDto = jdbcTemplate.queryForObject(
					"select * from [sur_user] where username=? and password=?",
					new Object[] { username, password }, new UserMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return userDto;
	}

	/**
	 * 用户映射类
	 */
	private class UserMapper implements RowMapper<UserDto> {

		@Override
		public UserDto mapRow(ResultSet resultSet, int i) throws SQLException {
			UserDto userDto = new UserDto();
			userDto.setUid(resultSet.getInt("uid"));
			userDto.setUsername(resultSet.getString("username"));
			userDto.setType(resultSet.getString("type"));
			return userDto;
		}
	}

	public List<UserDto> getUserList() {
		List<UserDto> list = null;
		String sql = "select * from sur_user order by uid asc";
		try {
			list = jdbcTemplate.query(sql,
					new RowMapperResultSetExtractor<UserDto>(new UserMapper()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return list;
	}

	public int changePwd(UserDto userDto) {
		int result = jdbcTemplate.update("update sur_user set password=? where uid=?",
				userDto.getPassword(), userDto.getUid());
		return result;
	}
}
