package com.device.business.transferManager.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BasePropertyUnionBean {
	private String transfer_id;
	
	private String table_name;
	
	private String property_id;

	/**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	
	public String getTransfer_id() {
		return transfer_id;
	}

	public void setTransfer_id(String transfer_id) {
		this.transfer_id = transfer_id;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getProperty_id() {
		return property_id;
	}

	public void setProperty_id(String property_id) {
		this.property_id = property_id;
	}
	
	
}
