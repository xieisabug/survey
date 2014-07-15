package com.survey.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.survey.mvc.dto.StatusReportDto;

@Repository("statusReportDao")
public class StatusReportDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	public List<StatusReportDto> statusReport(String area, String startDate,
			String endDate, String status,String customerType, String uid,String city) {

		List<StatusReportDto> list = null;
		if (!"0".equals(status)) {
			status = " and status='" + status + "' ";
		} else {
			status = "";
		}
		if (!"0".equals(city)) {
			city = " and city='" + city + "' ";
		} else {
			city = "";
		}
		if (!"0".equals(customerType)) {
			customerType = " and customer_type='" + customerType + "' ";
		} else {
			customerType = "";
		}
		if (!"0".equals(uid)) {
			uid = "and uid =" + uid;
		} else {
			uid = "";
		}
		if (!"".equals(area)) {
			area = " and area = '" + area + "' ";
		}
		if (!"".equals(startDate)) {
			startDate = " survey_date > '" + startDate + " 00:00:00' ";
		}
		
		if (!"".equals(endDate)) {
			endDate = " survey_date < '" + endDate + " 23:59:59' ";
		}
		String left = "";
		String right = "";
		String center = "";
		if(!("".equals(startDate)&&"".equals(endDate))){
			left=" and ((";
			right = ") or survey_date is NULL) ";
		}
		if((!"".equals(startDate))&&(!"".equals(endDate))){
			center = " and ";
		}
		String sql = "select c.area area,sum(CASE c.status WHEN '1' then 1 ELSE 0 END) status2,sum (CASE c.status WHEN '2' then 1 ELSE 0 END) status1,sum (CASE c.status WHEN '3' then 1 ELSE 0 END) " 
				+ "status3,sum (CASE c.status WHEN '4' then 1 ELSE 0 END) status4,COUNT(*) sum from SUR_CUSTOMER c " +
						"where 1=1 "+area + left + startDate +center + endDate + right+ status + customerType + uid+ city +"  GROUP BY c.area";
		//System.out.println(sql);
		try {

			list = jdbcTemplate.query(sql,
					new RowMapperResultSetExtractor<StatusReportDto>(
							new StatusReportMapper()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return list;
	}
	
	private class StatusReportMapper implements RowMapper<StatusReportDto> {

		@Override
		public StatusReportDto mapRow(ResultSet resultSet, int i)
				throws SQLException {
			StatusReportDto statusReportDto = new StatusReportDto();
			statusReportDto.setArea(resultSet.getString("area"));
			statusReportDto.setStatus1(resultSet.getInt("status1"));
			statusReportDto.setStatus2(resultSet.getInt("status2"));
			statusReportDto.setStatus3(resultSet.getInt("status3"));
			statusReportDto.setStatus4(resultSet.getInt("status4"));
			statusReportDto.setSum(resultSet.getInt("sum"));
			return statusReportDto;
		}

	}
}
