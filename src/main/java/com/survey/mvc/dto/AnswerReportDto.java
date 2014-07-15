package com.survey.mvc.dto;

public class AnswerReportDto {

	private String title; //题目
	int level1 = 0; //非常满意
	int level2 = 0; //满意
	int level3 = 0; //基本满意
	int level4 = 0; //不满意
	int level5 = 0; //不了解
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLevel1() {
		return level1;
	}
	public void setLevel1(int level1) {
		this.level1 = level1;
	}
	public void setLevel1() {
		this.level1++;
	}
	public int getLevel2() {
		return level2;
	}
	public void setLevel2(int level2) {
		this.level2 = level2;
	}
	public void setLevel2() {
		this.level2++;
	}
	public int getLevel3() {
		return level3;
	}
	public void setLevel3(int level3) {
		this.level3 = level3;
	}
	public void setLevel3() {
		this.level3++;
	}
	public int getLevel4() {
		return level4;
	}
	public void setLevel4(int level4) {
		this.level4 = level4;
	}
	public void setLevel4() {
		this.level4++;
	}
	
	public int getLevel5() {
		return level5;
	}
	public void setLevel5(int level5) {
		this.level5 = level5;
	}
	public void setLevel5() {
		this.level5++;
	}
	
	
}
