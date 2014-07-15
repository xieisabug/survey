package com.survey.mvc.dto;

public class StatusReportDto {

	private int status1; //回访成功
	private int status2; //未回访
	private int status3; //无效回访
	private int status4; //需再次回访
	private String area;
	private int sum;
	
	public int getStatus1() {
		return status1;
	}
	public void setStatus1(int status1) {
		this.status1 = status1;
	}
	public int getStatus2() {
		return status2;
	}
	public void setStatus2(int status2) {
		this.status2 = status2;
	}
	public int getStatus3() {
		return status3;
	}
	public void setStatus3(int status3) {
		this.status3 = status3;
	}
	public int getStatus4() {
		return status4;
	}
	public void setStatus4(int status4) {
		this.status4 = status4;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	
	
}
