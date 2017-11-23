package com.device.business.recordManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordChangeElement {
	private String	id;
	@FormParam("change_content")
	private String	change_content;
	
	@FormParam("change_date")
	private Date	change_date;
	
	@FormParam("change_user")
	private String	change_user;
	
	@FormParam("examine_user")
	private String	examine_user;
	
	@FormParam("check_user")
	private String	check_user;
	
	@FormParam("notice_users")
	private String	notice_users;
	
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

	public String getChange_content() {
		return change_content;
	}

	public void setChange_content(String change_content) {
		this.change_content = change_content;
	}

	public Date getChange_date() {
		return change_date;
	}

	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

	public String getChange_user() {
		return change_user;
	}

	public void setChange_user(String change_user) {
		this.change_user = change_user;
	}

	public String getExamine_user() {
		return examine_user;
	}

	public void setExamine_user(String examine_user) {
		this.examine_user = examine_user;
	}

	public String getCheck_user() {
		return check_user;
	}

	public void setCheck_user(String check_user) {
		this.check_user = check_user;
	}

	public String getNotice_users() {
		return notice_users;
	}

	public void setNotice_users(String notice_users) {
		this.notice_users = notice_users;
	}

}
