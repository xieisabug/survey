package com.survey.mvc.dao;

import com.survey.mvc.dto.AnswersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository("answerDao")
public class AnswerDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    /**
     * 插入一条答案
     * @param qid 问题的id
     * @param cid 用户id
     * @param answer 答案
     * @param feedback 意见反馈
     * @return 插入是否成功
     */
    public boolean insertAnswer(int qid, int cid, int answer, String feedback) {
        return  1 == jdbcTemplate.update("insert into sur_answers(qid,cid,answer,feedback) values(?,?,?,?)",
                qid,cid,answer,feedback);
    }
    
    
    /**
     * 查询某个客户的问卷情况
     * @param cid
     * @return
     */
    public List<AnswersDto> queryByCid(int cid){
    	List<AnswersDto> list;
    	list = jdbcTemplate.query("select a.*,b.title from sur_answers a INNER JOIN sur_question b on a.qid = b.qid where cid=? order by qid asc",
                new Object[]{cid},new AnswersDtoMapper());
    	return list;
    }

    public void updateAnswer(int qid, int cid, int answer, String feedback) {
        jdbcTemplate.update("update sur_answers set answer=?,feedback=? where cid=? and qid=?",answer, feedback, cid, qid);
    }


    private class AnswersDtoMapper implements RowMapper<AnswersDto> {

		@Override
		public AnswersDto mapRow(ResultSet resultSet, int i)
				throws SQLException {
			AnswersDto answersDto = new AnswersDto();
			answersDto.setAid(resultSet.getInt("aid"));
			answersDto.setAnswer(resultSet.getString("answer"));
			answersDto.setCid(resultSet.getInt("cid"));
			answersDto.setFeedback(resultSet.getString("feedback"));
			answersDto.setQid(resultSet.getInt("qid"));
			answersDto.setTitle(resultSet.getString("title"));
			return answersDto;
		}

	}
}
