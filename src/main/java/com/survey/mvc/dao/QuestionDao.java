package com.survey.mvc.dao;

import com.survey.mvc.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("questionDao")
public class QuestionDao {


    @Autowired
    public JdbcTemplate jdbcTemplate;

    /**
     * 获取到所有的问题
     * @return 所有问题的实例list
     */
    public List<QuestionDto> getAllQuestion(){
        List<QuestionDto> questionDtos;
        try {
            questionDtos = jdbcTemplate.query("select * from sur_question",new QuestionMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<QuestionDto>();
        }
        return questionDtos;
    }
    
    @SuppressWarnings("deprecation")
	public int getQuestionCount(){
    	int count = 0;
    	try {
    		count = jdbcTemplate.queryForInt(("select count(*) from sur_question"));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 删除所有问题
     */
    public void deleteAllQuestion() {
        jdbcTemplate.update("delete from sur_question");
    }

    /**
     * 插入一条问题
     * @param s 需要插入的问题
     */
    public void insertQuestion(int id,String s) {
        jdbcTemplate.update("insert into sur_question(qid,content) values(?,?)",id,s);
    }

    /**
     * 问题映射类
     */
    private class QuestionMapper implements RowMapper<QuestionDto>{

        @Override
        public QuestionDto mapRow(ResultSet resultSet, int i) throws SQLException {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setQid(resultSet.getInt("qid"));
            questionDto.setContent(resultSet.getString("content"));
            return questionDto;
        }
    }
}
