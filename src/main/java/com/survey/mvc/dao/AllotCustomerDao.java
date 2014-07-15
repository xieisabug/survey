package com.survey.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 分配客户
 * 
 * @author aisino_lzw
 * 
 */
@Repository("allotCustomerDao")
public class AllotCustomerDao {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	/**
	 * 查询客户总数量
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int allCusCount() {
		int count = 0;
		String sql = "select count(*) from sur_customer";
		count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	/**
	 * 查询用户总数量
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int allUserCount() {
		int count = 0;
		String sql = "select count(*) from sur_user where type = '0'";
		count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	/**
	 * 每个用户至少拥护客户量
	 * 
	 * @param cusCount
	 * @param userCount
	 * @return
	 */
	public int[] everyCount(int cusCount, int userCount) {
		int[] nums = new int[2];
		nums[0] = cusCount / userCount;
		nums[1] = cusCount % userCount;
		return nums;
	}

	/**
	 * 为每个用户分配客户
	 * 
	 * @param userId
	 * @return
	 */
	public int[] allot(int uid) {
		int cusCount = allCusCount();
		int userCount = allUserCount();
		int nums[] = everyCount(cusCount, userCount);
		String sql = "select count(*) from sur_user where type = '0' and uid <= "
				+ uid;
		int userIndex = jdbcTemplate.queryForInt(sql);
		int index[] = new int[2];
		if (nums[1] == 0) {
			index[0] = (userIndex - 1) * nums[0] + 1;
			index[1] = index[0] * userIndex;
		} else if (userIndex <= nums[1]) {
			index[0] = (userIndex - 1) * nums[0] + userIndex;
			index[1] = index[0] + nums[0];
		} else {
			index[0] = (userIndex - 1) * nums[0] + nums[1] + 1;
			index[1] = index[0] + nums[0]-1;
			if(index[0]>cusCount){
				index[0] = 0;
				index[1] = 0;
			}
			
		}
		return index;
	}

	/**
	 * 确定当前用户的客户起止cid
	 * @param uid
	 * @return
	 */
	public int[] getPosition(int uid) {
		int index[] = null;
		if(uid==-1){
			index = new int[]{1,allCusCount()};
		}else{
			index= allot(uid);
		}
		String start = "select max(cid) from SUR_CUSTOMER where cid in (select top "
				+ index[0] + " cid from SUR_CUSTOMER)";
		String end = "select max(cid) from SUR_CUSTOMER where cid in (select top "
				+ index[1] + " cid from SUR_CUSTOMER)";
		int startCid = jdbcTemplate.queryForInt(start);
		int endCid = jdbcTemplate.queryForInt(end);
		return new int[] { startCid, endCid };
	}
}
