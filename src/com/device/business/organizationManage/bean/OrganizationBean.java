package com.device.business.organizationManage.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class OrganizationBean {
	
	private String id;
	
	private String org_code;
	
	private String org_name;
	
	private String org_short;
	
	private int telephone;
	
	private int org_level;
	
	private String org_addr;
	
	private int fax;
	
	private int tele_addr;
	
	private String remark;
	
	private String creator_id;
	
	private String creator_name;
	
	private Date create_time;
	
	private int deleteflag;
	
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

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_short() {
		return org_short;
	}

	public void setOrg_short(String org_short) {
		this.org_short = org_short;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}

	public int getOrg_level() {
		return org_level;
	}

	public void setOrg_level(int org_level) {
		this.org_level = org_level;
	}

	public String getOrg_addr() {
		return org_addr;
	}

	public void setOrg_addr(String org_addr) {
		this.org_addr = org_addr;
	}

	public int getFax() {
		return fax;
	}

	public void setFax(int fax) {
		this.fax = fax;
	}

	public int getTele_addr() {
		return tele_addr;
	}

	public void setTele_addr(int tele_addr) {
		this.tele_addr = tele_addr;
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

}
