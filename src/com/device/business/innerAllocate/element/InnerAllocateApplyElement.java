package com.device.business.innerAllocate.element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InnerAllocateApplyElement {

	@FormParam("apply.alloc_id")
	private String alloc_id;

	@FormParam("apply.fanumber")
	private String fanumber;

	@FormParam("apply.faname")
	private String faname;

	@FormParam("apply.assetName")
	private String assetName;

	@FormParam("apply.alloc_user")
	private String alloc_user;

	@FormParam("apply.alloc_holder")
	private String alloc_holder;

	@FormParam("apply.alloc_value")
	private String alloc_value;

	@FormParam("apply.alloc_type")
	private String alloc_type;

	@FormParam("apply.alloc_date")
	private Date alloc_date;

	@FormParam("apply.alloc_reason")
	private String alloc_reason;

	@FormParam("apply.alloc_memo")
	private String alloc_memo;

	@FormParam("apply.alloc_depart")
	private String alloc_depart;

	@FormParam("apply.apply_date")
	private Date apply_date;

	@FormParam("apply.status")
	private String status;

	@FormParam("apply.innerAllocRemark")
	private String innerAllocRemark;

	private String applyer;

	private Date verify_date;

	private String verifyer;

	public InnerAllocateApplyElement() {
		assetName = "";
		alloc_user = "";
		alloc_holder = "";
		alloc_value = "";
		alloc_type = "";
		// alloc_date = "";
		alloc_reason = "";
		alloc_memo = "";
		alloc_depart = "";
		// apply_date = "";
		status = "";
	}

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public Date getAlloc_date() {
		return alloc_date;
	}

	public void setAlloc_date(Date alloc_date) {
		this.alloc_date = alloc_date;
	}

	public Date getApply_date() {
		return apply_date;
	}

	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}

	public Date getVerify_date() {
		return verify_date;
	}

	public void setVerify_date(Date verify_date) {
		this.verify_date = verify_date;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	public String getVerifyer() {
		return verifyer;
	}

	public void setVerifyer(String verifyer) {
		this.verifyer = verifyer;
	}

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
	}

	public String getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(String alloc_id) {
		this.alloc_id = alloc_id;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAlloc_user() {
		return alloc_user;
	}

	public void setAlloc_user(String alloc_user) {
		this.alloc_user = alloc_user;
	}

	public String getAlloc_holder() {
		return alloc_holder;
	}

	public void setAlloc_holder(String alloc_holder) {
		this.alloc_holder = alloc_holder;
	}

	public String getAlloc_value() {
		return alloc_value;
	}

	public void setAlloc_value(String alloc_value) {
		this.alloc_value = alloc_value;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getAlloc_reason() {
		return alloc_reason;
	}

	public void setAlloc_reason(String alloc_reason) {
		this.alloc_reason = alloc_reason;
	}

	public String getAlloc_memo() {
		return alloc_memo;
	}

	public void setAlloc_memo(String alloc_memo) {
		this.alloc_memo = alloc_memo;
	}

	public String getAlloc_depart() {
		return alloc_depart;
	}

	public void setAlloc_depart(String alloc_depart) {
		this.alloc_depart = alloc_depart;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInnerAllocRemark() {
		return innerAllocRemark;
	}

	public void setInnerAllocRemark(String innerAllocRemark) {
		this.innerAllocRemark = innerAllocRemark;
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
