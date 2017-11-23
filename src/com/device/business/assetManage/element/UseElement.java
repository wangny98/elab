package com.device.business.assetManage.element;

import javax.ws.rs.FormParam;

public class UseElement {
	@FormParam("use.id")
	private String id;
	@FormParam("use.fanumber")
	private String fanumber;
	@FormParam("use.usestart")
	private String usestart;
	@FormParam("use.useend")
	private String useend;
	@FormParam("use.usedept")
	private String usedept;
	@FormParam("use.u_user")
	private String u_user;
	@FormParam("use.reason")
	private String reason;
	@FormParam("use.mile")
	private String mile;
	@FormParam("use.oilwear")
	private String oilwear;
	// 保险费
	@FormParam("use.premium")
	private String premium;
	// 过路费
	@FormParam("use.toll")
	private String toll;

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

	public String getUsestart() {
		return usestart;
	}

	public void setUsestart(String usestart) {
		this.usestart = usestart;
	}

	public String getUseend() {
		return useend;
	}

	public void setUseend(String useend) {
		this.useend = useend;
	}

	public String getUsedept() {
		return usedept;
	}

	public void setUsedept(String usedept) {
		this.usedept = usedept;
	}

	public String getU_user() {
		return u_user;
	}

	public void setU_user(String u_user) {
		this.u_user = u_user;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMile() {
		return mile;
	}

	public void setMile(String mile) {
		this.mile = mile;
	}

	public String getOilwear() {
		return oilwear;
	}

	public void setOilwear(String oilwear) {
		this.oilwear = oilwear;
	}

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getToll() {
		return toll;
	}

	public void setToll(String toll) {
		this.toll = toll;
	}

}
