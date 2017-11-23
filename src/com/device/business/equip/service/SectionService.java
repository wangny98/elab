package com.device.business.equip.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.equip.bean.SectionBean;
import com.device.business.equip.dao.SectionDao;
import com.device.business.equip.service.element.SectionElement;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/SectionService")
public class SectionService {
	@Autowired
	SectionDao	sectionDao;

	@Path("/searchByProject")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("projectId") String projectId,
									 @FormParam("start") int start, @FormParam("limit") int limit){

		RestResponse response = new RestResponse();
		List<SectionBean> sectionList = new ArrayList<SectionBean>();
		int count = 0;

		try{

			SectionElement sectionElement = new SectionElement();
			sectionElement.setProjectId(Integer.valueOf(projectId));

			sectionList = sectionDao.selectByPage(sectionElement, start, limit);

		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setRetObject("sections", sectionList);
		response.setSuccess(true);
		return response.returnResult();
	}

	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@Form SectionElement element,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<SectionBean> list = new ArrayList<SectionBean>();
		int count = 0;
		try{
			list = sectionDao.selectByPage(element, start, limit);
			count = sectionDao.count(element);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("Section", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/searchAll")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchAll(){
		RestResponse response = new RestResponse();
		List<SectionBean> list = new ArrayList<SectionBean>();
		try{
			list = sectionDao.selectByPage(null, 0, 0);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("bean", list);
		return response.returnResult();
	}
	
	@Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> add(@Form SectionBean bean){
		RestResponse response = new RestResponse();
		int count=0;
		try{
			count = sectionDao.add(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/load")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> load(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		SectionBean bean=null;
		try{
			bean = sectionDao.load(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("bean", bean);
		return response.returnResult();
	}
	
	@Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modify(@Form SectionBean bean){
		RestResponse response = new RestResponse();
		int count=0;
		try{
			count = sectionDao.modify(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/remove")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> remove(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		int count=0;
		try{
			count = sectionDao.del(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	
	@Path("/getUserACL")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getUserACL(@FormParam("userId") String userId){
		RestResponse response = new RestResponse();
		List<SectionBean> list = new ArrayList<SectionBean>();
		try{
			list = sectionDao.getUserFunc(userId);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		return response.returnResult();
	}
	
	@Path("/modifyUserACL")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyUserACL(@FormParam("userId") String userId,@FormParam("codes") String codes){
		RestResponse response = new RestResponse();
		int num = 0;
		try{
			num = sectionDao.modifyUserFunc(userId, codes);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("num", num);
		return response.returnResult();
	}
}
