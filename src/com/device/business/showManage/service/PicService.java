package com.device.business.showManage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.showManage.bean.PicBean;
import com.device.business.showManage.dao.PicDao;
import com.device.business.showManage.element.PicElement;
import com.device.business.showManage.element.ScienceShowElement;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/picManage")
public class PicService {
	@Autowired
	PicDao picDao;
	
	/**
     * 批量上传文档
     * @param request Http请求
     * @return
     */
    @Path("/uploadFiles")
    @POST
    @Produces("text/html; charset=utf-8")
    public String uploadFiles(@Context HttpServletRequest request) {
    	String returnValue = null;
    	String show_id = request.getParameter("show_id");
    	String pic_descript = request.getParameter("pic_descript");
    	try{
    		returnValue = picDao.importDoc(request, show_id,pic_descript);
    	}catch(Exception e){
    		returnValue = "{success:false}";
    		e.printStackTrace();
    	}
    	System.out.println("returnValue : "+returnValue);
    	return returnValue;
    }
	
	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		int count = 0;
		List<PicBean> list = new ArrayList<PicBean>();
		try{
			list = picDao.query(searchName, start, limit);
			count = picDao.count(searchName);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SystemException);
		return response.returnResult();
	}
	
	@Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> add(@Form PicElement element,@FormParam("property_ids")String property_ids){
		RestResponse response = new RestResponse();
		PicBean bean = new PicBean();
		
		try{
			BeanUtils.copyProperties(bean, element);
			picDao.add(bean);
			response.setSuccess(true);
		}catch(Exception e){
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
	public Map<String,Object> load(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			PicBean bean  = picDao.load(id);
			response.setRetObject("bean", bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modify(@Form ScienceShowElement element,@FormParam("id")String id){
		RestResponse response = new RestResponse();
		PicBean bean = new PicBean();
		bean.setId(id);
		try{
			BeanUtils.copyProperties(bean, element);
			picDao.update(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> delete(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		
		try{
			picDao.delete(id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
}
