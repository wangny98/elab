package com.device.business.innerAllocate.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.dao.AssetDao;
import com.device.business.classificationManager.dao.ClassificationDao;
import com.device.business.innerAllocate.bean.InnerAllocateBean;
import com.device.business.innerAllocate.bean.LinkBean;
import com.device.business.innerAllocate.dao.InnerAllocateDao;
import com.device.business.innerAllocate.element.InnerAllocateApplyElement;
import com.device.business.login.element.LoginElement;
import com.device.business.organizationManage.dao.OrganizationDao;
import com.device.business.print.service.BaseReportService;
import com.device.component.cache.UserInfoCache;
import com.device.component.cache.UserInfoCatcheDto;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/innerAllocate")
public class InnerAllocateService {

	@Autowired
	private UserInfoCache userInfoCache;
	
	@Autowired
	InnerAllocateDao innerAllocateDaoDao;
	@Autowired
	AssetDao assetDao;
	@Autowired
	OrganizationDao organizationDao;
	@Autowired
	ClassificationDao classificationDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("id") String id,@FormParam("alloc_depart") String alloc_depart,
			@FormParam("alloc_type") String alloc_type,@FormParam("year") String year,
			@FormParam("status") String status,@FormParam("alloc_user") String alloc_user,
			@FormParam("alloc_holder") String alloc_holder,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<InnerAllocateBean> list = new ArrayList<InnerAllocateBean>();
		int count = 0;
		try{
			InnerAllocateBean innerAllocateBean  = new InnerAllocateBean();
			if (id != null && !id.equals("")) {
				innerAllocateBean.setId(Long.parseLong(id));
			}
			if (alloc_depart != null && !alloc_depart.equals("")) {
				innerAllocateBean.setAlloc_depart(alloc_depart);
			}
			if (alloc_type != null && !alloc_type.equals("")) {
				innerAllocateBean.setAlloc_type(alloc_type);
			}
			if (status != null && !status.equals("")) {
				innerAllocateBean.setStatus(status);
			}
			if (year != null && !year.equals("")) {
				innerAllocateBean.setAsdate(year+"-1-1");
				innerAllocateBean.setAedate(year+"-12-31");
			}
			if (alloc_holder != null && !alloc_holder.equals("")) {
				innerAllocateBean.setAlloc_holder("%"+alloc_holder+"%");
			}
			if (alloc_user != null && !alloc_user.equals("")) {
				innerAllocateBean.setAlloc_user("%"+alloc_user+"%");
			}
			LoginElement loginElement =AuthorityContext.getLoginElement();
			String userId = loginElement.getUserId();
			if(userInfoCache.getUserInfo(userId) != null){
             	HashMap<String,Object> map =new HashMap<String,Object>();
             	map.put("innerAllocateBean", innerAllocateBean);
             	map.put("loginElement", loginElement);
             	UserInfoCatcheDto dto = new UserInfoCatcheDto();
             	dto.setUserInfo(map);
             	userInfoCache.addUserInfo(userId, dto);
            }
			servletRequest.getSession().setAttribute("innerAllocateBean", innerAllocateBean);
			list = innerAllocateDaoDao.queryInnerAllocate(innerAllocateBean, start, limit);
			for (int i = 0; i < list.size(); i++) {
				InnerAllocateBean tmp = list.get(i);
				tmp.setAlloc_departText(assetDao.getDeptName(tmp.getAlloc_depart()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("调拨方式");
				dictionaryBean.setAttr_key(tmp.getAlloc_type());
				tmp.setAlloc_typeText(assetDao.getComboValue(dictionaryBean));
			}
			count = innerAllocateDaoDao.countInnerAllocate(innerAllocateBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("apply", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/addInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addInnerApply(@Form InnerAllocateApplyElement innerAllocateApplyElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
//		System.out.print(innerAllocateApplyElement.getAlloc_date());
		try{
			LoginElement login = AuthorityContext.getLoginElement();
			innerAllocateApplyElement.setApplyer(login.getUserFullName());
			innerAllocateApplyElement.setApply_date(new Date());
			innerAllocateApplyElement.setAlloc_date(new Date());
//			innerAllocateApplyElement.setAlloc_depart(organizationDao.queryDeptCode(innerAllocateApplyElement.getAlloc_depart()));
			if (innerAllocateDaoDao.isAllocExist(innerAllocateApplyElement.getAlloc_id()) == 0) {
				int pk = innerAllocateDaoDao.insertInnerAllocate(innerAllocateApplyElement);
				if (pk == 1) {
					//插入连接表
					String[] fanumbers = innerAllocateApplyElement.getFanumber().split(",");
					for (int i = 0; i < fanumbers.length; i++) {
						LinkBean linkBean = new LinkBean();
						linkBean.setId(PrimaryKeyUtil.getSeq());
						linkBean.setApplyid(innerAllocateApplyElement.getAlloc_id());
						linkBean.setFanumber(fanumbers[i]);
						innerAllocateDaoDao.insertInnerLink(linkBean);
					}
				} else {
					response1.setSuccess(false);
					return response1.returnResult();
				}
			}else{
				innerAllocateDaoDao.updateInnerAllocate(innerAllocateApplyElement);
				innerAllocateDaoDao.deleteInnerLink(innerAllocateApplyElement.getAlloc_id());
				String[] fanumbers = innerAllocateApplyElement.getFanumber().split(",");
				for (int i = 0; i < fanumbers.length; i++) {
					LinkBean linkBean = new LinkBean();
					linkBean.setId(PrimaryKeyUtil.getSeq());
					linkBean.setApplyid(innerAllocateApplyElement.getAlloc_id());
					linkBean.setFanumber(fanumbers[i]);
					innerAllocateDaoDao.insertInnerLink(linkBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/modifyInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyInnerApply(@Form InnerAllocateApplyElement innerAllocateApplyElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
//			innerAllocateApplyElement.setAlloc_depart(organizationDao.queryDeptCode(innerAllocateApplyElement.getAlloc_depart()));
			innerAllocateDaoDao.updateInnerAllocate(innerAllocateApplyElement);
			innerAllocateDaoDao.deleteInnerLink(innerAllocateApplyElement.getAlloc_id());
			String[] fanumbers = innerAllocateApplyElement.getFanumber().split(",");
			for (int i = 0; i < fanumbers.length; i++) {
				LinkBean linkBean = new LinkBean();
				linkBean.setId(PrimaryKeyUtil.getSeq());
				linkBean.setApplyid(innerAllocateApplyElement.getAlloc_id());
				linkBean.setFanumber(fanumbers[i]);
				innerAllocateDaoDao.insertInnerLink(linkBean);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/searchMayApplyAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchMayApplyAsset(@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("fanumber") String fanumber, @FormParam("faclassification") String faclassification,
			@FormParam("faname") String faname, @FormParam("fadept") String fadept){
		RestResponse response = new RestResponse();
		List<AssetBean> list = new ArrayList<AssetBean>();
		int count = 0;
		try{
			AssetBean assetBean = new AssetBean();
			assetBean.setFanumber(fanumber);
			if (faclassification != null && !faclassification.equals("")) {
				assetBean.setFaclassification(classificationDao.queryClassCode(faclassification));
			}
			if (fadept != null && !fadept.equals("")) {
				assetBean.setFadept(organizationDao.queryDeptCode(fadept));
			}
			if (faname != null && !faname.equals("")) {
				assetBean.setFaname("%"+faname+"%");
			}
			assetBean.setStatus("2");
			list = assetDao.queryAssetSearch(assetBean, start, limit);
			count = assetDao.countAssetSearch(assetBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/deleteInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteInnerApply(@FormParam("ids") String ids){
		RestResponse response1 = new RestResponse();
		
		try{
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				innerAllocateDaoDao.deleteInnerAllocate(id[i]);
				innerAllocateDaoDao.deleteInnerLink(id[i]);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/submitInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> submitInnerApply(@FormParam("ids") String ids){
		RestResponse response1 = new RestResponse();
		
		try{
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				LoginElement login = AuthorityContext.getLoginElement();
				InnerAllocateApplyElement innerAllocateApplyElement = new InnerAllocateApplyElement();
				innerAllocateApplyElement.setAlloc_id(id[i]);
				innerAllocateApplyElement.setStatus("3");
				innerAllocateApplyElement.setApply_date(new Date());
				innerAllocateApplyElement.setApplyer(login.getUserFullName());
				innerAllocateDaoDao.submitInnerAllocate(innerAllocateApplyElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/approveInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> approveInnerApply(@FormParam("ids") String ids){
		RestResponse response1 = new RestResponse();
		
		try{
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				LoginElement login = AuthorityContext.getLoginElement();
				InnerAllocateApplyElement innerAllocateApplyElement = new InnerAllocateApplyElement();
				innerAllocateApplyElement.setAlloc_id(id[i]);
				innerAllocateApplyElement.setStatus("5");
				innerAllocateApplyElement.setVerify_date(new Date());
				innerAllocateApplyElement.setVerifyer(login.getUserFullName());
				innerAllocateDaoDao.verifyInnerAllocate(innerAllocateApplyElement);
				String[] fanumber = innerAllocateDaoDao.queryInnerAllocateAssetID(id[i]).split(",");
				for (int k = 0; k < fanumber.length; k++) {
					AssetBean assetBean = new AssetBean();
					assetBean.setFanumber(fanumber[k]);
					assetBean.setStatus("3");
					assetDao.updateAssetStatus(assetBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/rejectInnerApply")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> rejectInnerApply(@FormParam("ids") String ids){
		RestResponse response1 = new RestResponse();
		
		try{
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				InnerAllocateApplyElement innerAllocateApplyElement = new InnerAllocateApplyElement();
				innerAllocateApplyElement.setAlloc_id(id[i]);
				innerAllocateApplyElement.setStatus("4");
				innerAllocateDaoDao.submitInnerAllocate(innerAllocateApplyElement);
				String[] fanumber = innerAllocateDaoDao.queryInnerAllocateAssetID(id[i]).split(",");
				for (int k = 0; k < fanumber.length; k++) {
					AssetBean assetBean = new AssetBean();
					assetBean.setFanumber(fanumber[k]);
					assetBean.setStatus("2");
					assetDao.updateAssetStatus(assetBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/exportExcel")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> exportExcel(
			@QueryParam("scrap_id") String scrap_id,
			@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException {

		RestResponse restResponse = new RestResponse();

		String export_Exl;
		HSSFWorkbook workBook =null;
		
		OutputStream output = null;
		try {
			Map<Integer, List<InnerAllocateBean>> map = new HashMap<Integer, List<InnerAllocateBean>>();
			String[] title = { "申请编号", "资产编号","资产名称","调拨后使用人", "调拨后保管人", "调拨价值", "调拨方式" ,"经办日期",
					"调拨理由","调入部门", "调入科室","申请日期","申请人","审核日期","审核人","状态"};
			String[] sheet = { "内部资产调拨申请" };
			InnerAllocateBean innerAllocateBean = new InnerAllocateBean();
			innerAllocateBean = (InnerAllocateBean)request.getSession().getAttribute("innerAllocateBean");
			List<InnerAllocateBean> dataAllocateBeans = innerAllocateDaoDao.queryInnerAllocate(innerAllocateBean);
			for (int i = 0; i < dataAllocateBeans.size(); i++) {
				InnerAllocateBean tmp = dataAllocateBeans.get(i);
				tmp.setAlloc_departText(assetDao.getDeptName(tmp.getAlloc_depart()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("调拨方式");
				dictionaryBean.setAttr_key(tmp.getAlloc_type());
				tmp.setAlloc_typeText(assetDao.getComboValue(dictionaryBean));
			}
			map.put(0, dataAllocateBeans);
			
			workBook = innerAllocateDaoDao.export(sheet, title, map);
			
			export_Exl = URLEncoder.encode("applyList.xls", "utf-8");
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
	
	@GET
    @Path("/printApply")
    @Produces(MediaType.TEXT_HTML)
	public void printApply(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam("userid") String userid) throws ServletException, IOException, JRException{
		List<InnerAllocateBean> list = new ArrayList<InnerAllocateBean>();
		InnerAllocateBean innerAllocateBean = new InnerAllocateBean();
		LoginElement loginElement = new LoginElement();
		
		if(userInfoCache.getUserInfo(userid) !=null){
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("innerAllocateBean")) {
				innerAllocateBean = (InnerAllocateBean) userInfoCache.getUserInfo(userid).getUserInfo().get("innerAllocateBean");
			}
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("loginElement")) {
				loginElement = (LoginElement) userInfoCache.getUserInfo(userid).getUserInfo().get("loginElement");
			} 
        }
		
		try {
			list = innerAllocateDaoDao.queryInnerAllocate(innerAllocateBean);
			for (int i = 0; i < list.size(); i++) {
				InnerAllocateBean tmp = list.get(i);
				list.get(i).setAlloc_departText(assetDao.getDeptName(tmp.getAlloc_depart()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("调拨方式");
				dictionaryBean.setAttr_key(tmp.getAlloc_type());
				list.get(i).setAlloc_typeText(assetDao.getComboValue(dictionaryBean));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reportName = "PrintApply";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("printPerson", loginElement.getUserFullName());
        parameters.put("reportName", "内部申请调拨表");
        Date nowd = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameters.put("staticsTime", dateFormat.format(nowd));
        
        BaseReportService.htmlReport(request, response, reportName, list, parameters);
	}
	
	@Path("/searchAllocAsset")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchAllocAsset(
			@Context HttpServletRequest request, @Context HttpServletResponse response1,
			@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("id") String id,@FormParam("alloc_depart") String alloc_depart,
			@FormParam("alloc_type") String alloc_type,@FormParam("year") String year,
			@FormParam("status") String status,@FormParam("alloc_user") String alloc_user,
			@FormParam("alloc_holder") String alloc_holder,
			@FormParam("fanumber") String fanumber, @FormParam("faclassification") String faclassification,
			@FormParam("faname") String faname, @FormParam("fadept") String fadept){
		RestResponse response = new RestResponse();
		List<AssetBean> list = new ArrayList<AssetBean>();
		int count = 0;
		try{
			InnerAllocateBean innerAllocateBean  = new InnerAllocateBean();
			if (id != null && !id.equals("")) {
				innerAllocateBean.setId(Long.parseLong(id));
			}
			if (alloc_depart != null && !alloc_depart.equals("")) {
				innerAllocateBean.setAlloc_depart(alloc_depart);
			}
			if (alloc_type != null && !alloc_type.equals("")) {
				innerAllocateBean.setAlloc_type(alloc_type);
			}
			if (status != null && !status.equals("")) {
				innerAllocateBean.setStatus(status);
			}
			if (year != null && !year.equals("")) {
				innerAllocateBean.setAsdate(year+"-1-1");
				innerAllocateBean.setAedate(year+"-12-31");
			}
			if (alloc_holder != null && !alloc_holder.equals("")) {
				innerAllocateBean.setAlloc_holder("%"+alloc_holder+"%");
			}
			if (alloc_user != null && !alloc_user.equals("")) {
				innerAllocateBean.setAlloc_user("%"+alloc_user+"%");
			}
			LoginElement loginElement =AuthorityContext.getLoginElement();
			String userId = loginElement.getUserId();
			if(userInfoCache.getUserInfo(userId) != null){
             	HashMap<String,Object> map =new HashMap<String,Object>();
             	map.put("innerAllocateBean", innerAllocateBean);
             	map.put("loginElement", loginElement);
             	UserInfoCatcheDto dto = new UserInfoCatcheDto();
             	dto.setUserInfo(map);
             	userInfoCache.addUserInfo(userId, dto);
            }
			request.getSession().setAttribute("innerAllocateBean", innerAllocateBean);
			list = innerAllocateDaoDao.queryAllocAsset(innerAllocateBean, start, limit);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				tmp.setFaclassificationText(assetDao.getClassName(tmp.getFaclassification()));
//				tmp.setFadeptText(assetDao.getDeptName(tmp.getFadept()));
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("使用状况");
				dictionaryBean.setAttr_key(tmp.getFausestate());
				tmp.setFausestateText(assetDao.getComboValue(dictionaryBean));
			}
			count = innerAllocateDaoDao.countAllocAsset(innerAllocateBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	
}
