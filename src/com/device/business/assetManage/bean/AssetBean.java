package com.device.business.assetManage.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssetBean {
	private String tabletype;
	// ////////基本信息/////////////////////////////
	private String id;

	private String fanumber;

	private String faclassification;
	private String faclassificationText;

	private String fagnumber;

	private String faname;

	private String famodel;
	// 制造厂商
	private String famanufacturer;

	private String faamount;
	// 计量单位 c
	private String fauom;
	private String fauomText;
	// 出厂编号
	private String fafnum;
	// 是否计量用具 1是0否
	private String fameasure;
	private String fameasureText;

	private String faarrivedate;

	private String faaccountdate;
	// 使用年限
	private String fauseage;
	// 折旧状态
	private String fadeprection;
	private String fadeprectionText;
	// 资金来源
	private String fafr;
	private String fafrText;
	// 价值类型
	private String fatov;
	private String fatovText;

	private String favalue;
	// 获取方式
	private String fagm;
	private String fagmText;
	// 使用方向
	private String fadfu;
	private String fadfuText;
	// 使用状态
	private String fausestate;
	private String fausestateText;
	// 部门
	private String fadept;
	private String fadeptText;
	// 使用人
	private String fauser;

	private String faremarks;

	private String status;
	// ///////////计算机信息/////////////////////////////////////////////
	// 设备类型
	private String devicetype;
	private String devicetypeText;
	// CPU类型
	private String cputype;
	private String cputypeText;
	// 主频
	private String clockspeed;
	// 内存
	private String ram;
	// 硬盘
	private String harddisk;
	// 显示器类型
	private String monitor;
	// 键盘
	private String keyboard;
	// 鼠标
	private String mouse;
	// ///////////车辆/////////////////////////////////
	// 验车月份2
	private String carvalidatem2;
	// 车辆类型
	private String cartype;
	private String cartypeText;
	// 里程数
	private String mileage;
	// 车架号
	private String carframe;
	// 生产日期
	private String carpdate;
	// 报废日期
	private String carexpireddate;
	// 更新日期
	private String carupdatedate;
	// 座位数
	private String carseat;
	// 燃油种类
	private String oiltype;
	private String oiltypeText;
	// 排量
	private String carcc;
	// 验车月份1
	private String carvalidatem1;
	// 发动机号
	private String carengine;
	// 车牌号
	private String carnum;

	// ////////房屋////////////////////////////////////
	// 联系电话
	private String htel;
	// 联系人
	private String hcontract;
	// 物业
	private String hwuye;
	// 质量等级
	private String hquality;
	private String hqualityText;
	// 建筑结构
	private String hstructure;
	private String hstructureText;
	// 竣工日期
	private String hcompletedate;
	// 房屋类别
	private String htype;
	private String htypeText;
	// 房屋面积
	private String hsize;
	// hlandsize土地面积
	private String hlandsize;
	// 用途
	private String hpurpose;
	private String hpurposeText;
	// 土地属性
	private String hlandproperty;
	private String hlandpropertyText;
	// 供暖方式
	private String heattype;
	private String heattypeText;
	// 地下层数
	private String hblayer;
	// 层数
	private String hlayer;
	// 房屋产权号
	private String hcqnum;
	// 房屋形式
	private String hform;
	private String hformText;
	// 房屋名称17
	private String hname;
	// 地下面积
	private String hbsize;
	// //////////////验收信息///////////////////////////////////////////
	// 验收时间
	private String checkdate;
	// 验收人员
	private String checker;
	// 出厂编号
	// check中使用的
	private String cfafnum;
	// 质保到期
	private String allcoverdate;
	// 验收结果
	private String checkresult;
	// 存放地点
	private String faarea;
	// 验收说明
	private String checkremark;
	// //////////////采购信息///////////////////////////////////////////
	// 采购的组织形式
	private String fapof;
	private String fapofText;
	// 采购人
	private String fabuyer;
	// 采购合同
	private String facontract;
	// 供应商
	private String fasupplier;
	// 供应商电话
	private String fasuppliertel;
	// 采购日期
	private String fapdate;
	// 发票号码
	private String fainvoicenumber;
	// 会计凭证号
	private String faadn;
	// 是否上报
	private String fareport;
	// ////////////////////////////////////////////////////////

	private String yearfrom;

	private String yearto;

	// ////////////////////////////////////////////////////////

	private String fafirstmeasure;

	private String retestperiod;

	private String lasttestdate;

	// ////////////////////////////////////////////////////////
	private String preToMeasure;

	private String dateFrom;

	private String dateTo;

	// ////////////////////////////////////////////////////////
	private String qcresult;

	private String qcresultText;

	private String qcname;

	private String qcdate;

	private String remark;
	// ////////////////////////////////////////////////////////
	private String pdresult;

	private String pdresultText;

	private String pdname;

	private String pddate;

	private String pddatatext;

	private String ispdtext;
	
	
	public AssetBean(){
		famodel = "";
		famanufacturer = "";
		faamount = "";
		fafnum = "";
		fauseage = "";
		fafr = "";
		favalue = "";
		fauser = "";
		faremarks = "";
		carcc = "";
		carframe = "";
		carseat = "";
		cartype = "";
		cartypeText = "";
		hcqnum = "";
		hlandsize = "";
		hsize = "";
		htype = "";
		htypeText = "";
	}

	public String getIspdtext() {
		return ispdtext;
	}

	public void setIspdtext(String ispdtext) {
		this.ispdtext = ispdtext;
	}

	public String getPddatatext() {
		return pddatatext;
	}

	public void setPddatatext(String pddatatext) {
		this.pddatatext = pddatatext;
	}

	// ////////////////////////////////////////////////////////

	private String alloc_id;
	private String asset_id;
	private String alloc_reason;
	private String alloc_memo;
	private String alloc_depart;
	private String alloc_departText;
	private String alloc_user;
	private String alloc_holder;
	private Long alloc_value;
	private String alloc_type;
	private String alloc_typeText;
	private String alloc_date;
	private String innerAllocRemark;

	// ////////////////////////////////////////////////////////
	public String getPreToMeasure() {
		return preToMeasure;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public void setPreToMeasure(String preToMeasure) {
		this.preToMeasure = preToMeasure;
	}

	public String getYearfrom() {
		return yearfrom;
	}

	public void setYearfrom(String yearfrom) {
		this.yearfrom = yearfrom;
	}

	public String getYearto() {
		return yearto;
	}

	public void setYearto(String yearto) {
		this.yearto = yearto;
	}

	public String getTabletype() {
		return tabletype;
	}

	public void setTabletype(String tabletype) {
		this.tabletype = tabletype;
	}

	public String getFagnumber() {
		return fagnumber;
	}

	public void setFagnumber(String fagnumber) {
		this.fagnumber = fagnumber;
	}

	public String getCfafnum() {
		return cfafnum;
	}

	public void setCfafnum(String cfafnum) {
		this.cfafnum = cfafnum;
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

	public String getFaname() {
		return faname;
	}

	public void setFaname(String faname) {
		this.faname = faname;
	}

	public String getFaclassification() {
		return faclassification;
	}

	public void setFaclassification(String faclassification) {
		this.faclassification = faclassification;
	}

	public String getFamodel() {
		return famodel;
	}

	public void setFamodel(String famodel) {
		this.famodel = famodel;
	}

	public String getFauom() {
		return fauom;
	}

	public void setFauom(String fauom) {
		this.fauom = fauom;
	}

	public String getFaamount() {
		return faamount;
	}

	public void setFaamount(String faamount) {
		this.faamount = faamount;
	}

	public String getFamanufacturer() {
		return famanufacturer;
	}

	public void setFamanufacturer(String famanufacturer) {
		this.famanufacturer = famanufacturer;
	}

	public String getFagm() {
		return fagm;
	}

	public void setFagm(String fagm) {
		this.fagm = fagm;
	}

	public String getFadept() {
		return fadept;
	}

	public void setFadept(String fadept) {
		this.fadept = fadept;
	}

	public String getFareport() {
		return fareport;
	}

	public void setFareport(String fareport) {
		this.fareport = fareport;
	}

	public String getFapof() {
		return fapof;
	}

	public void setFapof(String fapof) {
		this.fapof = fapof;
	}

	public String getFauseage() {
		return fauseage;
	}

	public void setFauseage(String fauseage) {
		this.fauseage = fauseage;
	}

	public String getFaarea() {
		return faarea;
	}

	public void setFaarea(String faarea) {
		this.faarea = faarea;
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

	public String getFausestate() {
		return fausestate;
	}

	public void setFausestate(String fausestate) {
		this.fausestate = fausestate;
	}

	public String getFadfu() {
		return fadfu;
	}

	public void setFadfu(String fadfu) {
		this.fadfu = fadfu;
	}

	public String getFaremarks() {
		return faremarks;
	}

	public void setFaremarks(String faremarks) {
		this.faremarks = faremarks;
	}

	public String getFameasure() {
		return fameasure;
	}

	public void setFameasure(String fameasure) {
		this.fameasure = fameasure;
	}

	public String getFafnum() {
		return fafnum;
	}

	public void setFafnum(String fafnum) {
		this.fafnum = fafnum;
	}

	public String getFabuyer() {
		return fabuyer;
	}

	public void setFabuyer(String fabuyer) {
		this.fabuyer = fabuyer;
	}

	public String getFaadn() {
		return faadn;
	}

	public void setFaadn(String faadn) {
		this.faadn = faadn;
	}

	public String getFapdate() {
		return fapdate;
	}

	public String getFauser() {
		return fauser;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFaaccountdate() {
		return faaccountdate;
	}

	public String getCheckdate() {
		return checkdate;
	}

	public String getFaarrivedate() {
		return faarrivedate;
	}

	public void setFaarrivedate(String faarrivedate) {
		this.faarrivedate = faarrivedate;
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

	public String getCheckremark() {
		return checkremark;
	}

	public void setCheckremark(String checkremark) {
		this.checkremark = checkremark;
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

	public String getFainvoicenumber() {
		return fainvoicenumber;
	}

	public void setFainvoicenumber(String fainvoicenumber) {
		this.fainvoicenumber = fainvoicenumber;
	}

	public void setFaaccountdate(String faaccountdate) {
		this.faaccountdate = faaccountdate;
	}

	public void setFauser(String fauser) {
		this.fauser = fauser;
	}

	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}

	public void setFapdate(String fapdate) {
		this.fapdate = fapdate;
	}

	public String getFaclassificationText() {
		return faclassificationText;
	}

	public void setFaclassificationText(String faclassificationText) {
		this.faclassificationText = faclassificationText;
	}

	public String getFauomText() {
		return fauomText;
	}

	public void setFauomText(String fauomText) {
		this.fauomText = fauomText;
	}

	public String getFadeprectionText() {
		return fadeprectionText;
	}

	public void setFadeprectionText(String fadeprectionText) {
		this.fadeprectionText = fadeprectionText;
	}

	public String getFafrText() {
		return fafrText;
	}

	public void setFafrText(String fafrText) {
		this.fafrText = fafrText;
	}

	public String getFatovText() {
		return fatovText;
	}

	public void setFatovText(String fatovText) {
		this.fatovText = fatovText;
	}

	public String getFagmText() {
		return fagmText;
	}

	public void setFagmText(String fagmText) {
		this.fagmText = fagmText;
	}

	public String getFadfuText() {
		return fadfuText;
	}

	public void setFadfuText(String fadfuText) {
		this.fadfuText = fadfuText;
	}

	public String getFausestateText() {
		return fausestateText;
	}

	public void setFausestateText(String fausestateText) {
		this.fausestateText = fausestateText;
	}

	public String getFadeptText() {
		return fadeptText;
	}

	public void setFadeptText(String fadeptText) {
		this.fadeptText = fadeptText;
	}

	public String getDevicetypeText() {
		return devicetypeText;
	}

	public void setDevicetypeText(String devicetypeText) {
		this.devicetypeText = devicetypeText;
	}

	public String getCputypeText() {
		return cputypeText;
	}

	public void setCputypeText(String cputypeText) {
		this.cputypeText = cputypeText;
	}

	public String getCartypeText() {
		return cartypeText;
	}

	public void setCartypeText(String cartypeText) {
		this.cartypeText = cartypeText;
	}

	public String getOiltypeText() {
		return oiltypeText;
	}

	public void setOiltypeText(String oiltypeText) {
		this.oiltypeText = oiltypeText;
	}

	public String getHqualityText() {
		return hqualityText;
	}

	public void setHqualityText(String hqualityText) {
		this.hqualityText = hqualityText;
	}

	public String getHstructureText() {
		return hstructureText;
	}

	public void setHstructureText(String hstructureText) {
		this.hstructureText = hstructureText;
	}

	public String getHtypeText() {
		return htypeText;
	}

	public void setHtypeText(String htypeText) {
		this.htypeText = htypeText;
	}

	public String getHpurposeText() {
		return hpurposeText;
	}

	public void setHpurposeText(String hpurposeText) {
		this.hpurposeText = hpurposeText;
	}

	public String getHlandpropertyText() {
		return hlandpropertyText;
	}

	public void setHlandpropertyText(String hlandpropertyText) {
		this.hlandpropertyText = hlandpropertyText;
	}

	public String getHeattypeText() {
		return heattypeText;
	}

	public void setHeattypeText(String heattypeText) {
		this.heattypeText = heattypeText;
	}

	public String getHformText() {
		return hformText;
	}

	public void setHformText(String hformText) {
		this.hformText = hformText;
	}

	public String getFapofText() {
		return fapofText;
	}

	public void setFapofText(String fapofText) {
		this.fapofText = fapofText;
	}

	public String getStatusText() {
		if (status != null) {
			if (status.equals("1")) {
				return "可申请";
			} else if (status.equals("2")) {
				return "正在申请";
			} else if (status.equals("3")) {
				return "正在使用";
			} else if (status.equals("4")) {
				return "已报废";
			}
		}
		return null;
	}

	public String getFareporttext() {
		if (fareport != null) {
			if (fareport.equals("1")) {
				return "已上报";
			} else {
				return "未上报";
			}
		} else {
			return null;
		}
	}

	public String getFameasuretext() {
		if (fameasure.equals("1"))
			return "是";
		else
			return "否";
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

	public String getQcresult() {
		return qcresult;
	}

	public void setQcresult(String qcresult) {
		this.qcresult = qcresult;
	}

	public String getQcname() {
		return qcname;
	}

	public void setQcname(String qcname) {
		this.qcname = qcname;
	}

	public String getQcdate() {
		return qcdate;
	}

	public void setQcdate(String qcdate) {
		this.qcdate = qcdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPdresult() {
		return pdresult;
	}

	public void setPdresult(String pdresult) {
		this.pdresult = pdresult;
	}

	public String getPdname() {
		return pdname;
	}

	public void setPdname(String pdname) {
		this.pdname = pdname;
	}

	public String getPddate() {
		return pddate;
	}

	public void setPddate(String pddate) {
		this.pddate = pddate;
	}

	public String getQcresultText() {
		return qcresultText;
	}

	public void setQcresultText(String qcresultText) {
		this.qcresultText = qcresultText;
	}

	public String getPdresultText() {
		return pdresultText;
	}

	public void setPdresultText(String pdresultText) {
		this.pdresultText = pdresultText;
	}

	public String getFameasureText() {
		return fameasureText;
	}

	public void setFameasureText(String fameasureText) {
		this.fameasureText = fameasureText;
	}

	public String getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}

	public String getAlloc_reason() {
		return alloc_reason;
	}

	public void setAlloc_reason(String alloc_reason) {
		this.alloc_reason = alloc_reason;
	}

	public String getAlloc_memo() {
		return alloc_memo;
	}

	public void setAlloc_memo(String alloc_memo) {
		this.alloc_memo = alloc_memo;
	}

	public String getAlloc_depart() {
		return alloc_depart;
	}

	public void setAlloc_depart(String alloc_depart) {
		this.alloc_depart = alloc_depart;
	}

	public String getAlloc_departText() {
		return alloc_departText;
	}

	public void setAlloc_departText(String alloc_departText) {
		this.alloc_departText = alloc_departText;
	}

	public String getAlloc_user() {
		return alloc_user;
	}

	public void setAlloc_user(String alloc_user) {
		this.alloc_user = alloc_user;
	}

	public String getAlloc_holder() {
		return alloc_holder;
	}

	public void setAlloc_holder(String alloc_holder) {
		this.alloc_holder = alloc_holder;
	}

	public Long getAlloc_value() {
		return alloc_value;
	}

	public void setAlloc_value(Long alloc_value) {
		this.alloc_value = alloc_value;
	}

	public String getAlloc_type() {
		return alloc_type;
	}

	public void setAlloc_type(String alloc_type) {
		this.alloc_type = alloc_type;
	}

	public String getAlloc_typeText() {
		return alloc_typeText;
	}

	public void setAlloc_typeText(String alloc_typeText) {
		this.alloc_typeText = alloc_typeText;
	}

	public String getAlloc_date() {
		return alloc_date;
	}

	public void setAlloc_date(String alloc_date) {
		this.alloc_date = alloc_date;
	}

	public String getInnerAllocRemark() {
		return innerAllocRemark;
	}

	public void setInnerAllocRemark(String innerAllocRemark) {
		this.innerAllocRemark = innerAllocRemark;
	}

	public String getAlloc_id() {
		return alloc_id;
	}

	public void setAlloc_id(String alloc_id) {
		this.alloc_id = alloc_id;
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
