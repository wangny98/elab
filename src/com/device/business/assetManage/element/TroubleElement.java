package com.device.business.assetManage.element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

public class TroubleElement {
	// 编号
	@FormParam("trouble.id")
	private String id;
	@FormParam("trouble.fanumber")
	private String fanumber;
	// 事故日期
	@FormParam("trouble.tdate")
	private String tdate;
	// 地点
	@FormParam("trouble.tplace")
	private String tplace;
	// 事由
	@FormParam("trouble.torigin")
	private String torigin;
	// 责任认定
	@FormParam("trouble.tduty")
	private String tduty;
	// 总费用
	@FormParam("trouble.tcost")
	private String tcost;
	// 需付费用
	@FormParam("trouble.tpaycost")
	private String tpaycost;
	// 备注
	@FormParam("trouble.remark")
	private String remark;
	// 唯一标识，删除用
	@FormParam("trouble.tid")
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

	public Date getTdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (tdate.equals("")) {

		} else {
			try {
				date = sdf.parse(tdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return date;
	}

	public void setTdate(String tdate) {
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
