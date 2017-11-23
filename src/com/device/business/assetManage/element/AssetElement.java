package com.device.business.assetManage.element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssetElement {
	// ////////基本信息/////////////////////////////
	@FormParam("asset.id")
	private String id;
	@FormParam("asset.fanumber")
	private String fanumber;
	@FormParam("asset.faclassification")
	private String faclassification;
	@FormParam("asset.fagnumber")
	private String fagnumber;
	@FormParam("asset.faname")
	private String faname;
	@FormParam("asset.famodel")
	private String famodel;
	// 制造厂商
	@FormParam("asset.famanufacturer")
	private String famanufacturer;
	@FormParam("asset.faamount")
	private String faamount;
	// 计量单位
	@FormParam("asset.fauom")
	private String fauom;
	// 出厂编号
	@FormParam("asset.fafnum")
	private String fafnum;
	// 是否计量用具 1是0否
	@FormParam("asset.fameasure")
	private int fameasure;
	@FormParam("asset.faarrivedate")
	private String faarrivedate;
	@FormParam("asset.faaccountdate")
	private String faaccountdate;
	// 使用年限
	@FormParam("asset.fauseage")
	private String fauseage;
	// 折旧状态
	@FormParam("asset.fadeprection")
	private String fadeprection;
	// 资金来源
	@FormParam("asset.fafr")
	private String fafr;
	@FormParam("asset.fatov")
	private String fatov;
	@FormParam("asset.favalue")
	private String favalue;
	// 获取方式
	@FormParam("asset.fagm")
	private String fagm;
	// 使用方向
	@FormParam("asset.fadfu")
	private String fadfu;
	// 使用状态
	@FormParam("asset.fausestate")
	private String fausestate;
	// 部门
	@FormParam("asset.fadept")
	private String fadept;
	// 使用人
	@FormParam("asset.fauser")
	private String fauser;
	@FormParam("asset.faremarks")
	private String faremarks;
	@FormParam("asset.status")
	private String status;

	// ///////////计算机信息/////////////////////////////////////////////
	// 设备类型
	@FormParam("asset.devicetype")
	private String devicetype;
	// CPU类型
	@FormParam("asset.cputype")
	private String cputype;
	// 主频
	@FormParam("asset.clockspeed")
	private String clockspeed;
	// 内存
	@FormParam("asset.ram")
	private String ram;
	// 硬盘
	@FormParam("asset.harddisk")
	private String harddisk;
	// 显示器类型
	@FormParam("asset.monitor")
	private String monitor;
	// 键盘
	@FormParam("asset.keyboard")
	private String keyboard;
	// 鼠标
	@FormParam("asset.mouse")
	private String mouse;
	// ///////////车辆/////////////////////////////////
	// 验车月份2
	@FormParam("asset.carvalidatem2")
	private String carvalidatem2;
	// 车辆类型
	@FormParam("asset.cartype")
	private String cartype;
	// 里程数
	@FormParam("asset.mileage")
	private String mileage;
	// 车架号
	@FormParam("asset.carframe")
	private String carframe;
	// 生产日期
	@FormParam("asset.carpdate")
	private String carpdate;
	// 报废日期
	@FormParam("asset.carexpireddate")
	private String carexpireddate;
	// 更新日期
	@FormParam("asset.carupdatedate")
	private String carupdatedate;
	// 座位数
	@FormParam("asset.carseat")
	private String carseat;
	// 燃油种类
	@FormParam("asset.oiltype")
	private String oiltype;
	// 排量
	@FormParam("asset.carcc")
	private String carcc;
	// 验车月份1
	@FormParam("asset.carvalidatem1")
	private String carvalidatem1;
	// 发动机号
	@FormParam("asset.carengine")
	private String carengine;
	// 车牌号
	@FormParam("asset.carnum")
	private String carnum;

	// ////////房屋////////////////////////////////////
	// 联系电话
	@FormParam("asset.htel")
	private String htel;
	// 联系人
	@FormParam("asset.hcontract")
	private String hcontract;
	// 物业
	@FormParam("asset.hwuye")
	private String hwuye;
	// 质量等级
	@FormParam("asset.hquality")
	private String hquality;
	// 建筑结构
	@FormParam("asset.hstructure")
	private String hstructure;
	// 竣工日期
	@FormParam("asset.hcompletedate")
	private String hcompletedate;
	// 房屋类别
	@FormParam("asset.htype")
	private String htype;
	// 房屋面积
	@FormParam("asset.hsize")
	private String hsize;
	// hlandsize土地面积
	@FormParam("asset.hlandsize")
	private String hlandsize;
	// 用途
	@FormParam("asset.hpurpose")
	private String hpurpose;
	// 土地属性
	@FormParam("asset.hlandproperty")
	private String hlandproperty;
	// 供暖方式
	@FormParam("asset.heattype")
	private String heattype;
	// 地下层数
	@FormParam("asset.hblayer")
	private String hblayer;
	// 层数
	@FormParam("asset.hlayer")
	private String hlayer;
	// 房屋产权号
	@FormParam("asset.hcqnum")
	private String hcqnum;
	// 房屋形式
	@FormParam("asset.hform")
	private String hform;
	// 房屋名称17
	@FormParam("asset.hname")
	private String hname;
	// 地下面积
	@FormParam("asset.hbsize")
	private String hbsize;
	// //////////////验收信息///////////////////////////////////////////
	// 验收时间
	@FormParam("asset.checkdate")
	private String checkdate;
	// 验收人员
	@FormParam("asset.checker")
	private String checker;
	// 出厂编号

	// 质保到期
	@FormParam("asset.allcoverdate")
	private String allcoverdate;
	// 验收结果
	@FormParam("asset.checkresult")
	private String checkresult;
	// 存放地点
	@FormParam("asset.faarea")
	private String faarea;
	// 验收说明
	@FormParam("asset.checkremark")
	private String checkremark;
	// //////////////采购信息///////////////////////////////////////////
	// 采购的组织形式
	@FormParam("asset.fapof")
	private String fapof;
	// 采购人
	@FormParam("asset.fabuyer")
	private String fabuyer;
	// 采购合同
	@FormParam("asset.facontract")
	private String facontract;
	// 供应商
	@FormParam("asset.fasupplier")
	private String fasupplier;
	// 供应商电话
	@FormParam("asset.fasuppliertel")
	private String fasuppliertel;
	// 采购日期
	@FormParam("asset.fapdate")
	private String fapdate;
	// 发票号码
	@FormParam("asset.fainvoicenumber")
	private String fainvoicenumber;
	// 会计凭证号
	@FormParam("asset.faadn")
	private String faadn;
	// 是否上报
	@FormParam("asset.fareport")
	private String fareport;

	// ////////////////////////////////////////
	// ////////////////////////////////////////////////////////
	@FormParam("asset.fafirstmeasure")
	private String fafirstmeasure;
	@FormParam("asset.retestperiod")
	private String retestperiod;
	@FormParam("asset.lasttestdate")
	private String lasttestdate;

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

	public String getFaclassification() {
		return faclassification;
	}

	public void setFaclassification(String faclassification) {
		this.faclassification = faclassification;
	}

	public String getFagnumber() {
		return fagnumber;
	}

	public void setFagnumber(String fagnumber) {
		this.fagnumber = fagnumber;
	}

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getFamodel() {
		return famodel;
	}

	public void setFamodel(String famodel) {
		this.famodel = famodel;
	}

	public String getFamanufacturer() {
		return famanufacturer;
	}

	public void setFamanufacturer(String famanufacturer) {
		this.famanufacturer = famanufacturer;
	}

	public String getFaamount() {
		return faamount;
	}

	public void setFaamount(String faamount) {
		this.faamount = faamount;
	}

	public String getFauom() {
		return fauom;
	}

	public void setFauom(String fauom) {
		this.fauom = fauom;
	}

	public String getFafnum() {
		return fafnum;
	}

	public void setFafnum(String fafnum) {
		this.fafnum = fafnum;
	}

	public int getFameasure() {
		return fameasure;
	}

	public void setFameasure(int fameasure) {
		this.fameasure = fameasure;
	}

	public String getFaarrivedate() {
		return faarrivedate;
	}

	public void setFaarrivedate(String faarrivedate) {
		this.faarrivedate = faarrivedate;
	}

	public String getFaaccountdate() {
		return faaccountdate;
	}

	public void setFaaccountdate(String faaccountdate) {
		this.faaccountdate = faaccountdate;
	}

	public String getFauseage() {
		return fauseage;
	}

	public void setFauseage(String fauseage) {
		this.fauseage = fauseage;
	}

	public String getFadeprection() {
		return fadeprection;
	}

	public void setFadeprection(String fadeprection) {
		this.fadeprection = fadeprection;
	}

	public String getFafr() {
		return fafr;
	}

	public void setFafr(String fafr) {
		this.fafr = fafr;
	}

	public String getFatov() {
		return fatov;
	}

	public void setFatov(String fatov) {
		this.fatov = fatov;
	}

	public String getFavalue() {
		return favalue;
	}

	public void setFavalue(String favalue) {
		this.favalue = favalue;
	}

	public String getFagm() {
		return fagm;
	}

	public void setFagm(String fagm) {
		this.fagm = fagm;
	}

	public String getFadfu() {
		return fadfu;
	}

	public void setFadfu(String fadfu) {
		this.fadfu = fadfu;
	}

	public String getFausestate() {
		return fausestate;
	}

	public void setFausestate(String fausestate) {
		this.fausestate = fausestate;
	}

	public String getFadept() {
		return fadept;
	}

	public void setFadept(String fadept) {
		this.fadept = fadept;
	}

	public String getFauser() {
		return fauser;
	}

	public void setFauser(String fauser) {
		this.fauser = fauser;
	}

	public String getFaremarks() {
		return faremarks;
	}

	public void setFaremarks(String faremarks) {
		this.faremarks = faremarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getCputype() {
		return cputype;
	}

	public void setCputype(String cputype) {
		this.cputype = cputype;
	}

	public String getClockspeed() {
		return clockspeed;
	}

	public void setClockspeed(String clockspeed) {
		this.clockspeed = clockspeed;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(String harddisk) {
		this.harddisk = harddisk;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(String keyboard) {
		this.keyboard = keyboard;
	}

	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}

	public String getCarvalidatem2() {
		return carvalidatem2;
	}

	public void setCarvalidatem2(String carvalidatem2) {
		this.carvalidatem2 = carvalidatem2;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getCarframe() {
		return carframe;
	}

	public void setCarframe(String carframe) {
		this.carframe = carframe;
	}

	public String getCarpdate() {
		return carpdate;
	}

	public void setCarpdate(String carpdate) {
		this.carpdate = carpdate;
	}

	public String getCarexpireddate() {
		return carexpireddate;
	}

	public void setCarexpireddate(String carexpireddate) {
		this.carexpireddate = carexpireddate;
	}

	public String getCarupdatedate() {
		return carupdatedate;
	}

	public void setCarupdatedate(String carupdatedate) {
		this.carupdatedate = carupdatedate;
	}

	public String getCarseat() {
		return carseat;
	}

	public void setCarseat(String carseat) {
		this.carseat = carseat;
	}

	public String getOiltype() {
		return oiltype;
	}

	public void setOiltype(String oiltype) {
		this.oiltype = oiltype;
	}

	public String getCarcc() {
		return carcc;
	}

	public void setCarcc(String carcc) {
		this.carcc = carcc;
	}

	public String getCarvalidatem1() {
		return carvalidatem1;
	}

	public void setCarvalidatem1(String carvalidatem1) {
		this.carvalidatem1 = carvalidatem1;
	}

	public String getCarengine() {
		return carengine;
	}

	public void setCarengine(String carengine) {
		this.carengine = carengine;
	}

	public String getCarnum() {
		return carnum;
	}

	public void setCarnum(String carnum) {
		this.carnum = carnum;
	}

	public String getHtel() {
		return htel;
	}

	public void setHtel(String htel) {
		this.htel = htel;
	}

	public String getHcontract() {
		return hcontract;
	}

	public void setHcontract(String hcontract) {
		this.hcontract = hcontract;
	}

	public String getHwuye() {
		return hwuye;
	}

	public void setHwuye(String hwuye) {
		this.hwuye = hwuye;
	}

	public String getHquality() {
		return hquality;
	}

	public void setHquality(String hquality) {
		this.hquality = hquality;
	}

	public String getHstructure() {
		return hstructure;
	}

	public void setHstructure(String hstructure) {
		this.hstructure = hstructure;
	}

	public String getHcompletedate() {
		return hcompletedate;
	}

	public void setHcompletedate(String hcompletedate) {
		this.hcompletedate = hcompletedate;
	}

	public String getHtype() {
		return htype;
	}

	public void setHtype(String htype) {
		this.htype = htype;
	}

	public String getHsize() {
		return hsize;
	}

	public void setHsize(String hsize) {
		this.hsize = hsize;
	}

	public String getHlandsize() {
		return hlandsize;
	}

	public void setHlandsize(String hlandsize) {
		this.hlandsize = hlandsize;
	}

	public String getHpurpose() {
		return hpurpose;
	}

	public void setHpurpose(String hpurpose) {
		this.hpurpose = hpurpose;
	}

	public String getHlandproperty() {
		return hlandproperty;
	}

	public void setHlandproperty(String hlandproperty) {
		this.hlandproperty = hlandproperty;
	}

	public String getHeattype() {
		return heattype;
	}

	public void setHeattype(String heattype) {
		this.heattype = heattype;
	}

	public String getHblayer() {
		return hblayer;
	}

	public void setHblayer(String hblayer) {
		this.hblayer = hblayer;
	}

	public String getHlayer() {
		return hlayer;
	}

	public void setHlayer(String hlayer) {
		this.hlayer = hlayer;
	}

	public String getHcqnum() {
		return hcqnum;
	}

	public void setHcqnum(String hcqnum) {
		this.hcqnum = hcqnum;
	}

	public String getHform() {
		return hform;
	}

	public void setHform(String hform) {
		this.hform = hform;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getHbsize() {
		return hbsize;
	}

	public void setHbsize(String hbsize) {
		this.hbsize = hbsize;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getAllcoverdate() {
		return allcoverdate;
	}

	public void setAllcoverdate(String allcoverdate) {
		this.allcoverdate = allcoverdate;
	}

	public String getCheckresult() {
		return checkresult;
	}

	public void setCheckresult(String checkresult) {
		this.checkresult = checkresult;
	}

	public String getFaarea() {
		return faarea;
	}

	public void setFaarea(String faarea) {
		this.faarea = faarea;
	}

	public String getCheckremark() {
		return checkremark;
	}

	public void setCheckremark(String checkremark) {
		this.checkremark = checkremark;
	}

	public String getFapof() {
		return fapof;
	}

	public void setFapof(String fapof) {
		this.fapof = fapof;
	}

	public String getFabuyer() {
		return fabuyer;
	}

	public void setFabuyer(String fabuyer) {
		this.fabuyer = fabuyer;
	}

	public String getFacontract() {
		return facontract;
	}

	public void setFacontract(String facontract) {
		this.facontract = facontract;
	}

	public String getFasupplier() {
		return fasupplier;
	}

	public void setFasupplier(String fasupplier) {
		this.fasupplier = fasupplier;
	}

	public String getFasuppliertel() {
		return fasuppliertel;
	}

	public void setFasuppliertel(String fasuppliertel) {
		this.fasuppliertel = fasuppliertel;
	}

	public String getFapdate() {
		return fapdate;
	}

	public void setFapdate(String fapdate) {
		this.fapdate = fapdate;
	}

	public String getFainvoicenumber() {
		return fainvoicenumber;
	}

	public void setFainvoicenumber(String fainvoicenumber) {
		this.fainvoicenumber = fainvoicenumber;
	}

	public String getFaadn() {
		return faadn;
	}

	public void setFaadn(String faadn) {
		this.faadn = faadn;
	}

	public String getFareport() {
		return fareport;
	}

	public void setFareport(String fareport) {
		this.fareport = fareport;
	}

	public String getFafirstmeasure() {
		return fafirstmeasure;
	}

	public void setFafirstmeasure(String fafirstmeasure) {
		this.fafirstmeasure = fafirstmeasure;
	}

	public String getRetestperiod() {
		return retestperiod;
	}

	public void setRetestperiod(String retestperiod) {
		this.retestperiod = retestperiod;
	}

	public String getLasttestdate() {
		return lasttestdate;
	}

	public void setLasttestdate(String lasttestdate) {
		this.lasttestdate = lasttestdate;
	}

}
