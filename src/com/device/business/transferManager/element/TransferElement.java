package com.device.business.transferManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TransferElement {
	
	private String id;
	
	@FormParam("transfer_no")
	private String transfer_no;
	
	@FormParam("org_id")
	private String org_id;
	
	@FormParam("org_unit")
	private String org_unit;
	
	@FormParam("department")
	private String department;
	
	@FormParam("user_id")
	private String user_id;
	
	@FormParam("user_name")
	private String user_name;
	
	@FormParam("change_value")
	private String change_value;
	
	@FormParam("change_way")
	private int change_way;
	
	@FormParam("change_time")
	private Date change_time;
	
	@FormParam("change_reason")
	private String change_reason;
	
	@FormParam("remark")
	private String remark;
	
	@FormParam("state")
	private String state;
	
	private String creator_id;
	private String creator_name;
	private Date create_time;
	private int deleteflag;
	
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

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_unit() {
		return org_unit;
	}

	public void setOrg_unit(String org_unit) {
		this.org_unit = org_unit;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getChange_value() {
		return change_value;
	}

	public void setChange_value(String change_value) {
		this.change_value = change_value;
	}

	public int getChange_way() {
		return change_way;
	}

	public void setChange_way(int change_way) {
		this.change_way = change_way;
	}

	public Date getChange_time() {
		return change_time;
	}

	public void setChange_time(Date change_time) {
		this.change_time = change_time;
	}



	public String getChange_reason() {
		return change_reason;
	}

	public void setChange_reason(String change_reason) {
		this.change_reason = change_reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(int deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getTransfer_no() {
		return transfer_no;
	}

	public void setTransfer_no(String transfer_no) {
		this.transfer_no = transfer_no;
	}

}
