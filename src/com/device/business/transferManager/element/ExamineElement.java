package com.device.business.transferManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExamineElement {
	
	@FormParam("id")
	private String  id;
	
	@FormParam("transfer_no")
	private String transfer_no;
	
	@FormParam("property_id")
	private String property_id;
	
	@FormParam("property_codeproperty_code")
	private String property_code;
	
	@FormParam("property_name")
	private String property_name ;
	
	@FormParam("property_type")
	private String property_type;
	
	@FormParam("examine_org")
	private String   examine_org;
	
	@FormParam("examine_id")
	private String   examine_id;
	
	@FormParam("examine_name")
	private String   examine_name;
	
	@FormParam("examine_time")
	private Date   examine_time;
	
	//@FormParam("state")
	private int   state;
	
	@FormParam("action")
	private String   action;
	
	/**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public String getExamine_id() {
		return examine_id;
	}

	public void setExamine_id(String examine_id) {
		this.examine_id = examine_id;
	}

	public String getExamine_name() {
		return examine_name;
	}

	public void setExamine_name(String examine_name) {
		this.examine_name = examine_name;
	}

	public Date getExamine_time() {
		return examine_time;
	}

	public void setExamine_time(Date examine_time) {
		this.examine_time = examine_time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransfer_no() {
		return transfer_no;
	}

	public void setTransfer_no(String transfer_no) {
		this.transfer_no = transfer_no;
	}

	public String getProperty_code() {
		return property_code;
	}

	public void setProperty_code(String property_code) {
		this.property_code = property_code;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getProperty_type() {
		return property_type;
	}

	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}

	public String getExamine_org() {
		return examine_org;
	}

	public void setExamine_org(String examine_org) {
		this.examine_org = examine_org;
	}

	

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProperty_id() {
		return property_id;
	}

	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}
}
