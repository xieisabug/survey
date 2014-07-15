package com.survey.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.survey.mvc.dto.CustomerDto;

@Repository("customerDao")
public class CustomerDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	/**
	 * 分页查询客户信息
	 * 
	 * @param page
	 * @param pageSize
	 * @param area
	 * @param status
	 * @return
	 */
	public List<CustomerDto> select(int page, int pageSize, String area,
			String status,String customerType, int[] position,String city) {
		List<CustomerDto> list = null;

		if (!"".equals(area)) {
			area = " area = '" + area + "' and";
		}
		if (!"0".equals(city)) {
			city = " city = '" + city + "' and";
		}else {
			city = "";
		}
		if (!"".equals(status)) {
			status = " status = '" + status + "' and";
		}
		if (!"".equals(customerType)) {
			customerType = " customer_type = '" + customerType + "' and";
		}
		String sql = "SELECT TOP " + pageSize + " * FROM sur_customer WHERE "
				+ area + status + customerType+ city + " cid NOT IN(SELECT TOP " + (page - 1)
				* pageSize + " cid FROM sur_customer where " + area + status + customerType +city
				+ " cid>=" + position[0] + " and cid<=" + position[1]
				+ "  ORDER BY cid asc) and cid>=" + position[0] + " and cid<="
				+ position[1] + " ORDER BY cid asc ";
		// System.out.println(sql);
		try {

			list = jdbcTemplate.query(sql,
					new RowMapperResultSetExtractor<CustomerDto>(
							new CustomerMapper()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return list;
	}

	/**
	 * 查询客户总数
	 * 
	 * @param area
	 * @param status
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int count(String area, String status,String customerType, int position[],String city) {
		int count = 0;
		if (!"".equals(area)) {
			area = " area = '" + area + "' and";
		}
		if (!"0".equals(city)) {
			city = " city = '" + city + "' and";
		}else{
			city = "";
		}
		if (!"".equals(status)) {
			status = " status = '" + status + "' and";
		}
		if (!"".equals(customerType)) {
			customerType = " customer_type = '" + customerType + "' and";
		}
		String sql = "select count(*) from sur_customer where " + area + status + customerType + city
				+ " cid>=" + position[0] + " and cid<=" + position[1] + " ";
		count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	public int allCount(String area, String status, String startDate,
			String endDate,String customerType, String uid,String city,String answer) {
		if (!"0".equals(status)) {
			status = " and c.status='" + status + "' ";
		} else {
			status = "";
		}
		if (!"0".equals(answer)) {
			answer = " and a.answer='" + answer + "' ";
		} else {
			answer = "";
		}
		if (!"0".equals(city)) {
			city = " and c.city='" + city + "' ";
		} else {
			city = "";
		}
		if (!"0".equals(customerType)) {
			customerType = " and c.customer_type='" + customerType + "' ";
		} else {
			customerType = "";
		}
		if (!"0".equals(uid)) {
			uid = "and c.uid =" + uid;
		} else {
			uid = "";
		}
		if (!"".equals(area)) {
			area = " and c.area = '" + area + "' ";
		}
		if (!"".equals(startDate)) {
			startDate = " and c.survey_date > '" + startDate + " 00:00:00' ";
		}
		if (!"".equals(endDate)) {
			endDate = " and c.survey_date < '" + endDate + " 23:59:59' ";
		}
		String sql = "SELECT count(DISTINCT c.cid)  FROM sur_customer c left JOIN SUR_ANSWERS a ON c.cid = a.cid WHERE " + " 1=1 "
				+ area + startDate + endDate + status + customerType + uid + city +answer;
		return jdbcTemplate.queryForInt(sql);
	}

	/**
	 * 通过客户的id属性，来获取一个客户实例
	 * 
	 * @param cid
	 *            客户的id属性
	 * @return 客户实例，如果没有则返回null
	 */
	public CustomerDto getCustomerById(int cid) {
		CustomerDto customerDto;
		try {
			customerDto = jdbcTemplate.queryForObject(
					"select * from sur_customer where cid=?",
					new Object[] { cid }, new CustomerMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return customerDto;
	}

	/**
	 * 更新问卷有关信息
	 * 
	 * @param cid
	 *            客户id
	 * @param updatePerson
	 *            更新联系人
	 * @param updatePhone
	 *            更新联系电话
	 * @param status
	 *            更新状态
	 * @param reason
	 *            更新状态理由
	 * @return 是否更新成功
	 */
	public boolean questionaireUpdate(int cid, String updatePerson,
			String updatePhone, int status, String reason,String finalPhone, int uid) {
		return 1 == jdbcTemplate
				.update("update sur_customer set update_person=?,update_phone=?,status=?,reason=?,final_phone=?,survey_date=?,uid=? where cid=?",
						updatePerson, updatePhone, status, reason,finalPhone, new Date(),
						uid, cid);
	}

	/**
	 * 状态更新，用于回访未成功
	 * 
	 * @param cid
	 *            客户id
	 * @param status
	 *            更新状态
	 * @param reason
	 *            更新状态理由
	 * @return 更新是否成功
	 */
	public boolean statusUpdate(int cid, int status, String reason,int uid) {
		return 1 == jdbcTemplate.update(
				"update sur_customer set status=?,reason=?,survey_date=?,uid=? where cid=?",
				status, reason,new Date(),uid, cid);

	}

	/**
	 * 统计查询
	 * 
	 * @param area
	 * @param startDate
	 *            回访时间（开始）
	 * @param endDate
	 *            回访时间（截止）
	 * @return
	 */
	public List<CustomerDto> reportSelect(int page, int pageSize, String area,
			String startDate, String endDate, String status,String customerType, String uid,String city,String answer) {

		List<CustomerDto> list = null;
		if (!"0".equals(status)) {
			status = " and c.status='" + status + "' ";
		} else {
			status = "";
		}
		if (!"0".equals(answer)) {
			answer = " and a.answer='" + answer + "' ";
		} else {
			answer = "";
		}
		if (!"0".equals(city)) {
			city = " and c.city='" + city + "' ";
		} else {
			city = "";
		}
		if (!"0".equals(customerType)) {
			customerType = " and c.customer_type='" + customerType + "' ";
		} else {
			customerType = "";
		}
		if (!"0".equals(uid)) {
			uid = "and c.uid =" + uid;
		} else {
			uid = "";
		}
		if (!"".equals(area)) {
			area = " and c.area = '" + area + "' ";
		}
		if (!"".equals(startDate)) {
			startDate = " and c.survey_date > '" + startDate + " 00:00:00' ";
		}
		if (!"".equals(endDate)) {
			endDate = " and c.survey_date < '" + endDate + " 23:59:59' ";
		}
		String sql = "";
		if (page == -1) {
			sql = "SELECT  *  FROM sur_customer c WHERE " + " 1=1 " + area
					+ startDate + endDate + status + customerType + uid + city +"  ORDER BY c.cid asc";
		} else {
			sql = "SELECT DISTINCT TOP " + pageSize + "  c.* FROM sur_customer c left JOIN SUR_ANSWERS a ON c.cid = a.cid WHERE "
					+ " c.cid NOT IN(SELECT DISTINCT TOP " + (page - 1) * pageSize
					+ "  c.cid FROM sur_customer c left JOIN SUR_ANSWERS a ON c.cid = a.cid where  1=1 " + area + startDate
					+ endDate + status + customerType + uid + city+answer+ " ORDER BY c.cid asc) " + area
					+ startDate + endDate + status + customerType + uid+ city + answer
					+ "  ORDER BY c.cid asc ";
		}
		try {

			list = jdbcTemplate.query(sql,
					new RowMapperResultSetExtractor<CustomerDto>(
							new CustomerMapper()));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return list;
	}

	private class CustomerMapper implements RowMapper<CustomerDto> {

		@Override
		public CustomerDto mapRow(ResultSet resultSet, int i)
				throws SQLException {
			CustomerDto customerDto = new CustomerDto();
			customerDto.setCid(resultSet.getInt("cid"));
			customerDto.setAdress(resultSet.getString("adress"));
			customerDto.setArea(resultSet.getString("area"));
			customerDto.setCname(resultSet.getString("cname"));
			customerDto.setCustomerType(resultSet.getString("customer_type"));
			customerDto.setIrsId(resultSet.getInt("irs_id"));
			customerDto.setIrsName(resultSet.getString("irs_name"));
			customerDto.setPerson(resultSet.getString("person") == null ? ""
					: resultSet.getString("person"));
			customerDto.setPhone(resultSet.getString("phone"));
			customerDto.setPhone1(resultSet.getString("phone1"));
			customerDto.setPhone2(resultSet.getString("phone2"));
			customerDto.setReason(resultSet.getString("reason"));
			customerDto.setRevenueId(resultSet.getInt("revenue_id"));
			customerDto.setRevenueName(resultSet.getString("revenue_name"));
			customerDto.setStatus(resultSet.getString("status"));
			customerDto.setTaxCode(resultSet.getString("tax_code"));
			customerDto.setUpdatePerson(resultSet.getString("update_person"));
			customerDto.setUpdatePhone(resultSet.getString("update_phone"));
			customerDto.setFinalPhone(resultSet.getString("final_phone"));
			customerDto
					.setSurveyDate(resultSet.getString("survey_date") == null ? ""
							: resultSet.getString("survey_date").substring(0,
									19));
			return customerDto;
		}

	}

}
