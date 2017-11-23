package com.device.business.manageQRCode.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.element.AssetElement;
import com.device.business.login.element.LoginElement;
import com.device.business.manageQRCode.bean.manageQRCodeBean;
import com.device.business.manageQRCode.dao.manageQRCodeDao;
import com.device.business.print.service.BaseReportService;
import com.device.component.cache.UserInfoCache;
import com.device.filter.AuthorityContext;
import com.device.util.GucasQRCodeEncoder;
import com.device.util.ImgErToFileUtil;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/manageQRCode")
public class manageQRCodeService {

	@Autowired
	manageQRCodeDao arDao;
	
	@Autowired
	private UserInfoCache userInfoCache;
	
	@Path("/getQRCode")
    @GET
    @Produces("image/png") 
	public void  getQRCode(@QueryParam("nos") String nos,
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
//		RestResponse response1 = new RestResponse();
		
		manageQRCodeBean codes = new manageQRCodeBean();
		
		try{
			codes = arDao.getQRCode(nos);
			if (codes != null) {
				response.setContentType("image/jpg"); //设置返回的文件类型     
			    OutputStream os = response.getOutputStream(); 
			    os.write(codes.getQrcode());    
			    os.flush();    
			    os.close();   
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Path("/PrintQrcode")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  PrintQrcode(@FormParam("fanumber") String fanumber,
			@Context HttpServletRequest request,
            @Context HttpServletResponse response) throws ServletException, IOException, JRException{
		RestResponse response1 = new RestResponse();
		List<manageQRCodeBean> codes = new ArrayList<manageQRCodeBean>();
		String realpathString = request.getRealPath("/qrcode");
		manageQRCodeDao.delAllFile(realpathString);
		try{
			String pString = request.getContextPath();
			String[] fanumbers = fanumber.split(","); 
			for (int i = 0; i < fanumbers.length; i++) {
				manageQRCodeBean bean = arDao.getQRCode(fanumbers[i]);
				String pathString = "deviceManager/qrcode/";
				String qrcodename = "qrcode"+ new Date().getTime()+".jpg";
				ImgErToFileUtil.saveToImgByStr(bean.getQrcode(), request.getRealPath("/qrcode"), qrcodename);
				bean.setPicPath(pathString+qrcodename);
				codes.add(bean) ;
			}
			response.setContentType("image/jpg"); //设置返回的文件类型     
		}catch(Exception e){
			response1.setSuccess(false);
			e.printStackTrace();
		}
		response1.setSuccess(true);
		response1.setRetObject("qrcode", codes);
		return response1.returnResult();
	}
	
}
