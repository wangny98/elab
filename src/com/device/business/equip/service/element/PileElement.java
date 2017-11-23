package com.device.business.equip.service.element;

import java.util.Date;

import javax.validation.constraints.Null;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import com.device.util.DateFormat;

public class PileElement {
	
	@QueryParam("mixStationNumber")
	public String mixStationNumber;

	// 桩编号
	@QueryParam("pileNumber")
	public String pileNumber;

	// 打桩机编号
	@QueryParam("pileDriverNumber")
	public String pileDriverNumber;

	// 开始时间
	@DateFormat("yyyy-MM-dd")
	@QueryParam("startTime")
	public Date startTime;

	// 结束时间
	@DateFormat("yyyy-MM-dd")
	@QueryParam("endTime")
	public Date endTime;

	// 制浆站编号
	@QueryParam("sectionNumber")
	public String sectionNumber;
	
	// 工程编号
	@QueryParam("projectId")
	public String projectId;

	public String getPileNumber() {
		return pileNumber;
	}

	public void setPileNumber(String pileNumber) {
		this.pileNumber = pileNumber;
	}

	public String getPileDriverNumber() {
		return pileDriverNumber;
	}

	public void setPileDriverNumber(String pileDriverNumber) {
		this.pileDriverNumber = pileDriverNumber;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public String getMixStationNumber() {
		return mixStationNumber;
	}

	public void setMixStationNumber(String mixStationNumber) {
		this.mixStationNumber = mixStationNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	

}
