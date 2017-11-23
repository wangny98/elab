package com.device.business.service.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssetBean_list {
	
	private String id;  //资产ID
	private String asset_no; //资产编号
	private String asset_name; //资产名称
	private String asset_type; //资产类型
	private String asset_status; //资产状态

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAsset_no() {
		return asset_no;
	}


	public void setAsset_no(String asset_no) {
		this.asset_no = asset_no;
	}


	public String getAsset_name() {
		return asset_name;
	}


	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}


	public String getAsset_type() {
		return asset_type;
	}


	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}


	public String getAsset_status() {
		return asset_status;
	}


	public void setAsset_status(String asset_status) {
		this.asset_status = asset_status;
	}

	/*	public String getFaentrydate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = null;
		if (faentrydate == null) {
			return "";
		} else {
			time = format.format(faentrydate);
		}
		return time;
	}
	
	public void setFaentrydate(Date faentrydate) {
		this.faentrydate = faentrydate;
	}*/

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
