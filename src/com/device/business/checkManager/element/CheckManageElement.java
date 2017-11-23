package com.device.business.checkManager.element;

import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CheckManageElement {
	@FormParam("check.id")
	private String id;

	@FormParam("check.fanumber")
	private String fanumber;

	@FormParam("check.remark")
	private String remark;

	/**** 盘点 ****/
	@FormParam("check.pdname")
	private String pdname;

	@FormParam("check.pdresult")
	private String pdresult;

	private String pddate;

	/**** 清查 ****/
	@FormParam("check.qcname")
	private String qcname;

	@FormParam("check.qcresult")
	private String qcresult;

	private String qcdate;

	/**
	 * 重写toString()方法
	 * 
	 * @return 返回对象的字符串表达
	 */
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

	public String getFanumber() {
		return fanumber;
	}

	public void setFanumber(String fanumber) {
		this.fanumber = fanumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPdname() {
		return pdname;
	}

	public void setPdname(String pdname) {
		this.pdname = pdname;
	}

	public String getPdresult() {
		return pdresult;
	}

	public void setPdresult(String pdresult) {
		this.pdresult = pdresult;
	}

	public String getQcname() {
		return qcname;
	}

	public void setQcname(String qcname) {
		this.qcname = qcname;
	}

	public String getQcresult() {
		return qcresult;
	}

	public void setQcresult(String qcresult) {
		this.qcresult = qcresult;
	}

	public String getPddate() {
		return pddate;
	}

	public void setPddate(String pddate) {
		this.pddate = pddate;
	}

	public String getQcdate() {
		return qcdate;
	}

	public void setQcdate(String qcdate) {
		this.qcdate = qcdate;
	}

}
