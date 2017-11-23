package com.device.business.innerAllocate.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InnerAllocateBean {

	private Long id;
	private String faname;
	private String alloc_id;
	private String asset_id;
	private String alloc_reason;
	private String alloc_memo;
	private String alloc_depart;
	private String alloc_departText;
	private String alloc_user;
	private String alloc_holder;
	private Long alloc_value;
	private String alloc_type;
	private String alloc_typeText;
	private String alloc_date;
	private String applyer;
	private String apply_date;
	private String status;
	private String verifyer;
	private String verify_date;
	private String asdate;
	private String aedate;
	private String innerAllocRemark;

	public String getAsdate() {
		return asdate;
	}

	public void setAsdate(String asdate) {
		this.asdate = asdate;
	}

	public String getAedate() {
		return aedate;
	}

	public void setAedate(String aedate) {
		this.aedate = aedate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
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

	public Long getAlloc_value() {
		return alloc_value;
	}

	public void setAlloc_value(Long alloc_value) {
		this.alloc_value = alloc_value;
	}

	public String getAlloc_depart() {
		return alloc_depart;
	}

	public void setAlloc_depart(String alloc_depart) {
		this.alloc_depart = alloc_depart;
	}

	public String getAlloc_departText() {
		return alloc_departText;
	}

	public void setAlloc_departText(String alloc_departText) {
		this.alloc_departText = alloc_departText;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getAlloc_typeText() {
		return alloc_typeText;
	}

	public void setAlloc_typeText(String alloc_typeText) {
		this.alloc_typeText = alloc_typeText;
	}

	public String getAlloc_date() {
		return alloc_date;
	}

	public void setAlloc_date(String alloc_date) {
		this.alloc_date = alloc_date;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
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

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusText() {
		if (status != null) {
			if (status.equals("1")) {
				return "未申请";
			} else if (status.equals("2")) {
				return "撤销";
			} else if (status.equals("3")) {
				return "已申请未审核";
			} else if (status.equals("4")) {
				return "审核不通过";
			} else if (status.equals("5")) {
				return "审核通过";
			}
		}
		return null;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getVerify_date() {
		if (verify_date != null) {
			String[] tmpStrings = verify_date.split(" ");
			verify_date = tmpStrings[0];
		}
		return verify_date;
	}

	public void setVerify_date(String verify_date) {
		this.verify_date = verify_date;
	}

	public String getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(String alloc_id) {
		this.alloc_id = alloc_id;
	}

}
