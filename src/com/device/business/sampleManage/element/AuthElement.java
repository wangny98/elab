package com.device.business.sampleManage.element;

import javax.ws.rs.FormParam;

public class AuthElement {

	@FormParam("auth.id")
	private String id;
	@FormParam("auth.authproject")
	private String authproject;
	@FormParam("auth.pestname")
	private String pestname;
	@FormParam("auth.authposition")
	private String authposition;
	@FormParam("auth.pesttype")
	private String pesttype;
	@FormParam("auth.judge")
	private String judge;
	@FormParam("auth.judgetitle")
	private String judgetitle;
	@FormParam("auth.tel")
	private String tel;
	@FormParam("auth.memo")
	private String memo;
	@FormParam("auth.demo_id")
	private String demo_id;
	private String authdate;
	
	@FormParam("auth.verifyer")
	private String verifyer;
	@FormParam("auth.verifydate")
	private String verifydate;
	@FormParam("auth.verifytitle")
	private String verifytitle;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthproject() {
		return authproject;
	}
	public void setAuthproject(String authproject) {
		this.authproject = authproject;
	}
	public String getPestname() {
		return pestname;
	}
	public void setPestname(String pestname) {
		this.pestname = pestname;
	}
	public String getAuthposition() {
		return authposition;
	}
	public void setAuthposition(String authposition) {
		this.authposition = authposition;
	}
	public String getPesttype() {
		return pesttype;
	}
	public void setPesttype(String pesttype) {
		this.pesttype = pesttype;
	}
	public String getJudge() {
		return judge;
	}
	public void setJudge(String judge) {
		this.judge = judge;
	}
	public String getJudgetitle() {
		return judgetitle;
	}
	public void setJudgetitle(String judgetitle) {
		this.judgetitle = judgetitle;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDemo_id() {
		return demo_id;
	}
	public void setDemo_id(String demo_id) {
		this.demo_id = demo_id;
	}
	public String getAuthdate() {
		return authdate;
	}
	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}
	public String getVerifyer() {
		return verifyer;
	}
	public void setVerifyer(String verifyer) {
		this.verifyer = verifyer;
	}
	public String getVerifydate() {
		return verifydate;
	}
	public void setVerifydate(String verifydate) {
		this.verifydate = verifydate;
	}
	public String getVerifytitle() {
		return verifytitle;
	}
	public void setVerifytitle(String verifytitle) {
		this.verifytitle = verifytitle;
	}
	
}
