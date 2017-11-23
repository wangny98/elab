package com.device.business.checkManager.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CheckManageBean {

	private String id;

	private String fanumber;

	private String remark;

	/**** 盘点 ****/
	private String pdname;

	private String pdresult;

	private String pddate;

	private String yearfrom;

	private String yearto;

	/**** 清查 ****/
	private String qcname;

	private String qcresult;

	private String qcdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPdname() {
		return pdname;
	}

	public void setPdname(String pdname) {
		this.pdname = pdname;
	}

	public String getPdresult() {
		return pdresult;
	}

	public void setPdresult(String pdresult) {
		this.pdresult = pdresult;
	}

	public String getQcname() {
		return qcname;
	}

	public void setQcname(String qcname) {
		this.qcname = qcname;
	}

	public String getQcresult() {
		return qcresult;
	}

	public void setQcresult(String qcresult) {
		this.qcresult = qcresult;
	}

	public String getPddate() {
		return pddate;
	}

	public void setPddate(String pddate) {
		this.pddate = pddate;
	}

	public String getQcdate() {
		return qcdate;
	}

	public void setQcdate(String qcdate) {
		this.qcdate = qcdate;
	}

	public String getYearfrom() {
		return yearfrom;
	}

	public void setYearfrom(String yearfrom) {
		this.yearfrom = yearfrom;
	}

	public String getYearto() {
		return yearto;
	}

	public void setYearto(String yearto) {
		this.yearto = yearto;
	}

}
