package com.device.business.assetManage.element;

import javax.ws.rs.FormParam;

public class DeviceUseElement {
	@FormParam("deviceuse.id")
	private String id;
	@FormParam("deviceuse.fanumber")
	private String fanumber;
	@FormParam("deviceuse.usedate")
	private String usedate;
	@FormParam("deviceuse.usecontent")
	private String usecontent;
	@FormParam("deviceuse.usebegintime")
	private String usebegintime;
	@FormParam("deviceuse.useendtime")
	private String useendtime;
	@FormParam("deviceuse.count")
	private String count;
	@FormParam("deviceuse.sampleamount")
	private String sampleamount;
	@FormParam("deviceuse.income")
	private String income;
	@FormParam("deviceuse.guifee")
	private String guifee;
	@FormParam("deviceuse.runstate")
	private String runstate;
	@FormParam("deviceuse.operator")
	private String operator;
	@FormParam("deviceuse.usedept")
	private String usedept;

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

	public String getUsedate() {
		return usedate;
	}

	public void setUsedate(String usedate) {
		this.usedate = usedate;
	}

	public String getUsecontent() {
		return usecontent;
	}

	public void setUsecontent(String usecontent) {
		this.usecontent = usecontent;
	}

	public String getUsebegintime() {
		return usebegintime;
	}

	public void setUsebegintime(String usebegintime) {
		this.usebegintime = usebegintime;
	}

	public String getUseendtime() {
		return useendtime;
	}

	public void setUseendtime(String useendtime) {
		this.useendtime = useendtime;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSampleamount() {
		return sampleamount;
	}

	public void setSampleamount(String sampleamount) {
		this.sampleamount = sampleamount;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getGuifee() {
		return guifee;
	}

	public void setGuifee(String guifee) {
		this.guifee = guifee;
	}

	public String getRunstate() {
		return runstate;
	}

	public void setRunstate(String runstate) {
		this.runstate = runstate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUsedept() {
		return usedept;
	}

	public void setUsedept(String usedept) {
		this.usedept = usedept;
	}

}
