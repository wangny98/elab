package com.device.business.assetManage.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.bean.DeviceUseBean;
import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.bean.RentBean;
import com.device.business.assetManage.bean.RepairBean;
import com.device.business.assetManage.bean.TestResultBean;
import com.device.business.assetManage.bean.UseBean;
import com.device.business.assetManage.dao.AssetCommonDao;
import com.device.business.assetManage.dao.AssetComputerDao;
import com.device.business.assetManage.dao.AssetDao;
import com.device.business.assetManage.dao.AssetHouseDao;
import com.device.business.assetManage.dao.AssetHouseholdDao;
import com.device.business.assetManage.dao.AssetInstrumentDao;
import com.device.business.assetManage.dao.AssetVehicleDao;
import com.device.business.assetManage.element.AssetElement;
import com.device.business.assetManage.element.DeviceUseElement;
import com.device.business.assetManage.element.RentElement;
import com.device.business.assetManage.element.RepairElement;
import com.device.business.assetManage.element.TestResultElement;
import com.device.business.assetManage.element.TroubleElement;
import com.device.business.assetManage.element.UseElement;
import com.device.business.checkManager.bean.CheckManageBean;
import com.device.business.checkManager.bean.SearchBean;
import com.device.business.classificationManager.dao.ClassificationDao;
import com.device.business.innerAllocate.bean.InnerAllocateBean;
import com.device.business.login.element.LoginElement;
import com.device.business.manageQRCode.bean.manageQRCodeBean;
import com.device.business.manageQRCode.dao.manageQRCodeDao;
import com.device.business.manageQRCode.service.manageQRCodeService;
import com.device.business.organizationManage.dao.OrganizationDao;
import com.device.business.print.service.BaseReportService;
import com.device.component.cache.UserInfoCache;
import com.device.component.cache.UserInfoCatcheDto;
import com.device.filter.AuthorityContext;
import com.device.util.GucasQRCodeEncoder;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.star.awt.DeviceInfo;

@Path("/assetManage")
public class AssetManageService {
	
	@Autowired
	private UserInfoCache userInfoCache;

	@Autowired
	AssetDao assetDao;
	@Autowired
	AssetHouseholdDao assetHouseholdDao;
	@Autowired
	AssetComputerDao assetComputerDao;
	@Autowired
	AssetInstrumentDao assetInstrumentDao;
	@Autowired
	AssetHouseDao assetHouseDao;
	@Autowired
	AssetVehicleDao assetVehicleDao;
	@Autowired
	AssetCommonDao assetCommonDao;
	@Autowired
	OrganizationDao organizationDao;
	@Autowired
	ClassificationDao classificationDao;
	@Autowired
	manageQRCodeDao maCodeDao;
	
	@Path("/addBasicAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addBasicAsset(@Form AssetElement assetElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetElement.setId(PrimaryKeyUtil.getSeq());
     		assetElement.setFaclassification(assetElement.getFaclassification().split(" ")[0]);
//			assetElement.setFadept(organizationDao.queryDeptCode(assetElement.getFadept()));
			String[] dept = assetElement.getFadept().split(" ");
			assetElement.setFadept(dept[0]);
			assetDao.insertBasicAsset(assetElement);
			if(assetElement.getFaclassification().equals("2010000")){
				assetComputerDao.insertComputerAsset(assetElement);
			} else if (assetElement.getFaclassification().equals("1020000")) {
				assetHouseDao.insertHouseAsset(assetElement);
			} else if (assetElement.getFaclassification().equals("2030000")) {
				assetVehicleDao.insertAssetVehicle(assetElement);
			}
			this.addQRCode(assetElement);
			response1.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		
		return response1.returnResult();
	}
	
