package com.device.business.assetManage.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

public class TestResultBean {
	//
	private String id;

	private String testdept;
	// 证书编号
	private String cetificateno;

	private String fanumber;

	private String faname;
	//
	private String testresult;
	//
	private String teststaff;
	//
	private String testdate;

	private String tsdate;

	private String tedate;
	//
	private String remark;
	//
	private String enterstaff;
	//
	private String enterdate;

	public String getTestdept() {
		return testdept;
	}

	public void setTestdept(String testdept) {
		this.testdept = testdept;
	}

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getTsdate() {
		return tsdate;
	}

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
	}

	public void setTsdate(String tsdate) {
		this.tsdate = tsdate;
	}

	public String getTedate() {
		return tedate;
	}

	public void setTedate(String tedate) {
		this.tedate = tedate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCetificateno() {
		return cetificateno;
	}

	public void setCetificateno(String cetificateno) {
		this.cetificateno = cetificateno;
	}

	public String getTestresult() {
		return testresult;
	}

	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}

	public String getTeststaff() {
		return teststaff;
	}

	public void setTeststaff(String teststaff) {
		this.teststaff = teststaff;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnterstaff() {
		return enterstaff;
	}

	public void setEnterstaff(String enterstaff) {
		this.enterstaff = enterstaff;
	}

	public String getTestdate() {
		return testdate;
	}

	public String getEnterdate() {
		return enterdate;
	}

	public void setTestdate(String testdate) {
		this.testdate = testdate;
	}

	public void setEnterdate(String enterdate) {
		this.enterdate = enterdate;
	}

}
