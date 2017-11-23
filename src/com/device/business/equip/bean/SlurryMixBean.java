package com.device.business.equip.bean;

import java.util.Date;

public class SlurryMixBean {
	private String id;
	private Date mixTime;
	private Date dataUploadTime;
	private Integer sequenceNumber;
	private Integer expectWater;
	private Integer expectCement;
	private Float realWater;
	private Integer realCement;
	private Integer realCement2;
	private Integer realSlurry;
	private Float waterCementRatio;
	private Float density;
	private String mixEquipmentId;
	private Integer projectId;
	private Integer sectionId;
	private String mixEquipmentCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getMixTime() {
		return mixTime;
	}
	public void setMixTime(Date mixTime) {
		this.mixTime = mixTime;
	}
	public Date getDataUploadTime() {
		return dataUploadTime;
	}
	public void setDataUploadTime(Date dataUploadTime) {
		this.dataUploadTime = dataUploadTime;
	}
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public Integer getExpectWater() {
		return expectWater;
	}
	public void setExpectWater(Integer expectWater) {
		this.expectWater = expectWater;
	}
	public Integer getExpectCement() {
		return expectCement;
	}
	public void setExpectCement(Integer expectCement) {
		this.expectCement = expectCement;
	}
	public Float getRealWater() {
		return realWater;
	}
	public void setRealWater(Float realWater) {
		this.realWater = realWater;
	}
	public Integer getRealCement() {
		return realCement;
	}
	public void setRealCement(Integer realCement) {
		this.realCement = realCement;
	}
	public Integer getRealCement2() {
		return realCement2;
	}
	public void setRealCement2(Integer realCement2) {
		this.realCement2 = realCement2;
	}
	public Integer getRealSlurry() {
		return realSlurry;
	}
	public void setRealSlurry(Integer realSlurry) {
		this.realSlurry = realSlurry;
	}
	public Float getWaterCementRatio() {
		return waterCementRatio;
	}
	public void setWaterCementRatio(Float waterCementRatio) {
		this.waterCementRatio = waterCementRatio;
	}
	public Float getDensity() {
		return density;
	}
	public void setDensity(Float density) {
		this.density = density;
	}
	public String getMixEquipmentId() {
		return mixEquipmentId;
	}
	public void setMixEquipmentId(String mixEquipmentId) {
		this.mixEquipmentId = mixEquipmentId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getSectionId() {
		return sectionId;
	}
	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}
	public String getMixEquipmentCode() {
		return mixEquipmentCode;
	}
	public void setMixEquipmentCode(String mixEquipmentCode) {
		this.mixEquipmentCode = mixEquipmentCode;
	}
	
	
}
