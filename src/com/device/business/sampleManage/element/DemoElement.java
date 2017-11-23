package com.device.business.sampleManage.element;

import javax.ws.rs.FormParam;

public class DemoElement {

	@FormParam("small.id")
	private String id;
	@FormParam("small.basic_id")
	private String basic_id;
	@FormParam("small.no")
	private String no;
	@FormParam("small.name")
	private String name;
	@FormParam("small.total")
	private String total;
	@FormParam("small.requirement")
	private String requirement;
	@FormParam("small.memo")
	private String memo;
	@FormParam("small.judge")
	private String judge;
	private String status;
	
	private String handletype;
	@FormParam("small.destroyer")
	private String destroyer;
	@FormParam("small.destroydate")
	private String destroydate;
	@FormParam("small.staytodate")
	private String staytodate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBasic_id() {
		return basic_id;
	}

	public void setBasic_id(String basic_id) {
		this.basic_id = basic_id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHandletype() {
		return handletype;
	}

	public void setHandletype(String handletype) {
		this.handletype = handletype;
	}

	public String getDestroyer() {
		return destroyer;
	}

	public void setDestroyer(String destroyer) {
		this.destroyer = destroyer;
	}

	public String getDestroydate() {
		return destroydate;
	}

	public void setDestroydate(String destroydate) {
		this.destroydate = destroydate;
	}

	public String getStaytodate() {
		return staytodate;
	}

	public void setStaytodate(String staytodate) {
		this.staytodate = staytodate;
	}

}
