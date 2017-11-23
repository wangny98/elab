package com.device.business.assetManage.element;

import javax.ws.rs.FormParam;

public class RepairElement {
	// 编号
	@FormParam("repair.id")
	private String id;
	@FormParam("repair.fanumber")
	private String fanumber;
	@FormParam("repair.faname")
	private String faname;
	@FormParam("repair.rdept")
	private String rdept;
	@FormParam("repair.rdate")
	private String rdate;
	@FormParam("repair.rcost")
	private String rcost;
	@FormParam("repair.rremark")
	private String rremark;
	// 出险
	@FormParam("repair.chuxian")
	private String chuxian;
	// 赔偿金额
	@FormParam("repair.paid")
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
