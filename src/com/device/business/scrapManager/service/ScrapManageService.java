package com.device.business.scrapManager.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;


import com.device.business.print.service.BaseReportService;
import com.device.business.scrapManager.bean.ScrapBean;
import com.device.business.scrapManager.dao.ScrapManageDao;
import com.device.business.scrapManager.element.ScrapElement;
import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.business.transferManager.element.TransferElement;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.device.filter.AuthorityContext;
import com.device.business.login.element.LoginElement;
@Path("/scrapManage")
public class ScrapManageService {
	@Autowired
	ScrapManageDao scrapManageDao;

	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> search(
			@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("stateStr") String stateStr) {
		RestResponse response = new RestResponse();
		String states = new String();

		if (stateStr.equals("scrap")) {
			states = "(1,2,3)";
		} else if (stateStr.equals("examine")) {
			states = "(3,4,5)";
		} else if (stateStr.equals("check")) {
			states = "(5,6,7)";
		} else if (stateStr.equals("approve")) {
			states = "(7,8,9)";
		}else if(stateStr.equals("8")){
			states = "(8)";
		}
		System.out
				.println("states .................................." + states);
		int count = 0;
		List<ScrapBean> list = new ArrayList<ScrapBean>();

		try {
			list = scrapManageDao.queryTransfer(searchName, start, limit,
					states);
			count = scrapManageDao.count(searchName, states);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}

		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}

	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> add(@Form ScrapElement element,
			@FormParam("property_ids") String property_ids,
			@FormParam("property_types") String property_types,@FormParam("state")String stateStr) {
		RestResponse response = new RestResponse();
		ScrapBean bean = new ScrapBean();
		int state = 0;
		if(stateStr==null||stateStr.equals(""))
			state = 1;
		else
			state = Integer.valueOf(stateStr);
		try {
			BeanUtils.copyProperties(bean, element);
			scrapManageDao.add(bean, property_ids, property_types,state);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}

		return response.returnResult();
	}

	@Path("/load")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> load(@FormParam("id") String id) {
		RestResponse response = new RestResponse();
		try {
			ScrapBean bean = scrapManageDao.load(id);
			response.setRetObject("bean", bean);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			response.setRetObject("bean", null);
			return response.returnResult();
		}
		return response.returnResult();
	}

	@Path("/modify")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> modify(@Form ScrapElement element,
			@FormParam("id") String id,
			@FormParam("property_ids") String property_ids,
			@FormParam("property_types") String property_types) {
		RestResponse response = new RestResponse();
		ScrapBean bean = new ScrapBean();
		try {
			BeanUtils.copyProperties(bean, element);
			bean.setId(id);
			scrapManageDao.update(bean,property_ids,property_types);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}

	@Path("/searchBaseProperty")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> searchBaseProperty(
			@FormParam("scrap_id") String scrap_id,
			@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit) {
		RestResponse response = new RestResponse();
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		int count = 0;
		try {
			list = scrapManageDao.listBaseProperty(searchName, start, limit,scrap_id);
			count = scrapManageDao.countBaseProperty(searchName);
		} catch (Exception e) {
			e.printStackTrace();
			response.setRetObject("list", list);
			response.setSuccess(false);
			return response.returnResult();
		}

		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SystemException);
		return response.returnResult();
	}

	@Path("/queryBaseProperty")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> queryBaseProperty(
			@FormParam("scrap_id") String scrap_id,
			@FormParam("start") int start, @FormParam("limit") int limit) {
		RestResponse response = new RestResponse();
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		int count = 0;
		try {
			list = scrapManageDao.queryCheckProperty(scrap_id, start, limit);
			count = scrapManageDao.countCheckProperty(scrap_id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setRetObject("list", list);
			response.setSuccess(false);
			return response.returnResult();
		}

		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SystemException);
		return response.returnResult();
	}

	@Path("/changScrapState")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> changTransferState(
			@FormParam("scrap_id") String scrap_id,
			@FormParam("state") String state) {
		RestResponse response = new RestResponse();
		try {
			ScrapBean bean = scrapManageDao.load(scrap_id);
			//System.out.println("bean : "+bean);
			if(bean==null||state==null){
				response.setSuccess(false);
				response.setRetCode(101);//单据不存在
			}else{
				if(this.checkState(state, bean)==1){
					response.setSuccess(false);
					response.setRetCode(102);//状态被修改不存在
				}else{
					scrapManageDao.updatePropertyStatue(scrap_id, state);
					response.setSuccess(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	public int checkState(String state,ScrapBean bean){
		int num=0;
		Integer changeState = Integer.valueOf(state);
		Integer st=bean.getState();

		switch(changeState){
			case 2:{
				if(st!=1&&st!=3){
					num=1;
				}
				break;
			}
			case 3:{
				if(st!=1){
					num=1;
				}
				break;
			}
			case 4:{
				if(st!=3){
					num=1;
				}
				break;
			}
			case 5:{
				if(st!=3){
					num=1;
				}
				break;
			}
			case 6:{
				if(st!=5){
					num=1;
				}
				break;
			}
			case 7:{
				if(st!=5){
					num=1;
				}
				break;
			}
			case 8:{
				if(st!=7){
					num=1;
				}
				break;
			}
			case 9:{
				if(st!=7){
					num=1;
				}
				break;
			}
			default:{
				num =1;
			}
		}
		return num;
	}

	
	@GET
    @Path("/PrintScrap")
    @Produces(MediaType.TEXT_HTML)
	public void PrintScrap(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@QueryParam("scrap_id") String scrap_id) throws ServletException, IOException, JRException{
		ScrapBean bean = new ScrapBean();
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		try {
			bean = scrapManageDao.load(scrap_id);
			list = scrapManageDao.queryCheckProperty(scrap_id, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("list : "+list.get(0));
		String reportName = "PrintScrap";
		Map<String, Object> parameters = new HashMap<String, Object>();
        LoginElement loginElement = AuthorityContext.getLoginElement();
        parameters.put("printPerson", loginElement.getUserFullName());
        parameters.put("reportName", "报废报表");
        Date nowd = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        parameters.put("staticsTime", dateFormat.format(nowd));
        
        BaseReportService.htmlReport(request, response, reportName, list, parameters);
	}
}
