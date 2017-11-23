package com.device.business.systemSet.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UnitElement {

	private String id;
	
	@FormParam("unit_name")
	private String unit_name;
	
	@FormParam("unit_charge_name")
	private String unit_charge_name;
	
	@FormParam("property_charge_name")
	private String property_charge_name;
	
	@FormParam("write_name")
	private String write_name;
	
	@FormParam("telephone")
	private String telephone;
	
	@FormParam("unit_addr")
	private String unit_addr;
	
	@FormParam("unit_code")
	private String unit_code;
	
	@FormParam("finance_code")
	private String finance_code;
	
	@FormParam("post_code")
	private String post_code;
	
	@FormParam("unit_local")
	private String unit_local;
	
	@FormParam("unit_character")
	private String unit_character;
	
	@FormParam("unit_account")
	private String unit_account;
	
	@FormParam("unit_type")
	private String unit_type;
	
	@FormParam("last_year_code")
	private String last_year_code;
	
	@FormParam("depart_classic_type")
	private String depart_classic_type;
	
	@FormParam("report_reason")
	private String report_reason;
	
	@FormParam("report_type")
	private String report_type;
	
	@FormParam("remark_code")
	private String remark_code;
	
	//@FormParam("plait_date")
	private Date plait_date;
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getUnit_charge_name() {
		return unit_charge_name;
	}

	public void setUnit_charge_name(String unit_charge_name) {
		this.unit_charge_name = unit_charge_name;
	}

	public String getProperty_charge_name() {
		return property_charge_name;
	}

	public void setProperty_charge_name(String property_charge_name) {
		this.property_charge_name = property_charge_name;
	}

	public String getWrite_name() {
		return write_name;
	}

	public void setWrite_name(String write_name) {
		this.write_name = write_name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUnit_addr() {
		return unit_addr;
	}

	public void setUnit_addr(String unit_addr) {
		this.unit_addr = unit_addr;
	}

	public String getUnit_code() {
		return unit_code;
	}

	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}

	public String getFinance_code() {
		return finance_code;
	}

	public void setFinance_code(String finance_code) {
		this.finance_code = finance_code;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getUnit_local() {
		return unit_local;
	}

	public void setUnit_local(String unit_local) {
		this.unit_local = unit_local;
	}

	public String getUnit_character() {
		return unit_character;
	}

	public void setUnit_character(String unit_character) {
		this.unit_character = unit_character;
	}

	public String getUnit_account() {
		return unit_account;
	}

	public void setUnit_account(String unit_account) {
		this.unit_account = unit_account;
	}

	public String getUnit_type() {
		return unit_type;
	}

	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}

	public String getLast_year_code() {
		return last_year_code;
	}

	public void setLast_year_code(String last_year_code) {
		this.last_year_code = last_year_code;
	}

	public String getDepart_classic_type() {
		return depart_classic_type;
	}

	public void setDepart_classic_type(String depart_classic_type) {
		this.depart_classic_type = depart_classic_type;
	}

	public String getReport_reason() {
		return report_reason;
	}

	public void setReport_reason(String report_reason) {
		this.report_reason = report_reason;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getRemark_code() {
		return remark_code;
	}

	public void setRemark_code(String remark_code) {
		this.remark_code = remark_code;
	}

	public Date getPlait_date() {
		return plait_date;
	}

	public void setPlait_date(Date plait_date) {
		this.plait_date = plait_date;
	}
	
	
}