	public boolean addQRCode(AssetElement assetElement){
		try{
			StringBuffer message = new StringBuffer();
			message.append(assetElement.getId()).append("&").append(assetElement.getFaname()).
			append("&").append(assetElement.getFaclassification()).append("&")
				.append(assetElement.getFadept()).append("&").append(assetElement.getFaarrivedate());
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GucasQRCodeEncoder.encode(message.toString(), "test.jpg", out);
			
			out.flush();
			byte[] b = out.toByteArray();
			
			manageQRCodeBean code = new manageQRCodeBean();
			code.setAsset_id(assetElement.getFanumber());
			code.setState(1);
			code.setQrcode(b);
			code.setDescription("");
			code.setId(PrimaryKeyUtil.getSeq());
			code.setCreate_date(new Date());
			assetDao.generateQRCode(code);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Path("/searchAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(
			@FormParam("fanumber") String fanumber,@FormParam("faname") String faname,
			@FormParam("faclassification") String faclassification,@FormParam("fadept") String fadept,
			@FormParam("fabuyer") String fabuyer,@FormParam("fauser") String fauser,
			@FormParam("year") String year,@FormParam("fapof") String fapof,
			@FormParam("status") String status,@FormParam("fameasure") String fameasure,
			@FormParam("preToMeasure") String preToMeasure,@FormParam("fafr") String fafr,
			@FormParam("fareport") String fareport,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@Context HttpServletRequest request
		){
		RestResponse response = new RestResponse();
		List<AssetBean> list = new ArrayList<AssetBean>();
		int count = 0;
		try{
			AssetBean assetBean = new AssetBean();
			assetBean.setFanumber(fanumber);
			assetBean.setFafr(fafr);
			assetBean.setFareport(fareport);
			if (faname != null && !faname.equals("")) {
				assetBean.setFaname("%"+faname+"%");
			}
			if (faclassification != null && !faclassification.equals("")) {
				assetBean.setFaclassification(faclassification.split(" ")[0]);
			}
			if (fadept != null && !fadept.equals("")) {
//				assetBean.setFadept(organizationDao.queryDeptCode(fadept));
				assetBean.setFadept(fadept.split(" ")[0]);
			}
			if (fabuyer != null && !fabuyer.equals("")) {
				assetBean.setFabuyer("%"+fabuyer+"%");
			}
			if (fauser != null && !fauser.equals("")) {
				assetBean.setFauser("%"+fauser+"%");
			}
			if (year != null && !year.equals("")) {
				assetBean.setYearfrom(year+"-1-1");
				assetBean.setYearto(year+"-12-31");
			}
			assetBean.setFapof(fapof);
			assetBean.setStatus(status);
			assetBean.setFameasure(fameasure);
			if (preToMeasure != null && !preToMeasure.equals("") && preToMeasure.equals("1")) {
				assetBean.setPreToMeasure(preToMeasure);
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
				Date nowDate = new Date();
				Calendar cal = new GregorianCalendar();  
		        cal.setTime(nowDate); 
		        int Year = cal.get(Calendar.YEAR);  
		        int Month = cal.get(Calendar.MONTH);  
		        int Day = cal.get(Calendar.DAY_OF_MONTH);  
		        
		        int fromMonth = Month - 12;
		        int toMonth = Month -10;
		        cal.set(Calendar.YEAR, Year);  
		        cal.set(Calendar.MONTH, fromMonth);  
		        cal.set(Calendar.DAY_OF_MONTH, Day);  
		        assetBean.setDateFrom(formatDate.format(cal.getTime()));
		        cal.set(Calendar.YEAR, Year);  
		        cal.set(Calendar.MONTH, toMonth); 
		        cal.set(Calendar.DAY_OF_MONTH, Day); 
		        assetBean.setDateTo(formatDate.format(cal.getTime()));
			}

			LoginElement loginElement =AuthorityContext.getLoginElement();
			String id = loginElement.getUserId();
			if(userInfoCache.getUserInfo(id) != null){
             	HashMap<String,Object> map =new HashMap<String,Object>();
             	map.put("assetbean", assetBean);
             	map.put("loginElement", loginElement);
             	UserInfoCatcheDto dto = new UserInfoCatcheDto();
             	dto.setUserInfo(map);
             	userInfoCache.addUserInfo(id, dto);
            }
			request.getSession().setAttribute("assetbean", assetBean);
			
			list = assetDao.queryAssetSearch(assetBean, start,  limit);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				tmp.setFaclassificationText(assetDao.getClassName(tmp.getFaclassification()));
				tmp.setFadeptText(assetDao.getDeptName(tmp.getFadept()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("计量单位");
				dictionaryBean.setAttr_key(tmp.getFauom());
				String tString = assetDao.getComboValue(dictionaryBean);
				tmp.setFauomText(tString);
				dictionaryBean.setAttr_name("折旧状态");
				dictionaryBean.setAttr_key(tmp.getFadeprection());
				tmp.setFadeprectionText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("经费来源");
				dictionaryBean.setAttr_key(tmp.getFafr());
				tmp.setFafrText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("价值类型");
				dictionaryBean.setAttr_key(tmp.getFatov());
				tmp.setFatovText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("取得方式");
				dictionaryBean.setAttr_key(tmp.getFagm());
				tmp.setFagmText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("使用方向");
				dictionaryBean.setAttr_key(tmp.getFadfu());
				tmp.setFadfuText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("使用状况");
				dictionaryBean.setAttr_key(tmp.getFausestate());
				tmp.setFausestateText(assetDao.getComboValue(dictionaryBean));
				///////////////
				dictionaryBean.setAttr_name("设备类型");
				dictionaryBean.setAttr_key(tmp.getDevicetype());
				tmp.setDevicetypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("CPU类型");
				dictionaryBean.setAttr_key(tmp.getCputype());
				tmp.setCputypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("车辆类型");
				dictionaryBean.setAttr_key(tmp.getCartype());
				tmp.setCartypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("燃油类型");
				dictionaryBean.setAttr_key(tmp.getOiltype());
				tmp.setOiltypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("质量等级");
				dictionaryBean.setAttr_key(tmp.getHquality());
				tmp.setHqualityText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("建筑结构");
				dictionaryBean.setAttr_key(tmp.getHstructure());
				tmp.setHstructureText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("房屋类别");
				dictionaryBean.setAttr_key(tmp.getHtype());
				tmp.setHtypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("土地用途");
				dictionaryBean.setAttr_key(tmp.getHpurpose());
				tmp.setHpurposeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("土地属性");
				dictionaryBean.setAttr_key(tmp.getHlandproperty());
				tmp.setHlandpropertyText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("供暖方式");
				dictionaryBean.setAttr_key(tmp.getHeattype());
				tmp.setHeattypeText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("房管形式");
				dictionaryBean.setAttr_key(tmp.getHform());
				tmp.setHformText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("组织形式");
				dictionaryBean.setAttr_key(tmp.getFapof());
				tmp.setFapofText(assetDao.getComboValue(dictionaryBean));
			}
			count = assetDao.countAssetSearch(assetBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("asset", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/modifyBasicAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyBasicAsset(@Form AssetElement assetElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
//			assetElement.setFaclassification(classificationDao.queryClassCode(assetElement.getFaclassification()));
			assetElement.setFaclassification(assetElement.getFaclassification().split(" ")[0]);
//			assetElement.setFadept(organizationDao.queryDeptCode(assetElement.getFadept()));
			assetElement.setFadept(assetElement.getFadept().split(" ")[0]);
			assetDao.updateAsset(assetElement);
			assetComputerDao.updateComputerAsset(assetElement);
			assetHouseDao.updateHouseAsset(assetElement);
			assetVehicleDao.updateAssetVehicle(assetElement);
			maCodeDao.deleteQRCode(assetElement.getFanumber());
			assetElement.setId(assetDao.queryAssetID(assetElement.getFanumber()));
			this.addQRCode(assetElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/addPurchaseAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addPurchaseAsset(@Form AssetElement assetElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			int isExist = assetDao.isPurchaseExist(assetElement.getFanumber());
			
			if (isExist == 0) {
				assetElement.setId(PrimaryKeyUtil.getSeq());
				assetDao.insertPurchaseAsset(assetElement);
			} else {
				assetDao.updatePurchaseAsset(assetElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/addCheckAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addCheckAsset(@Form AssetElement assetElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			int isExist = assetDao.isCheckExist(assetElement.getFanumber());
			
			if (isExist == 0) {
				assetElement.setId(PrimaryKeyUtil.getSeq());
				assetDao.insertCheckAsset(assetElement);
				assetElement.setStatus("2");
				assetDao.updateBasicAssetStatus(assetElement);
			} else {
				assetDao.updateCheckAsset(assetElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/addDeviceUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addDeviceUse(@Form DeviceUseElement deviceUseElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			deviceUseElement.setId(PrimaryKeyUtil.getSeq());
//			deviceUseElement.setUsedept(organizationDao.queryDeptCode(deviceUseElement.getUsedept()));
//			if (deviceUseElement.getUsedept() != null && !deviceUseElement.getUsedept().equals("")) {
//				deviceUseElement.setUsedept(deviceUseElement.getUsedept().split(" ")[0]);
//			}
			
			assetDao.insertDeviceUse(deviceUseElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/modifyDeviceUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyDeviceUse(@Form DeviceUseElement deviceUseElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
//			deviceUseElement.setUsedept(organizationDao.queryDeptCode(deviceUseElement.getUsedept()));
//			if (deviceUseElement.getUsedept() != null && !deviceUseElement.getUsedept().equals("")) {
//				deviceUseElement.setUsedept(deviceUseElement.getUsedept().split(" ")[0]);
//			}
			assetDao.updateDeviceUse(deviceUseElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/deleteDeviceUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteDeviceUse(@Form DeviceUseElement deviceUseElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteDeviceUse(deviceUseElement.getId());
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/searchDeviceUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchDeviceUse(@FormParam("fanumber") String fanumber,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<DeviceUseBean> list = new ArrayList<DeviceUseBean>();
		int count = 0;
		try{
			list = assetDao.queryDeviceUse(fanumber, start, limit);
			for (int i = 0; i < list.size(); i++) {
				DeviceUseBean tmp = list.get(i);
				tmp.setFaname(assetDao.queryAssetName(fanumber));
//				tmp.setUsedept(assetDao.getDeptName(tmp.getUsedept()));
			}
			count = assetDao.countDeviceUse(fanumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/addUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addUse(@Form UseElement useElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			if (assetDao.queryUseByID(useElement.getId()) == 0) {
				useElement.setId(PrimaryKeyUtil.getSeq());
//				useElement.setUsedept(organizationDao.queryDeptCode(useElement.getUsedept()));
//				if (useElement.getUsedept() != null && !useElement.getUsedept().equals("")) {
//					useElement.setUsedept(useElement.getUsedept().split(" ")[0]);
//				}
				assetDao.insertUse(useElement);
			}else{
				assetDao.updateUse(useElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/deleteUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteUse(@Form UseElement useElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteUse(useElement.getId());
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/searchUse")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchUse(@FormParam("fanumber") String fanumber,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<UseBean> list = new ArrayList<UseBean>();
		int count = 0;
		try{
			list = assetDao.queryUse(fanumber, start, limit);
			for (int i = 0; i < list.size(); i++) {
				UseBean tmp = list.get(i);
				tmp.setFaname(assetDao.queryAssetName(fanumber));
//				tmp.setUsedept(assetDao.getDeptName(tmp.getUsedept()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("变动原因");
				dictionaryBean.setAttr_key(tmp.getReason());
				tmp.setReasonText(assetDao.getComboValue(dictionaryBean));
			}
			count = assetDao.countUse(fanumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/deleteRepair")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteRepair(@Form RepairElement repairElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteRepair(repairElement.getId());
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/addRepair")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addRepair(@Form RepairElement repairElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			repairElement.setId(PrimaryKeyUtil.getSeq());
			assetDao.insertRepair(repairElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/modifyRepair")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyRepair(@Form RepairElement repairElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			assetDao.updateRepair(repairElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/searchRepair")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchRepair(@FormParam("fanumber") String fanumber,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<RepairBean> list = new ArrayList<RepairBean>();
		int count = 0;
		try{
			list = assetDao.queryRepair(fanumber, start, limit);
			for (int i = 0; i < list.size(); i++) {
				RepairBean tmp = list.get(i);
				tmp.setFaname(assetDao.queryAssetName(fanumber));
			}
			count = assetDao.countRepair(fanumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/deleteRent")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteRent(@Form RentElement rentElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteRent(rentElement.getId());
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/addRent")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addRent(@Form RentElement rentElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			rentElement.setId(PrimaryKeyUtil.getSeq());
			assetDao.insertRent(rentElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/modifyRent")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyRent(@Form RentElement rentElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			assetDao.updateRent(rentElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/searchRent")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchRent(@FormParam("fanumber") String fanumber,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<RentBean> list = new ArrayList<RentBean>();
		int count = 0;
		try{
			list = assetDao.queryRent(fanumber, start, limit);
			for (int i = 0; i < list.size(); i++) {
				RentBean tmp = list.get(i);
				tmp.setFaname(assetDao.queryAssetName(fanumber));
			}
			count = assetDao.countRent(fanumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/batchGenerate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> batchGenerate(@FormParam("number") String number,@FormParam("fanumber") String fanumber){
		RestResponse response = new RestResponse();
		try{
			AssetBean assetBean = new AssetBean();
			assetBean.setFanumber(fanumber);
			AssetElement item = new AssetElement();
			BeanUtils.copyProperties(item, assetDao.queryAssetSearch(assetBean).get(0));
			int num = Integer.parseInt(number);
			for (int i = 0; i < num; i++) {
				item.setId(PrimaryKeyUtil.getSeq());
				item.setFanumber(this.getBatchFanumber(item));
				item.setFaaccountdate(item.getFaaccountdate().split(" ")[0]);
				item.setFaarrivedate(item.getFaarrivedate().split(" ")[0]);
				assetDao.insertBasicAsset(item);
				if (assetComputerDao.isComputerInfoExist(fanumber) != 0) {
					assetComputerDao.insertComputerAsset(item);
				}
				if (assetHouseDao.isHouseInfoExist(fanumber) != 0) {
					assetHouseDao.insertHouseAsset(item);
				}
				if (assetVehicleDao.isVehicleInfoExist(fanumber) != 0) {
					assetVehicleDao.insertAssetVehicle(item);
				}
				if (assetDao.isCheckExist(fanumber) != 0) {
					assetDao.insertCheckAsset(item);
				}
				if (assetDao.isPurchaseExist(fanumber) != 0) {
					assetDao.insertPurchaseAsset(item);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	private String getBatchFanumber(AssetElement assetBean){
		String fanumber = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		fanumber = format.format(new Date());
		fanumber += assetBean.getFadept();
		AssetBean search = new AssetBean();
		search.setFadept(assetBean.getFadept());
		List<AssetBean> list =  assetDao.queryAssetSearch(search);
		if (list.size() != 0) {
			String tmpString = list.get(0).getFanumber().substring(list.get(0).getFanumber().length()-4);
			int sum = Integer.parseInt(tmpString) + 1;
			tmpString = Integer.toString(sum);
			while(tmpString.length() < 4) {
				tmpString = "0"+tmpString;
			}
			fanumber += tmpString;
		} else {
			fanumber += "0001";
		}
		
		return fanumber;
	}
	
	@Path("/modifyMeasureInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyMeasureInfo(@Form AssetElement assetElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.updateMeasure(assetElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@GET
    @Path("/machineAccount")
    @Produces(MediaType.TEXT_HTML)
	public void machineAccount(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam("userid") String userid) throws ServletException, IOException, JRException{
		List<AssetBean> list = new ArrayList<AssetBean>();
		AssetBean assetBean = new AssetBean();
		LoginElement loginElement = new LoginElement();
		
		if(userInfoCache.getUserInfo(userid) !=null){
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("assetbean")) {
				assetBean = (AssetBean) userInfoCache.getUserInfo(userid).getUserInfo().get("assetbean");
			}
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("loginElement")) {
				loginElement = (LoginElement) userInfoCache.getUserInfo(userid).getUserInfo().get("loginElement");
			}
        }
		
		try{
			list = assetDao.queryAssetSearch(assetBean);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				tmp.setFaclassificationText(assetDao.getClassName(tmp.getFaclassification()));
				tmp.setFadeptText(assetDao.getDeptName(tmp.getFadept()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reportName = "PrintAsset";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("printPerson", loginElement.getUserFullName());
        parameters.put("reportName", "资产台帐");
        Date nowd = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameters.put("staticsTime", dateFormat.format(nowd));
        
        BaseReportService.htmlReport(request, response, reportName, list, parameters);
	}
	
	@Path("/getUserId")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getUserId(){
		RestResponse response = new RestResponse();
		
		LoginElement loginElement =AuthorityContext.getLoginElement();
		String id = loginElement.getUserId();
			
		response.setSuccess(true);
		response.setRetObject("userid", id);
		return response.returnResult();
	}
	
	@Path("/getFanumber")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getFanumber(@FormParam("dept") String dept){
		RestResponse response = new RestResponse();
		String fanumber = null ;
		int count = 0;
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			fanumber = format.format(new Date());
			String deptCode = dept.split(" ")[0];//organizationDao.queryDeptCode(dept); 
			fanumber += deptCode;
			AssetBean assetBean = new AssetBean();
			assetBean.setFadept(deptCode);
			List<AssetBean> list =  assetDao.queryAssetSearch(assetBean);
			if (list.size() != 0) {
				String tmpString = list.get(0).getFanumber().substring(list.get(0).getFanumber().length()-4);
				int sum = Integer.parseInt(tmpString) + 1;
				tmpString = Integer.toString(sum);
				while(tmpString.length() < 4) {
					tmpString = "0"+tmpString;
				}
				fanumber += tmpString;
			} else {
				fanumber += "0001";
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("fanumber", fanumber);
		return response.returnResult();
	}
	
	@Path("/getAssetMayTest")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getAssetMayTest(@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("fanumber") String fanumber, @FormParam("faclassification") String faclassification,
			@FormParam("faname") String faname, @FormParam("fadept") String fadept){
		RestResponse response = new RestResponse();
		List<AssetBean> list = new ArrayList<AssetBean>();
		int count = 0;
		try{
			AssetBean assetBean = new AssetBean();
			assetBean.setFanumber(fanumber);
			assetBean.setFaclassification(faclassification);
			if (faname != null && !faname.equals("")) {
				assetBean.setFaname("%"+faname+"%");
			}
			assetBean.setFadept(fadept.split(" ")[0]);
			list = assetDao.getAssetMayTest(assetBean,start, limit);
			count = assetDao.countAssetMayTest(assetBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/getType")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getType(@FormParam(value = "type") String type){
		RestResponse response = new RestResponse();
		
		List<DictionaryBean> list = new ArrayList<DictionaryBean>();
		try{
			list = assetDao.queryDictionary(type);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		
		response.setSuccess(true);
		response.setRetObject("list", list);
		
		return response.returnResult();
	}
	
	@Path("/searchMeasureResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchMeasureResult(@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("cetificateno") String cetificateno, @FormParam("teststaff") String teststaff,
			@FormParam("year") String year
			){
		RestResponse response = new RestResponse();
		List<TestResultBean> list = new ArrayList<TestResultBean>();
		int count = 0;
		try{
			TestResultBean testResultBean = new TestResultBean();
			testResultBean.setCetificateno(cetificateno);
			if (teststaff != null && !teststaff.equals("") ) {
				testResultBean.setEnterstaff("%"+teststaff+"%");
			}
			if (year != null && !year.equals("")) {
				testResultBean.setTsdate(year+"-1-1");
				testResultBean.setTedate(year+"-12-31");
			}
				
			list = assetDao.queryTestResult(testResultBean, start, limit);
			count = assetDao.countTestResult(testResultBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("measure", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}

	@Path("/addMeasureResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addMeasureResult(@Form TestResultElement testResultElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			LoginElement login = AuthorityContext.getLoginElement();
			testResultElement.setId(PrimaryKeyUtil.getSeq());
			testResultElement.setEnterstaff(login.getUserFullName());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			testResultElement.setEnterdate(df.format(new Date()));
			if (assetDao.queryTestResultByFanumber(testResultElement.getFanumber()) != 0) {
				assetDao.updateTestResult(testResultElement);
			}else{
				assetDao.insertTestResult(testResultElement);
				AssetElement assetElement = new AssetElement();
				assetElement.setFanumber(testResultElement.getFanumber());
				assetElement.setLasttestdate(df.format(new Date()));
				assetDao.updateMeasureLast(assetElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/deleteMeasureResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteMeasureResult(@FormParam(value = "ids") String ids ){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteTestResultUnion(ids);
			assetDao.deleteTestResult(ids);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/modifyMeasureResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyMeasureResult(@Form TestResultElement testResultElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		
		try{
			assetDao.deleteTestResultUnion(testResultElement.getCetificateno());
			assetDao.insertTestResultUnion(testResultElement);
			assetDao.updateTestResult(testResultElement);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@SuppressWarnings("null")
	@Path("/exportExcel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> exportExcel(
			@QueryParam("scrap_id") String scrap_id,
			@Context HttpServletResponse response,
			@Context HttpServletRequest servletRequest) throws IOException {

		RestResponse restResponse = new RestResponse();

		String export_Exl;
		HSSFWorkbook workBook =null;
		
		OutputStream output = null;
		try {
			Map<Integer, List<AssetBean>> map = new HashMap<Integer, List<AssetBean>>();
			String[] title = { "资产编号","资产分类","国资编号","资产名称","规格型号",
					"制造厂商","数量","计量单位","出厂编号","计量器具",
					"到货时间","入账时间","使用年限","折旧状态","经费来源",
					"价值类型","价值","取得方式","使用方向","使用状况",
					"部门","使用人","备注"};
			String[] sheet = { "资产详单" };
			AssetBean assetBean = new AssetBean();
			
			assetBean = (AssetBean) servletRequest.getSession().getAttribute("assetbean");
			List<AssetBean> list = new ArrayList<AssetBean>();
			list = assetDao.queryAssetSearch(assetBean);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				DictionaryBean dictionaryBean = new DictionaryBean();
				tmp.setFaclassificationText(assetDao.getClassName(tmp.getFaclassification()));
				tmp.setFadeptText(assetDao.getDeptName(tmp.getFadept()));
				dictionaryBean.setAttr_name("计量单位");
				dictionaryBean.setAttr_key(tmp.getFauom());
				String tString = assetDao.getComboValue(dictionaryBean);
				tmp.setFauomText(tString);
				dictionaryBean.setAttr_name("折旧状态");
				dictionaryBean.setAttr_key(tmp.getFadeprection());
				tmp.setFadeprectionText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("经费来源");
				dictionaryBean.setAttr_key(tmp.getFafr());
				tmp.setFafrText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("价值类型");
				dictionaryBean.setAttr_key(tmp.getFatov());
				tmp.setFatovText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("取得方式");
				dictionaryBean.setAttr_key(tmp.getFagm());
				tmp.setFagmText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("使用方向");
				dictionaryBean.setAttr_key(tmp.getFadfu());
				tmp.setFadfuText(assetDao.getComboValue(dictionaryBean));
				dictionaryBean.setAttr_name("使用状态");
				dictionaryBean.setAttr_key(tmp.getFausestate());
				tmp.setFausestateText(assetDao.getComboValue(dictionaryBean));
				if (tmp.getFameasure().equals("1")) {
					tmp.setFameasureText("是");
				}else {
					tmp.setFameasureText("否");
				}
			}
			map.put(0, list);
			workBook = assetDao.export(sheet, title, map);
			
			export_Exl = URLEncoder.encode("assetList.xls", "utf-8");
			response.setContentType("application/x-download;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ export_Exl);
			output = response.getOutputStream();
			workBook.write(output);
            if (output != null) {
                output.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 if (output != null) {
	                output.close();
	         }
		}

		return restResponse.returnResult();
	}

	@Path("/deleteAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteAsset(@FormParam(value = "ids") String ids ){
		RestResponse response1 = new RestResponse();
		
		try{
			int i = assetDao.deleteAsset(ids);
			int i1 = assetCommonDao.deleteCommonAsset(ids);
			int i2 = assetComputerDao.deleteComputerAsset(ids);
			int i3 = assetHouseDao.deleteHouseAsset(ids);
			int i4 = assetVehicleDao.deleteAssetVehicle(ids);
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		
		response1.setSuccess(true);
		return response1.returnResult();
	}
}
