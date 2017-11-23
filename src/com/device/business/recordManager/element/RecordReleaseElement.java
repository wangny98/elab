package com.device.business.recordManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.device.util.DateFormat;

public class RecordReleaseElement {
	private String id;
	
	@FormParam("record_type")
	private String record_type;
	
	@FormParam("record_name")
	private String record_name;
	
	@FormParam("record_code")
	private String record_code;
	
	@FormParam("record_use_scope")
	private String record_use_scope;
	
	@FormParam("record_use_date")
	@DateFormat("yyyy-MM-dd")
	private Date record_use_date;
	
	@FormParam("record_release_scope")
	private String record_release_scope;
	
	@FormParam("record_examine_date")
	@DateFormat("yyyy-MM-dd")
	private Date record_examine_date;
	
	@FormParam("examine_user")
	private String examine_user;
	
	@FormParam("record_release_date")
	@DateFormat("yyyy-MM-dd")
	private Date record_release_date;
	
	@FormParam("release_user")
	private String release_user;
	
	@FormParam("record_implement_date")
	@DateFormat("yyyy-MM-dd")
	private Date record_implement_date;
	
	@FormParam("remark")
	private String remark;
	
	@FormParam("establish_user")
	private String establish_user;
	
	@FormParam("status")
	private int status;
	
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

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public String getRecord_name() {
		return record_name;
	}

	public void setRecord_name(String record_name) {
		this.record_name = record_name;
	}

	public String getRecord_code() {
		return record_code;
	}

	public void setRecord_code(String record_code) {
		this.record_code = record_code;
	}

	public String getRecord_use_scope() {
		return record_use_scope;
	}

	public void setRecord_use_scope(String record_use_scope) {
		this.record_use_scope = record_use_scope;
	}

	public Date getRecord_use_date() {
		return record_use_date;
	}

	public void setRecord_use_date(Date record_use_date) {
		this.record_use_date = record_use_date;
	}

	public String getRecord_release_scope() {
		return record_release_scope;
	}

	public void setRecord_release_scope(String record_release_scope) {
		this.record_release_scope = record_release_scope;
	}

	public Date getRecord_examine_date() {
		return record_examine_date;
	}

	public void setRecord_examine_date(Date record_examine_date) {
		this.record_examine_date = record_examine_date;
	}

	public String getExamine_user() {
		return examine_user;
	}

	public void setExamine_user(String examine_user) {
		this.examine_user = examine_user;
	}

	public Date getRecord_release_date() {
		return record_release_date;
	}

	public void setRecord_release_date(Date record_release_date) {
		this.record_release_date = record_release_date;
	}

	public String getRelease_user() {
		return release_user;
	}

	public void setRelease_user(String release_user) {
		this.release_user = release_user;
	}

	public Date getRecord_implement_date() {
		return record_implement_date;
	}

	public void setRecord_implement_date(Date record_implement_date) {
		this.record_implement_date = record_implement_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEstablish_user() {
		return establish_user;
	}

	public void setEstablish_user(String establish_user) {
		this.establish_user = establish_user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
