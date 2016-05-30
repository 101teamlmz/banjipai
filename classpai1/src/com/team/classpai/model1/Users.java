package com.team.classpai.model1;

import cn.bmob.v3.BmobUser;

public class Users extends BmobUser {

	private String major;   //专业
	private String class1;   //班级
	private String info;     //身份信息       
	
	
	
	private String sex;  		// 性别
	private String phone; 		// 电话
	private String qq; 			// QQ
	private String school; 		// 学校
	private String cademy; 		// 学院
	private String dorPart; 	// 校区
	private String dorNum; 		// 寝室号
	private String state; 		// 状态
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getClass1() {
		return class1;
	}
	public void setClass1(String class1) {
		this.class1 = class1;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getCademy() {
		return cademy;
	}
	public void setCademy(String cademy) {
		this.cademy = cademy;
	}
	public String getDorPart() {
		return dorPart;
	}
	public void setDorPart(String dorPart) {
		this.dorPart = dorPart;
	}
	public String getDorNum() {
		return dorNum;
	}
	public void setDorNum(String dorNum) {
		this.dorNum = dorNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
