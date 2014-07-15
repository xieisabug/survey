package com.survey.mvc.dto;

import java.util.List;

/**
 * 客户信息对象
 * @author aisino_lzw
 *
 */
public class CustomerDto {

	private Integer cid;
	private String cname;
	private String taxCode;
	private String area;
	private String person;
	private String phone;
	private String updatePerson;
	private String updatePhone;
	private String status;
	private String reason;
	private String adress;
	private Integer irsId;
	private String irsName;
	private Integer revenueId;
	private String revenueName;
	private String customerType;
	private String surveyDate;
	private String strStatus;
	private String phone1;
	private String phone2;
	private String finalPhone;
	
	private List<AnswersDto> answersList;
	
	public List<AnswersDto> getAnswersList() {
		return answersList;
	}
	public void setAnswersList(List<AnswersDto> answersList) {
		this.answersList = answersList;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUpdatePerson() {
		return updatePerson;
	}
	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}
	public String getUpdatePhone() {
		return updatePhone;
	}
	public void setUpdatePhone(String updatePhone) {
		this.updatePhone = updatePhone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		int intStatus = Integer.valueOf(status);
		switch (intStatus) {
		case 1:
			this.strStatus = "未回访";
			break;
		case 2:
			this.strStatus = "回访成功";
			break;
		case 3:
			this.strStatus = "无效回访";
			break;
		case 4:
			this.strStatus = "需再次回访";
			break;
		}
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Integer getIrsId() {
		return irsId;
	}
	public void setIrsId(Integer irsId) {
		this.irsId = irsId;
	}
	public String getIrsName() {
		return irsName;
	}
	public void setIrsName(String irsName) {
		this.irsName = irsName;
	}
	public Integer getRevenueId() {
		return revenueId;
	}
	public void setRevenueId(Integer revenueId) {
		this.revenueId = revenueId;
	}
	public String getRevenueName() {
		return revenueName;
	}
	public void setRevenueName(String revenueName) {
		this.revenueName = revenueName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		if(customerType == null){
			this.customerType = "";
			return;
		}
		int type = Integer.valueOf(customerType);
		switch (type) {
		case 1:
			this.customerType = "一般纳税人";
			break;
		case 2:
			this.customerType = "小规模";
			break;
		case 3:
			this.customerType = "个体户";
			break;
		default :
			this.customerType = "";
			break;
		}
	}
	public String getSurveyDate() {
		return surveyDate;
	}
	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}
	public String getStrStatus() {
		return strStatus;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFinalPhone() {
		return finalPhone;
	}
	public void setFinalPhone(String finalPhone) {
		this.finalPhone = finalPhone;
	}
	
	
}
