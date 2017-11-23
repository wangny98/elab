package com.device.business.recordManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordRecycleElement {
	private String	id;
	
	@FormParam("recycle_user")
	private String	recycle_user;
	
	@FormParam("recycle_date")
	private Date	recycle_date;
	
	@FormParam("remark")
	private String	remark;

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

}
