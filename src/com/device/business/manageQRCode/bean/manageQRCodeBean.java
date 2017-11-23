package com.device.business.manageQRCode.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class manageQRCodeBean {

	private String id;
	private String asset_id;
	private byte[] qrcode;
	private Integer state;
	private String description;
	private Date create_date;
	private String picPath;
	private String fanumber;
	private String faname;
	private String faclassification;
	private String fadept;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public byte[] getQrcode() {
		return qrcode;
	}

	public void setQrcode(byte[] qrcode) {
		this.qrcode = qrcode;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
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

	public String getFaclassification() {
		return faclassification;
	}

	public void setFaclassification(String faclassification) {
		this.faclassification = faclassification;
	}

	public String getFadept() {
		return fadept;
	}

	public void setFadept(String fadept) {
		this.fadept = fadept;
	}

	/**
	 * 重写toString()方法
	 * 
	 * @return 返回对象的字符串表达
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
