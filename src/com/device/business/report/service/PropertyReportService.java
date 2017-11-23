package com.device.business.report.service;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jasperreports.engine.JRException;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.print.service.BaseReportService;
import com.device.business.report.bean.ComponentJsonReaderBean;
import com.device.business.report.dao.PropertyReportDao;
import com.device.business.scrapManager.bean.ScrapBean;
import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.filter.AuthorityContext;
import com.device.util.RestResponse;


@Path("propertyReport")
public class PropertyReportService {
	@Autowired
	private PropertyReportDao propertyReportDao;
	
	@Path("/baseInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public JSONObject baseInfo(){
		RestResponse response = new RestResponse();
		ComponentJsonReaderBean bean  = new ComponentJsonReaderBean();
		JSONObject json= new JSONObject();
		try{
			bean = propertyReportDao.groupPropertyByclassification();
			json = JSONObject.fromObject(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return null;
		}
		json.put("success", true);
        return json;
	}
	
	@Path("/changeInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public JSONObject changeInfo(){
		RestResponse response = new RestResponse();
		ComponentJsonReaderBean bean  = new ComponentJsonReaderBean();
		JSONObject json= new JSONObject();
		try{
			bean = propertyReportDao.propertyChange();
			json = JSONObject.fromObject(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return null;
		}
		json.put("success", true);
        return json;
	}
	
	@GET
    @Path("/reportBaseReport")
    @Produces(MediaType.TEXT_HTML)
	public void reportBaseReport(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException, JRException{

		ComponentJsonReaderBean bean  = new ComponentJsonReaderBean();
		try{
			bean = propertyReportDao.groupPropertyByclassification();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reportName = "reportBaseReport";
		
        
        BaseReportService.htmlReport(request, response, reportName, bean.getData(), null);
	}
	
	@GET
    @Path("/pringChangeInfo")
    @Produces(MediaType.TEXT_HTML)
	public void pringChangeInfo(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException, JRException{

		ComponentJsonReaderBean bean  = new ComponentJsonReaderBean();
		try{
			bean = propertyReportDao.propertyChange();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reportName = "pringChangeInfo";
		
        
        BaseReportService.htmlReport(request, response, reportName, bean.getData(), null);
	}
	
	@GET
	@Path("/exportExl1")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> exportExl1(@Context HttpServletResponse response) throws IOException {

		RestResponse restResponse = new RestResponse();

		String export_Exl;
		HSSFWorkbook workBook =null;
		
		OutputStream output = null;
		try {
			workBook = propertyReportDao.exportEXL1();
			
			export_Exl = URLEncoder.encode("exportExl1.xls", "utf-8");
			response.setContentType("application/x-download;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ export_Exl);
			output = response.getOutputStream();
			workBook.write(output);
            if (output != null) {
                output.close();
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 if (output != null) {
	                output.close();
	         }
		}

		return restResponse.returnResult();
	}
	
	@GET
	@Path("/exportExl2")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> exportExl2(
			@Context HttpServletResponse response) throws IOException {

		RestResponse restResponse = new RestResponse();

		String export_Exl;
		HSSFWorkbook workBook =null;
		
		OutputStream output = null;
		try {
			workBook = propertyReportDao.exportEXL2();
			
			export_Exl = URLEncoder.encode("exportExl2.xls", "utf-8");
			response.setContentType("application/x-download;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ export_Exl);
			output = response.getOutputStream();
			workBook.write(output);
            if (output != null) {
                output.close();
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 if (output != null) {
	                output.close();
	         }
		}

		return restResponse.returnResult();
	}
}
