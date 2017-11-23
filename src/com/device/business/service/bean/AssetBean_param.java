package com.device.business.service.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssetBean_param {
	
	private String id; //编号
	private String name;
	private String type;
	private String state;


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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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
