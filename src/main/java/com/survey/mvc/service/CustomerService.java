package com.survey.mvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.survey.mvc.dao.AllotCustomerDao;
import com.survey.mvc.dao.AnswerDao;
import com.survey.mvc.dao.CustomerDao;
import com.survey.mvc.dto.AnswersDto;
import com.survey.mvc.dto.CustomerDto;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private AnswerDao answerDao;
	@Autowired
	private AllotCustomerDao allotCustomerDao;

	public List<CustomerDto> select(int page, int pageSize, String area,
			String status,String customerType,int uid,String city) {
		int position[] = allotCustomerDao.getPosition(uid);
		return customerDao.select(page, pageSize, area, status,customerType,position,city);
	}

	public int count(String area, String status,String customerType,int uid,String city) {
		int position[] = allotCustomerDao.getPosition(uid);
		return customerDao.count(area, status,customerType,position,city);
	}

    public CustomerDto getCustomerById(int cid) {
        return customerDao.getCustomerById(cid);
    }

    /**
     * 更新问卷有关信息
     * @param cid 客户id
     * @param updatePerson 更新联系人
     * @param updatePhone 更新联系电话
     * @param status 更新状态
     * @param reason 更新状态理由
     */
    public void questionaireUpdate(int cid, String updatePerson, String updatePhone, int status, String reason,String finalPhone,int uid) {
        customerDao.questionaireUpdate(cid,updatePerson,updatePhone,status,reason,finalPhone,uid);
    }

    /**
     * 状态更新，用于回访未成功
     * @param cid 客户id
     * @param status 更新状态
     * @param reason 更新状态理由
     */
    public void statusUpdate(int cid, int status, String reason,int uid) {
        customerDao.statusUpdate(cid, status,reason,uid);
    }
    
    
    /**
     * 构建客户问卷结果信息
     * @param area
     * @param startDate
     * @param endDate
     * @return
     */
    public List<CustomerDto> getReportData(int page,int pageSize,String area,String startDate,String endDate,String status,String customerType,String uid,String city,String answer){
    	List<CustomerDto> customerList = customerDao.reportSelect(page,pageSize,area, startDate, endDate,status,customerType,uid,city,answer);
        for (CustomerDto customerDto : customerList) {
            List<AnswersDto> answersList = answerDao.queryByCid(customerDto.getCid());
            customerDto.setAnswersList(answersList);
        }
    	return customerList;
    }
    
    public int allCount(String area,String status,String startDate,String endDate,String customerType,String uid,String city,String answer){
    	return customerDao.allCount(area, status, startDate, endDate,customerType, uid,city,answer);
    }
}
