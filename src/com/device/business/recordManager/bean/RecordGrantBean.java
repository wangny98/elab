package com.device.business.recordManager.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordGrantBean {
	private String	id;
	private String	grant_no;
	private String	consume_user;
	private Date	consume_date;
	private String remark;
	private int delete_flag;
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrant_no() {
		return grant_no;
	}

	public void setGrant_no(String grant_no) {
		this.grant_no = grant_no;
	}

	public String getConsume_user() {
		return consume_user;
	}

	public void setConsume_user(String consume_user) {
		this.consume_user = consume_user;
	}

	public Date getConsume_date() {
		return consume_date;
	}

	public void setConsume_date(Date consume_date) {
		this.consume_date = consume_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	
}
