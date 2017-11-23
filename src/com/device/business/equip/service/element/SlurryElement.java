package com.device.business.equip.service.element;

import java.util.Date;

import javax.validation.constraints.Null;
import javax.ws.rs.FormParam;

import com.device.util.DateFormat;

public class SlurryElement {

	@FormParam("projectId")
	private Integer projectId;
	

	@FormParam("sectionId")
	private String sectionId;
	
	@Null
	@DateFormat("yyyy-MM-dd")
	@FormParam("dateAmount")
    private Date dateAmount;
	
	@FormParam("equipmentId")
	private String equipmentId;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Date getDateAmount() {
		return dateAmount;
	}

	public void setDateAmount(Date dateAmount) {
		this.dateAmount = dateAmount;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
    
    
}
