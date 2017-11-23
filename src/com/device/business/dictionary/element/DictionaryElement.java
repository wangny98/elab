package com.device.business.dictionary.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DictionaryElement {

	/**
	 * 继承serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	private String id;

	@FormParam("attr_name")
	private String attr_name;

	@FormParam("attr_key")
	private String attr_key;

	@FormParam("attr_value")
	private String attr_value;

	@FormParam("attr_type")
	private int attr_type;

	@FormParam("remark")
	 private String remark;
	
	@FormParam("seq")
	private int seq=0;

	@FormParam("state")
	private int state;

	private String creator_id;

	private String creator_name;

	private Date create_time;

	private int deleteflag;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public String getAttr_key() {
		return attr_key;
	}

	public void setAttr_key(String attr_key) {
		this.attr_key = attr_key;
	}

	public String getAttr_value() {
		return attr_value;
	}

	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}

	public int getAttr_type() {
		return attr_type;
	}

	public void setAttr_type(int attr_type) {
		this.attr_type = attr_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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
