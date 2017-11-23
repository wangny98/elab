package com.device.business.recordManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RecordGrantElement {
	
	private String	id;
	
	@FormParam("grant_no")
	private String	grant_no;
	
	@FormParam("consume_user")
	private String	consume_user;
	
	@FormParam("consume_date")
	private Date	consume_date;
	
	@FormParam("remark")
	private String	remark;
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGrant_no() {
		return grant_no;
	}

	public void setGrant_no(String grant_no) {
		this.grant_no = grant_no;
	}

	public String getConsume_user() {
		return consume_user;
	}

	public void setConsume_user(String consume_user) {
		this.consume_user = consume_user;
	}

	public Date getConsume_date() {
		return consume_date;
	}

	public void setConsume_date(Date consume_date) {
		this.consume_date = consume_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
