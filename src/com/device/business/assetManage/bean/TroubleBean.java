package com.device.business.assetManage.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TroubleBean {
	// 编号
	private String id;
	private String fanumber;
	// 事故日期
	private Date tdate;
	// 地点
	private String tplace;
	// 事由
	private String torigin;
	// 责任认定
	private String tduty;
	// 总费用
	private String tcost;
	// 需付费用
	private String tpaycost;
	// 备注
	private String remark;
	// 唯一标识，删除用
	private String tid;

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

	public String getTdate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = null;
		if (tdate == null) {
			return "";
		} else {
			time = format.format(tdate);
		}
		return time;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public String getTplace() {
		return tplace;
	}

	public void setTplace(String tplace) {
		this.tplace = tplace;
	}

	public String getTorigin() {
		return torigin;
	}

	public void setTorigin(String torigin) {
		this.torigin = torigin;
	}

	public String getTduty() {
		return tduty;
	}

	public void setTduty(String tduty) {
		this.tduty = tduty;
	}

	public String getTcost() {
		return tcost;
	}

	public void setTcost(String tcost) {
		this.tcost = tcost;
	}

	public String getTpaycost() {
		return tpaycost;
	}

	public void setTpaycost(String tpaycost) {
		this.tpaycost = tpaycost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}
