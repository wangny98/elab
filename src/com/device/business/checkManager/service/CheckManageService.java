package com.device.business.checkManager.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.dao.AssetDao;
import com.device.business.checkManager.bean.CheckManageBean;
import com.device.business.checkManager.bean.SearchBean;
import com.device.business.checkManager.dao.CheckManageDao;
import com.device.business.checkManager.element.CheckManageElement;
import com.device.business.login.element.LoginElement;
import com.device.business.print.service.BaseReportService;
import com.device.component.cache.UserInfoCache;
import com.device.component.cache.UserInfoCatcheDto;
import com.device.constant.ReturnCode;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;
import com.sun.org.apache.bcel.internal.generic.NEW;


@Path("/checkManage")
public class CheckManageService {
	@Autowired
	private CheckManageDao checkManageDao; 
	
	@Autowired
	private UserInfoCache userInfoCache;
	@Autowired
	AssetDao assetDao;
	
	@Path("/searchPd")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> searchPd(@Form CheckManageElement element,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("year") String year,@FormParam("fanumber") String fanumber,
			@FormParam("pdname") String pdname, @FormParam("pdresult") String pdresult,
			@Context HttpServletRequest request){
		RestResponse response = new RestResponse();
		int count = 0;
		List<AssetBean> list = new ArrayList<AssetBean>();
		try{
			CheckManageBean checkManageBean = new CheckManageBean();
			checkManageBean.setFanumber(fanumber);
			if (year != null && !year.equals("")) {
				checkManageBean.setYearfrom(year+"-1-1");
				checkManageBean.setYearto(year+"-12-31");
			}
			if (pdname != null && !pdname.equals("")) {
				checkManageBean.setPdname("%"+pdname+"%");
			}
			checkManageBean.setPdresult(pdresult);
			
			LoginElement loginElement =AuthorityContext.getLoginElement();
			String id = loginElement.getUserId();
			if(userInfoCache.getUserInfo(id) != null){
             	HashMap<String,Object> map =new HashMap<String,Object>();
             	map.put("checkManageBean", checkManageBean);
             	map.put("loginElement", loginElement);
             	UserInfoCatcheDto dto = new UserInfoCatcheDto();
             	dto.setUserInfo(map);
             	userInfoCache.addUserInfo(id, dto);
            }
			request.getSession().setAttribute("checkManageBean", checkManageBean);
			
			list  = checkManageDao.queryPd(checkManageBean,start, limit);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("盘点结果");
				dictionaryBean.setAttr_key(tmp.getPdresult());
				tmp.setPdresultText(assetDao.getComboValue(dictionaryBean));
			}
			count = checkManageDao.countPd(checkManageBean);
//			count = checkManageDao.countProperty(element);
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("check", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/addPd")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> addPd(@Form CheckManageElement element){
		RestResponse response = new RestResponse();
//		CheckManageBean bean = new CheckManageBean();
		try{
//			BeanUtils.copyProperties(bean, element);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			element.setPddate(format.format(new Date()));
			if (checkManageDao.isPdExist(element.getFanumber()) == 0) {
				element.setId(PrimaryKeyUtil.getSeq());
				checkManageDao.addPd(element);
			}else {
				checkManageDao.updPd(element);
			}
			response.setSuccess(true);
		}catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		
		return response.returnResult();
	}
	
	@SuppressWarnings("null")
	@Path("/exportExcelPd")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> exportExcelPd(
			@QueryParam("scrap_id") String scrap_id,
			@Context HttpServletResponse response,
			@Context HttpServletRequest request) throws IOException {
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
					"部门","使用人","备注","盘点人","盘点日期",
					"盘点结果"};
			String[] sheet = { "资产清查详单" };
			CheckManageBean checkManageBean = new CheckManageBean();
			checkManageBean = (CheckManageBean) request.getSession().getAttribute("checkManageBean");
			List<AssetBean> list = new ArrayList<AssetBean>();
			list = checkManageDao.queryPd(checkManageBean);
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
				dictionaryBean.setAttr_name("盘点结果");
				dictionaryBean.setAttr_key(tmp.getPdresult());
				tmp.setPdresultText(assetDao.getComboValue(dictionaryBean));
				if (tmp.getFameasure().equals("1")) {
					tmp.setFameasureText("是");
				}else {
					tmp.setFameasureText("否");
				}
			}
			map.put(0, list);
			
			workBook = checkManageDao.exportPd(sheet, title, map);
			
			export_Exl = URLEncoder.encode("assetPdList.xls", "utf-8");
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
    @Path("/printPd")
    @Produces(MediaType.TEXT_HTML)
	public void printPd(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam("userid") String userid) throws ServletException, IOException, JRException{
		List<AssetBean> list = new ArrayList<AssetBean>();
		CheckManageBean checkManageBean = new CheckManageBean();
		LoginElement loginElement = new LoginElement();
		
		if(userInfoCache.getUserInfo(userid) !=null){
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("checkManageBean")) {
				checkManageBean = (CheckManageBean) userInfoCache.getUserInfo(userid).getUserInfo().get("checkManageBean");
			}
			if (userInfoCache.getUserInfo(userid).getUserInfo().containsKey("loginElement")) {
				loginElement = (LoginElement) userInfoCache.getUserInfo(userid).getUserInfo().get("loginElement");
			}
        }
		
		try{
			list  = checkManageDao.queryPd(checkManageBean);
			for (int i = 0; i < list.size(); i++) {
				AssetBean tmp = list.get(i);
				DictionaryBean dictionaryBean = new DictionaryBean();
				tmp.setFaclassificationText(assetDao.getClassName(tmp.getFaclassification()));
				tmp.setFadeptText(assetDao.getDeptName(tmp.getFadept()));
				dictionaryBean.setAttr_name("盘点结果");
				dictionaryBean.setAttr_key(tmp.getPdresult());
				tmp.setPdresultText(assetDao.getComboValue(dictionaryBean));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reportName = "PrintPd";
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("printPerson", loginElement.getUserFullName());
        parameters.put("reportName", "资产信息盘点表");
        Date nowd = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameters.put("staticsTime", dateFormat.format(nowd));
        
        BaseReportService.htmlReport(request, response, reportName, list, parameters);
	}
	
}
