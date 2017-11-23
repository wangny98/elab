package com.device.business.service.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Verify_list {
	
	private String id;
	private String verify_no;  //申请单号
	private String verify_user; //申请后使用人
	private String verify_dept; //申请后使用单位
	private String verify_time; //申请时间
	private String verify_type; //申请类型：调拨申请、部门内部调拨申请、报废申请

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getVerify_no() {
		return verify_no;
	}


	public void setVerify_no(String verify_no) {
		this.verify_no = verify_no;
	}


	public String getVerify_user() {
		return verify_user;
	}


	public void setVerify_user(String verify_user) {
		this.verify_user = verify_user;
	}


	public String getVerify_dept() {
		return verify_dept;
	}


	public void setVerify_dept(String verify_dept) {
		this.verify_dept = verify_dept;
	}


	public String getVerify_time() {
		return verify_time;
	}


	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}
	

	public String getVerify_type() {
		return verify_type;
	}


	public void setVerify_type(String verify_type) {
		this.verify_type = verify_type;
	}


	/**
	 * 重写toString()方法
	 * 
	 * @return 返回对象的字符串表达
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
