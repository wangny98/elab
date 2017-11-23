package com.device.business.systemSet.element;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DepartmentElement {
	private String id;
	
	@FormParam("depart_name")
	private String depart_name;
	
	@FormParam("depart_code")
	private String depart_code;
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepart_name() {
		return depart_name;
	}

	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

	public String getDepart_code() {
		return depart_code;
	}

	public void setDepart_code(String depart_code) {
		this.depart_code = depart_code;
	}
}
