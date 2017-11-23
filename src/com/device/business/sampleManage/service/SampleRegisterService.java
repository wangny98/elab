package com.device.business.sampleManage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.dao.AssetDao;
import com.device.business.login.element.LoginElement;
import com.device.business.roleManage.bean.RoleBean;
import com.device.business.sampleManage.bean.AuthBean;
import com.device.business.sampleManage.bean.BaseSample;
import com.device.business.sampleManage.bean.DemoBean;
import com.device.business.sampleManage.dao.SampleRegisterDao;
import com.device.business.sampleManage.element.AuthElement;
import com.device.business.sampleManage.element.DemoElement;
import com.device.business.sampleManage.element.SampleRegisterElement;
import com.device.component.cache.UserInfoCache;
import com.device.component.cache.UserInfoCatcheDto;
import com.device.constant.ReturnCode;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/sampleRegister")
public class SampleRegisterService {

	@Autowired
	private UserInfoCache userInfoCache;
	
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	SampleRegisterDao sampleRegisterDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("submit_no") String submit_no,
			@FormParam("submit_depart") String submit_depart,@FormParam("submitter") String submitter,
			@FormParam("verify_no") String verify_no,@FormParam("submit_date") String submit_date,
			@FormParam("goods_name") String goods_name,@FormParam("goodtype") String goodtype,
			@FormParam("sample_region") String sample_region,@FormParam("export_import") String export_import,
			@FormParam("verify_type") String verify_type,@FormParam("status") String status,
			@FormParam("verify_identify") String verify_identify,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<BaseSample> list = new ArrayList<BaseSample>();
		int count = 0;
		try{
			BaseSample sample  = new BaseSample();
			if (submit_no != null && !submit_no.equals("")) {
				sample.setSubmit_no("%"+submit_no+"%");
			}
			if (submit_depart != null && !submit_depart.equals("")) {
				sample.setSubmit_depart("%"+submit_depart+"%");
			}
			if (submitter != null && !submitter.equals("")) {
				sample.setSubmitter("%"+submitter+"%");
			}
			if (verify_no != null && !verify_no.equals("")) {
				sample.setVerify_no("%"+verify_no+"%");
			}
			if (submit_date != null && !submit_date.equals("")) {
				sample.setSubmit_date(submit_date);
			}
			if (goods_name != null && !goods_name.equals("")) {
				sample.setGoods_name("%"+goods_name+"%");
			}
			if (goodtype != null && !goodtype.equals("")) {
				sample.setGoods_type(Integer.parseInt(goodtype));
			}
			if (sample_region != null && !sample_region.equals("")) {
				sample.setSample_region("%"+sample_region+"%");
			}
			if (export_import != null && !export_import.equals("")) {
				sample.setExport_import("%"+export_import+"%");
			}
			if (verify_type != null && !verify_type.equals("")) {
				sample.setVerify_type(Integer.parseInt(verify_type));
			}
			if (status != null && !status.equals("")) {
				sample.setStatus(status);
			}
			if (verify_identify != null && !verify_identify.equals("")) {
				sample.setVerify_identify(verify_identify);
			}
			
			LoginElement loginElement =AuthorityContext.getLoginElement();
			String userId = loginElement.getUserId();
			if(userInfoCache.getUserInfo(userId) != null){
             	HashMap<String,Object> map =new HashMap<String,Object>();
             	map.put("sampleBean", sample);
             	map.put("loginElement", loginElement);
             	UserInfoCatcheDto dto = new UserInfoCatcheDto();
             	dto.setUserInfo(map);
             	userInfoCache.addUserInfo(userId, dto);
            }
			servletRequest.getSession().setAttribute("sampleBean", sample);
			list = sampleRegisterDao.querySampleRegister(sample, start, limit);
			count = sampleRegisterDao.countsampleRegister(sample);
			for (int i = 0; i < list.size(); i++) {
				BaseSample baseSample = list.get(i);
				DictionaryBean dictionaryBean = new DictionaryBean();
				dictionaryBean.setAttr_name("货物类型");
				dictionaryBean.setAttr_key(String.valueOf(baseSample.getGoods_type()));
				String tString = assetDao.getComboValue(dictionaryBean);
				baseSample.setGoods_typeText(tString);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("sample", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/registSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registSample(@Form SampleRegisterElement sampleRegisterElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			sampleRegisterElement.setSubmit_date(format.format(new Date()));
			sampleRegisterElement.setId(PrimaryKeyUtil.getSeq());
			sampleRegisterElement.setSubmit_no(this.getSubmitNo(sampleRegisterElement.getSubmit_date()));
			int pk = sampleRegisterDao.insertsampleRegister(sampleRegisterElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	private String  getSubmitNo(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
		int count = sampleRegisterDao.countSamplesByDate(date);
		count++;
		String countString = Integer.toString(count);
		for (;countString.length() < 4;) {
			countString  = "0" + countString;
		}
		String result = format.format(new Date()) + countString; 
		
		return result;
	}
	
	@Path("/updateSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> updateSample(@Form SampleRegisterElement sampleRegisterElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			int pk = sampleRegisterDao.updateSample(sampleRegisterElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/deleteSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteSample(@FormParam("ids") String ids,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		try{
			if (sampleRegisterDao.deleteSample(ids) == 0 || 
					sampleRegisterDao.deleteDemoBySampleID(ids) == 0) {
				response.setSuccess(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/submitSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> submitSample(@FormParam("ids") String ids,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		try{
			if (sampleRegisterDao.checkSampleSubmit(ids) == 0) {
				response.setSuccess(false);
			}else{
				if (sampleRegisterDao.submitSample(ids) == 0) {
					response.setSuccess(false);
				}else{
					response.setSuccess(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		return response.returnResult();
	}
	/////////////////小样处理函数//////////////////////////////////////////
	
	@Path("/getDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getDemo(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<DemoBean> list = new ArrayList<DemoBean>();
		try{
			DemoBean demoBean = new DemoBean();
			demoBean.setId(id);
			list = sampleRegisterDao.queryDemo(demoBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		return response.returnResult();
	}
	
	@Path("/getDemoForAuthResult")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getDemoForAuthResult(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<DemoBean> list = new ArrayList<DemoBean>();
		try{
			DemoBean demoBean = new DemoBean();
			demoBean.setId(id);
			list = sampleRegisterDao.getDemoForAuthResult(demoBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		return response.returnResult();
	}
	
	@Path("/searchDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchDemo(
			@FormParam("id") String id,@FormParam("status") String status,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<DemoBean> list = new ArrayList<DemoBean>();
		int count = 0;
		try{
			DemoBean demoBean = new DemoBean();
			demoBean.setId(id);
			demoBean.setStatus(status);
			list = sampleRegisterDao.queryDemo(demoBean, start, limit);
			count = sampleRegisterDao.countDemo(demoBean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/searchDemo2")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchDemo2(
			@FormParam("id") String id,@FormParam("status") String status,
			@FormParam("no") String no,@FormParam("demo_name") String demo_name,
			@FormParam("demo_status") String demo_status,
			@FormParam("submit_no") String submit_no,
			@FormParam("submit_depart") String submit_depart,@FormParam("submitter") String submitter,
			@FormParam("verify_no") String verify_no,@FormParam("submit_date") String submit_date,
			@FormParam("goods_name") String goods_name,@FormParam("goodtype") String goodtype,
			@FormParam("sample_region") String sample_region,@FormParam("export_import") String export_import,
			@FormParam("verify_type") String verify_type,
			@FormParam("start") int start, @FormParam("limit") int limit,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		List<DemoBean> list = new ArrayList<DemoBean>();
		int count = 0;
		try{
			BaseSample baseSample = new BaseSample();
			if (id != null && !id.equals("")) {
				baseSample.setId(id);
			}
			if (no != null && !no.equals("")) {
				baseSample.setNo("%"+no+"%");
			}
			if (demo_name != null && !demo_name.equals("")) {
				baseSample.setDemo_name("%"+demo_name+"%");
			}
			if (demo_status != null && !demo_status.equals("")) {
				baseSample.setDemo_status(demo_status);
			}
			if (submit_depart != null && !submit_depart.equals("")) {
				baseSample.setSubmit_depart("%"+submit_depart+"%");
			}
			if (submitter != null && !submitter.equals("")) {
				baseSample.setSubmitter("%"+submitter+"%");
			}
			if (verify_no != null && !verify_no.equals("")) {
				baseSample.setVerify_no("%"+verify_no+"%");
			}
			if (submit_date != null && !submit_date.equals("")) {
				baseSample.setSubmit_date(submit_date);
			}
			if (goods_name != null && !goods_name.equals("")) {
				baseSample.setGoods_name("%"+goods_name+"%");
			}
			if (goodtype != null && !goodtype.equals("")) {
				baseSample.setGoods_type(Integer.parseInt(goodtype));
			}
			if (sample_region != null && !sample_region.equals("")) {
				baseSample.setSample_region("%"+sample_region+"%");
			}
			if (export_import != null && !export_import.equals("")) {
				baseSample.setExport_import("%"+export_import+"%");
			}
			if (verify_type != null && !verify_type.equals("")) {
				baseSample.setVerify_type(Integer.parseInt(verify_type));
			}
			if (status != null && !status.equals("")) {
				baseSample.setStatus(status);
			}
			list = sampleRegisterDao.queryDemo2(baseSample, start, limit);
			count = sampleRegisterDao.countDemo2(baseSample);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/registDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registDemo(@Form DemoElement demoElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			demoElement.setId(PrimaryKeyUtil.getSeq());
			int pk = sampleRegisterDao.insertDemo(demoElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/updateDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> updateDemo(@Form DemoElement demoElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.updateDemo(demoElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/deleteDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteDemo(@Form DemoElement demoElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.deleteDemo(demoElement.getId());
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	/////////////////////实验室评审//////////////////////////////////////
	
	@Path("/rejectSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> rejectSample(@Form SampleRegisterElement sampleRegisterElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.rejectSample(sampleRegisterElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/assignDemo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> assignDemo(@Form DemoElement demoElement, @Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.assignDemo(demoElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/acceptSample")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> acceptSample(@FormParam("id") String id,
			@Context HttpServletRequest request,@Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LoginElement loginElement =AuthorityContext.getLoginElement();
		try{
			SampleRegisterElement sampleRegisterElement = new SampleRegisterElement();
			sampleRegisterElement.setId(id);
			sampleRegisterElement.setStatus("3");
			sampleRegisterElement.setAcceptdate(format.format(new Date()));
			sampleRegisterElement.setAcceptor(loginElement.getUserFullName());
			
			int pk = sampleRegisterDao.acceptSample(sampleRegisterElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	////////////////////鉴定///////////////////////////////////////
	
	@Path("/registerAuth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registerAuth(@Form AuthElement authElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			authElement.setId(PrimaryKeyUtil.getSeq());
			authElement.setAuthdate(format.format(new Date()));
			int pk = sampleRegisterDao.insertAuth(authElement);
			DemoElement demoElement = new DemoElement();
			demoElement.setId(authElement.getDemo_id());
			demoElement.setStatus("1");
			int r = sampleRegisterDao.updateDemoStatus(demoElement);
			if (pk != 1 || r != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/updateAuth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> updateAuth(@Form AuthElement authElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.updateAuth(authElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/loadAuth")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadAuth(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
        try {
            AuthBean bean = sampleRegisterDao.loadAuth(id);
            response.setRetObject("auth", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
	}
	
	///////////////////////复核////////////////////////////////////
	
	@Path("/registerVerify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registerVerify(@Form AuthElement authElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			authElement.setVerifydate(format.format(new Date()));
			int pk = sampleRegisterDao.insertVerify(authElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}else{
				DemoElement demoElement = new DemoElement();
				demoElement.setId(authElement.getId());
				demoElement.setStatus("2");
				sampleRegisterDao.updateDemoStatus(demoElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/loadVerify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadVerify(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
        try {
        	AuthBean authBean = new AuthBean();
        	authBean.setId(id);
            AuthBean bean = sampleRegisterDao.loadVerify(authBean);
            bean.setId(id);
            response.setRetObject("auth", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
	}
	
	///////////////////////签发////////////////////////////////////
	
	@Path("/registerSign")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registerSign(@Form SampleRegisterElement sampleRegisterElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			sampleRegisterElement.setSigndate(format.format(new Date()));
			int pk = sampleRegisterDao.insertSign(sampleRegisterElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}else{
				//更新sample状态
				SampleRegisterElement sampleRegisterElement2 = new SampleRegisterElement();
				sampleRegisterElement2.setId(sampleRegisterElement.getId());
				sampleRegisterElement2.setStatus("7");
				sampleRegisterDao.updateSampleStatus(sampleRegisterElement2);
				//更新demo状态
				DemoElement demoElement = new DemoElement();
				demoElement.setId(sampleRegisterElement.getId());
				demoElement.setStatus("3");
				sampleRegisterDao.updateDemoStatusByBasicID(demoElement);
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/loadSign")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadSign(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
        try {
            BaseSample bean = sampleRegisterDao.loadSign(id);
            bean.setId(id);
            response.setRetObject("sample", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
	}
	
	///////////////样品处理////////////////////////////////////////////
	
	@Path("/registerDestroy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registerDestroy(@Form DemoElement demoElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.registerDestroy(demoElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/loadDestroy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadDestroy(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
        try {
            DemoBean bean = sampleRegisterDao.loadDestroy(id);
            bean.setId(id);
            response.setRetObject("small", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
	}
	
	///////////////////////////////////////////////////////////
	
	@Path("/registerReserve")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> registerReserve(@Form DemoElement demoElement, 
			@Context HttpServletRequest request,
            @Context HttpServletResponse response){
		RestResponse response1 = new RestResponse();
		try{
			int pk = sampleRegisterDao.registerReserve(demoElement);
			if (pk != 1) {
				response1.setSuccess(false);
				return response1.returnResult();
			}
		}catch(Exception e){
			e.printStackTrace();
			response1.setSuccess(false);
			return response1.returnResult();
		}
		response1.setSuccess(true);
		return response1.returnResult();
	}
	
	@Path("/loadReserve")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadReserve(
			@FormParam("id") String id,
			@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
        try {
            DemoBean bean = sampleRegisterDao.loadReserve(id);
            bean.setId(id);
            response.setRetObject("small", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
	}
	
	///////////////////////////////////////////////////////////
	
	@Path("/getType")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> getType(@FormParam(value = "type") String type){
		RestResponse response = new RestResponse();
		
		List<DictionaryBean> list = new ArrayList<DictionaryBean>();
		try{
			list = sampleRegisterDao.queryDictionary(type);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		
		response.setSuccess(true);
		response.setRetObject("list", list);
		
		return response.returnResult();
	}
	
}
