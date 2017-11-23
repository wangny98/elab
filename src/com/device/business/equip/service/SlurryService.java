package com.device.business.equip.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.SlurryAmountBean;
import com.device.business.equip.bean.SlurryPerdayBean;
import com.device.business.equip.dao.SlurryDao;
import com.device.business.equip.service.element.SlurryElement;
import com.device.core.util.CommUtils;
import com.device.util.RestResponse;
import com.device.util.TimeUtil;

@Path("/SlurryService")
public class SlurryService {
	@Autowired
	SlurryDao slurryDao;
	
	@Path("/querySectionAmount")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> querySectionAmount(@Form SlurryElement element){
		RestResponse response = new RestResponse();
		List<SlurryAmountBean> list = new ArrayList<SlurryAmountBean>();
		try{
			list = slurryDao.listSectionAmount(element);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("sectionAmount", list);
		return response.returnResult();
	}
	
	@Path("/queryProjectAmount")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> queryProjectAmount(@Form SlurryElement element){
		RestResponse response = new RestResponse();
		List<SlurryAmountBean> list = new ArrayList<SlurryAmountBean>();
		try{
			list = slurryDao.listProjectAmount(element);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("projectAmount", list);
		return response.returnResult();
	}
	
	@Path("/querySectionPerday")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> querySectionPerday(@Form SlurryElement element){
		RestResponse response = new RestResponse();
		List<SlurryPerdayBean> list = new ArrayList<SlurryPerdayBean>();
		
		if(CommUtils.isNullOrBlank(element.getDateAmount())){
			element.setDateAmount(new Date());
		}
		/*List<Date> dataList = new ArrayList<Date>();
		dataList.add(end);
		for(int i=-1;i>-31;i--){
			dataList.add(TimeUtil.addDay(i));
		}*/
		
		try{
			//list = slurryDao.listPerday(element);
			list = slurryDao.selectSlurry(element);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("data", list);
		return response.returnResult();
	}
}
