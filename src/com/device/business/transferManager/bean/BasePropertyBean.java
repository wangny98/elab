package com.device.business.transferManager.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BasePropertyBean {

	private String id;

	private String fanumber;

	private String faname;
	
	private String faclassification;
	
	private String tableType;
	
	private int faUseAge;
	
	private Date faPdate;
	
	private int checked=0;
	
	/**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public int getFaUseAge() {
		return faUseAge;
	}

	public void setFaUseAge(int faUseAge) {
		this.faUseAge = faUseAge;
	}

	public Date getFaPdate() {
		return faPdate;
	}

	public void setFaPdate(Date faPdate) {
		this.faPdate = faPdate;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
	}

	public String getFaclassification() {
		return faclassification;
	}

	public void setFaclassification(String faclassification) {
		this.faclassification = faclassification;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}
    
    
}
