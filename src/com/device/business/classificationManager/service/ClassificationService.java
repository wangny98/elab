package com.device.business.classificationManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.classificationManager.bean.ClassTreeBean;
import com.device.business.classificationManager.bean.ClassificationBean;
import com.device.business.classificationManager.dao.ClassificationDao;
import com.device.business.classificationManager.element.ClassificationElement;

import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/classificationManager")
public class ClassificationService {
	
	@Autowired
	ClassificationDao classificationDao;

	@Path("/classTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public List<ClassTreeBean> classTree(@QueryParam("id") String versionId) {
		ClassTreeBean root = null;
		try {
            root = classificationDao.getTree(versionId);
        }catch (Exception e) {
        	e.printStackTrace();
        }
		return root.getChildren();
	}
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<ClassificationBean> list = new ArrayList<ClassificationBean>();
		int count = 0;
		
		try{
			list = classificationDao.queryClassification(searchName, start, limit);
			count = classificationDao.countClassification(searchName);
		}catch(Exception e){
			e.printStackTrace();
			response.setRetCode(ReturnCode.SystemException);
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("cla", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SUCCESS);
		return response.returnResult();
	}
	
	@Path("/addClassification")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addClassification(@Form ClassificationElement classificationElement,@FormParam("parent")String parent_id){
		RestResponse response = new RestResponse();
		ClassificationBean bean = new ClassificationBean();
		
		try{
			BeanUtils.copyProperties(bean, classificationElement);
			classificationDao.addClassification(bean,parent_id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		
		return response.returnResult();
	}
	
	@Path("/modifyClassification")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyClassification(@Form ClassificationElement classificationElement){
		RestResponse response = new RestResponse();
		ClassificationBean bean = new ClassificationBean();
		try{
			BeanUtils.copyProperties(bean, classificationElement);
			classificationDao.updClassification(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/loadClassification")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadClassification(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		ClassificationBean bean = new ClassificationBean();
		try{
			bean = classificationDao.loadClassification(id);
			response.setRetObject("cla", bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/deleteClassification")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteClassification(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			classificationDao.deleteClassification(id);
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
