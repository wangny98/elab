package com.device.business.assetManage.element;

import javax.ws.rs.FormParam;

public class TestResultElement {

	@FormParam("measure.id")
	private String id;
	@FormParam("measure.testdept")
	private String testdept;
	@FormParam("measure.fanumber")
	private String fanumber;
	// 证书编号
	@FormParam("measure.cetificateno")
	private String cetificateno;
	//
	@FormParam("measure.testresult")
	private String testresult;
	//
	@FormParam("measure.teststaff")
	private String teststaff;
	//
	@FormParam("measure.testdate")
	private String testdate;
	//
	@FormParam("measure.remark")
	private String remark;
	//
	@FormParam("measure.enterstaff")
	private String enterstaff;
	//
	@FormParam("measure.enterdate")
	private String enterdate;

	private String fameasure;

	public String getFameasure() {
		return fameasure;
	}

	public void setFameasure(String fameasure) {
		this.fameasure = fameasure;
	}

	public String getTestdept() {
		return testdept;
	}

	public void setTestdept(String testdept) {
		this.testdept = testdept;
	}

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
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

	public void setTestdate(String testdate) {
		this.testdate = testdate;
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

	public void setEnterdate(String enterdate) {
		this.enterdate = enterdate;
	}

}
