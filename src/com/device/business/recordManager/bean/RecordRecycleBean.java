package com.device.business.recordManager.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordRecycleBean {
	private String	id;
	private String	recycle_user;
	private Date	recycle_date;
	private String	remark;
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

	public String getRecycle_user() {
		return recycle_user;
	}

	public void setRecycle_user(String recycle_user) {
		this.recycle_user = recycle_user;
	}

	public Date getRecycle_date() {
		return recycle_date;
	}

	public void setRecycle_date(Date recycle_date) {
		this.recycle_date = recycle_date;
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
