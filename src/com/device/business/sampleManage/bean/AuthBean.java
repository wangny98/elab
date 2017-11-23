package com.device.business.sampleManage.bean;

public class AuthBean {

	private String id;
	private String authproject;
	private String pestname;
	private String authposition;
	private String pesttype;
	private String judge;
	private String judgetitle;
	private String tel;
	private String memo;
	private String demo_id;
	private String authdate;

	private String verifyer;
	private String verifydate;
	private String verifytitle;

	public String getId() {
		if (id != null) {
			return id.trim();
		} else {
			return null;
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthproject() {
		if (authproject != null) {
			return authproject.trim();
		} else {
			return null;
		}
	}

	public void setAuthproject(String authproject) {
		this.authproject = authproject;
	}

	public String getPestname() {
		if (pestname != null) {
			return pestname.trim();
		} else {
			return null;
		}
	}

	public void setPestname(String pestname) {
		this.pestname = pestname;
	}

	public String getAuthposition() {
		if (authposition != null) {
			return authposition.trim();
		} else {
			return null;
		}
	}

	public void setAuthposition(String authposition) {
		this.authposition = authposition;
	}

	public String getPesttype() {
		if (pesttype != null) {
			return pesttype.trim();
		} else {
			return null;
		}
	}

	public void setPesttype(String pesttype) {
		this.pesttype = pesttype;
	}

	public String getJudge() {
		if (judge != null) {
			return judge.trim();
		} else {
			return null;
		}
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getJudgetitle() {
		if (judgetitle != null) {
			return judgetitle.trim();
		} else {
			return null;
		}
	}

	public void setJudgetitle(String judgetitle) {
		this.judgetitle = judgetitle;
	}

	public String getTel() {
		if (tel != null) {
			return tel.trim();
		} else {
			return null;
		}
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemo() {
		if (memo != null) {
			return memo.trim();
		} else {
			return null;
		}
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDemo_id() {
		if (demo_id != null) {
			return demo_id.trim();
		} else {
			return null;
		}
	}

	public void setDemo_id(String demo_id) {
		this.demo_id = demo_id;
	}

	public String getAuthdate() {
		if (authdate != null) {
			return authdate.trim();
		} else {
			return null;
		}
	}

	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}

	public String getVerifyer() {
		if (verifyer != null) {
			return verifyer.trim();
		} else {
			return null;
		}
	}

	public void setVerifyer(String verifyer) {
		this.verifyer = verifyer;
	}

	public String getVerifydate() {
		if (verifydate != null) {
			return verifydate.trim();
		} else {
			return null;
		}
	}

	public void setVerifydate(String verifydate) {
		this.verifydate = verifydate;
	}

	public String getVerifytitle() {
		if (verifytitle != null) {
			return verifytitle.trim();
		} else {
			return null;
		}
	}

	public void setVerifytitle(String verifytitle) {
		this.verifytitle = verifytitle;
	}

}
