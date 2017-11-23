package com.device.business.recordManager.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordControlListBean {
	
	private String id;
	private Date examine_date;
	private Date cx_date;
	private String gq_record;
	private String td_record;
	private String xz_record;
	private String cx_user;
	private String qkqs;
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

	public Date getExamine_date() {
		return examine_date;
	}

	public void setExamine_date(Date examine_date) {
		this.examine_date = examine_date;
	}

	public Date getCx_date() {
		return cx_date;
	}

	public void setCx_date(Date cx_date) {
		this.cx_date = cx_date;
	}

	public String getGq_record() {
		return gq_record;
	}

	public void setGq_record(String gq_record) {
		this.gq_record = gq_record;
	}

	public String getTd_record() {
		return td_record;
	}

	public void setTd_record(String td_record) {
		this.td_record = td_record;
	}

	public String getXz_record() {
		return xz_record;
	}

	public void setXz_record(String xz_record) {
		this.xz_record = xz_record;
	}

	public String getCx_user() {
		return cx_user;
	}

	public void setCx_user(String cx_user) {
		this.cx_user = cx_user;
	}

	public String getQkqs() {
		return qkqs;
	}

	public void setQkqs(String qkqs) {
		this.qkqs = qkqs;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}
	
	
}
