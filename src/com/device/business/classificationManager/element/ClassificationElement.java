package com.device.business.classificationManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

public class ClassificationElement {
	private String id;
	@FormParam("class_code")
	private String class_code;
	@FormParam("class_name")
	private String class_name;
	@FormParam("class_short")
	private String class_short;
	@FormParam("class_level")
	private int class_level;
	@FormParam("remark")
	private String remark;

	private String creator_id;

	private String creator_name;

	private Date create_time;

	private int deleteflag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClass_code() {
		return class_code;
	}

	public void setClass_code(String class_code) {
		this.class_code = class_code;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getClass_short() {
		return class_short;
	}

	public void setClass_short(String class_short) {
		this.class_short = class_short;
	}

	public int getClass_level() {
		return class_level;
	}

	public void setClass_level(int class_level) {
		this.class_level = class_level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
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

	public int getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(int deleteflag) {
		this.deleteflag = deleteflag;
	}

}
