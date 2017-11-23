package com.device.business.showManage.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CaseElement {
	
	@FormParam("id")
	private String id;
	
	@FormParam("name")
	private String name;
	
	@FormParam("note")
	private String note;
	
	@FormParam("creator_name")
	private String creator_name;
	
	@FormParam("create_time")
	private Date create_time;
	
	@FormParam("public_org")
	private String public_org;
	
	//@FormParam("public_time")
	private Date public_time;
	
	/**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getPublic_org() {
		return public_org;
	}

	public void setPublic_org(String public_org) {
		this.public_org = public_org;
	}

	public Date getPublic_time() {
		return public_time;
	}

	public void setPublic_time(Date public_time) {
		this.public_time = public_time;
	}
    
}
