package com.device.business.assetManage.element;

import javax.ws.rs.FormParam;

public class RentElement {
	@FormParam("rent.id")
	private String id;
	@FormParam("rent.fanumber")
	private String fanumber;
	@FormParam("rent.rentdate")
	private String rentdate;
	@FormParam("rent.rentmonth")
	private String rentmonth;
	@FormParam("rent.rentdept")
	private String rentdept;
	@FormParam("rent.rentcontracter")
	private String rentcontracter;
	@FormParam("rent.renttel")
	private String renttel;
	@FormParam("rent.rentsize")
	private String rentsize;
	@FormParam("rent.rentamount")
	private String rentamount;

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

	public String getRentdate() {
		return rentdate;
	}

	public void setRentdate(String rentdate) {
		this.rentdate = rentdate;
	}

	public String getRentmonth() {
		return rentmonth;
	}

	public void setRentmonth(String rentmonth) {
		this.rentmonth = rentmonth;
	}

	public String getRentdept() {
		return rentdept;
	}

	public void setRentdept(String rentdept) {
		this.rentdept = rentdept;
	}

	public String getRentcontracter() {
		return rentcontracter;
	}

	public void setRentcontracter(String rentcontracter) {
		this.rentcontracter = rentcontracter;
	}

	public String getRenttel() {
		return renttel;
	}

	public void setRenttel(String renttel) {
		this.renttel = renttel;
	}

	public String getRentsize() {
		return rentsize;
	}

	public void setRentsize(String rentsize) {
		this.rentsize = rentsize;
	}

	public String getRentamount() {
		return rentamount;
	}

	public void setRentamount(String rentamount) {
		this.rentamount = rentamount;
	}

}
