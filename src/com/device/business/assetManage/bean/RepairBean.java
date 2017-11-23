package com.device.business.assetManage.bean;


public class RepairBean {
	// 编号
	private String id;

	private String fanumber;

	private String faname;

	private String rdept;

	private String rdate;

	private String rcost;

	private String rremark;
	// 出险
	private String chuxian;
	// 赔偿金额
	private String paid;

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

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getRdept() {
		return rdept;
	}

	public void setRdept(String rdept) {
		this.rdept = rdept;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getRcost() {
		return rcost;
	}

	public void setRcost(String rcost) {
		this.rcost = rcost;
	}

	public String getRremark() {
		return rremark;
	}

	public void setRremark(String rremark) {
		this.rremark = rremark;
	}

	public String getChuxian() {
		return chuxian;
	}

	public void setChuxian(String chuxian) {
		this.chuxian = chuxian;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

}