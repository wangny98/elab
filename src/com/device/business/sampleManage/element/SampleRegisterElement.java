package com.device.business.sampleManage.element;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SampleRegisterElement {

	@FormParam("sample.id")
	private String id;

	@FormParam("sample.submit_no")
	private String submit_no;

	@FormParam("sample.submit_depart")
	private String submit_depart;

	@FormParam("sample.submitter")
	private String submitter;

	@FormParam("sample.submit_phone")
	private String submit_phone;

	@FormParam("sample.submit_date")
	private String submit_date;

	@FormParam("sample.goods_name")
	private String goods_name;

	@FormParam("sample.goods_type")
	private int goods_type;

	@FormParam("sample.sample_name")
	private String sample_name;

	@FormParam("sample.sample_num")
	private String sample_num;

	@FormParam("sample.sample_region")
	private String sample_region;

	@FormParam("sample.export_import")
	private String export_import;

	@FormParam("sample.sample_detail")
	private String sample_detail;

	@FormParam("sample.verify_no")
	private String verify_no;

	@FormParam("sample.verify_identify")
	private String verify_identify;

	@FormParam("sample.verify_circle")
	private String verify_circle;

	@FormParam("sample.verify_type")
	private int verify_type;

	@FormParam("sample.verify_projects")
	private String verify_projects;

	@FormParam("sample.memo")
	private String memo;

	private String status;
	
	@FormParam("sample.chargebackreason")
	private String chargebackreason;
	
	@FormParam("sample.signer")
	private String signer;
	@FormParam("sample.signdate")
	private String signdate;
	@FormParam("sample.signtitle")
	private String signtitle;
	@FormParam("sample.signmemo")
	private String signmemo;
	
	private String reviewer;
	private String reviewdate;
	private String acceptor;
	private String acceptdate;
	private String passer;
	private String passdate;
	private String sender;
	private String senddate;

	public SampleRegisterElement() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubmit_depart() {
		return submit_depart;
	}

	public void setSubmit_depart(String submit_depart) {
		this.submit_depart = submit_depart;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getSubmit_phone() {
		return submit_phone;
	}

	public void setSubmit_phone(String submit_phone) {
		this.submit_phone = submit_phone;
	}

	public String getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(String submit_date) {
		this.submit_date = submit_date;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public int getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(int goods_type) {
		this.goods_type = goods_type;
	}

	public String getSample_name() {
		return sample_name;
	}

	public void setSample_name(String sample_name) {
		this.sample_name = sample_name;
	}

	public String getSample_num() {
		return sample_num;
	}

	public void setSample_num(String sample_num) {
		this.sample_num = sample_num;
	}

	public String getSample_region() {
		return sample_region;
	}

	public void setSample_region(String sample_region) {
		this.sample_region = sample_region;
	}

	public String getExport_import() {
		return export_import;
	}

	public void setExport_import(String export_import) {
		this.export_import = export_import;
	}

	public String getSample_detail() {
		return sample_detail;
	}

	public void setSample_detail(String sample_detail) {
		this.sample_detail = sample_detail;
	}

	public String getVerify_no() {
		return verify_no;
	}

	public void setVerify_no(String verify_no) {
		this.verify_no = verify_no;
	}

	public String getVerify_identify() {
		return verify_identify;
	}

	public void setVerify_identify(String verify_identify) {
		this.verify_identify = verify_identify;
	}

	public String getVerify_circle() {
		return verify_circle;
	}

	public void setVerify_circle(String verify_circle) {
		this.verify_circle = verify_circle;
	}

	public int getVerify_type() {
		return verify_type;
	}

	public void setVerify_type(int verify_type) {
		this.verify_type = verify_type;
	}

	public String getVerify_projects() {
		return verify_projects;
	}

	public void setVerify_projects(String verify_projects) {
		this.verify_projects = verify_projects;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getChargebackreason() {
		return chargebackreason;
	}

	public void setChargebackreason(String chargebackreason) {
		this.chargebackreason = chargebackreason;
	}

	public String getSubmit_no() {
		return submit_no;
	}

	public void setSubmit_no(String submit_no) {
		this.submit_no = submit_no;
	}
	
	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getReviewdate() {
		return reviewdate;
	}

	public void setReviewdate(String reviewdate) {
		this.reviewdate = reviewdate;
	}

	public String getAcceptor() {
		return acceptor;
	}

	public void setAcceptor(String acceptor) {
		this.acceptor = acceptor;
	}

	public String getAcceptdate() {
		return acceptdate;
	}

	public void setAcceptdate(String acceptdate) {
		this.acceptdate = acceptdate;
	}

	public String getPasser() {
		return passer;
	}

	public void setPasser(String passer) {
		this.passer = passer;
	}

	public String getPassdate() {
		return passdate;
	}

	public void setPassdate(String passdate) {
		this.passdate = passdate;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenddate() {
		return senddate;
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public String getSigntitle() {
		return signtitle;
	}

	public void setSigntitle(String signtitle) {
		this.signtitle = signtitle;
	}

	public String getSignmemo() {
		return signmemo;
	}

	public void setSignmemo(String signmemo) {
		this.signmemo = signmemo;
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
