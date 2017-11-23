package com.device.business.dictionary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.RowBounds;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;


import com.agile.erms.utils.RestResponse;
import com.device.business.dictionary.bean.DictionaryBean;
import com.device.business.dictionary.dao.DictionaryDao;
import com.device.business.dictionary.element.DictionaryElement;
import com.device.constant.ReturnCode;
import com.sun.org.apache.commons.beanutils.BeanUtils;


@Path("/dictionaryManage")
public class DictionaryService {
	/**
     * 注入数据字典操作
     */
    @Autowired
    DictionaryDao dictionaryDao;


    /**
     * 列出所有数据字典信息
     * @param request
     * @return
     */
    @POST
    @Path("/listAllDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> listAllDictionary(@Context HttpServletRequest request) {

        RestResponse restResponse = new RestResponse();
        List<DictionaryBean> dictionaryBeanList = null;
        //List<DictionaryBean> getListSize = null;
        //初始化未分页的查询个数
        int dictionarySize=0;
        try {
            int start = Integer.parseInt(request.getParameter("start"));
            int limit = Integer.parseInt(request.getParameter("limit"));
            String queryName = request.getParameter("searchDictionary");
            //分页相关
            RowBounds rows = new RowBounds(start, limit);
            dictionaryBeanList = dictionaryDao.queryDictionary(queryName, rows);
            //不带分页查询出总个数
            dictionarySize = dictionaryDao.queryDictionaryCount(queryName);
            //dictionarySize = getListSize.size();
        } catch (Exception e) {
            //restResponse.setRetCode(ReturnCode.LOAD_ERROR);
        	e.printStackTrace();
            restResponse.setSuccess(false);

            return restResponse.returnResult();
        }

        restResponse.setRetObject("dictionaryList", dictionaryBeanList);
        restResponse.setRetObject("totalCount", dictionarySize);
        //restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }

    /**
     * 列出所有数据字典信息
     * @param request
     * @return
     */
    @POST
    @Path("/listDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> listDictionary() {

        RestResponse restResponse = new RestResponse();
        List<DictionaryBean> dictionaryBeanList = null;
        try {
            //dictionaryBeanList = dictionaryRepository.queryAllDictionary();
        } catch (Exception e) {
            //restResponse.setRetCode(ReturnCode.LOAD_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        restResponse.setRetObject("dictionaryList", dictionaryBeanList);
        //restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setSuccess(true);

        return restResponse.returnResult();
    }

    /**
     * 
     * 数据字典内部小面版的详细信息
     * @param request
     * @return
     */
    @POST
    @Path("/listDetailDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> listDetailDictionary(@Context HttpServletRequest request) {
        String attributeDic = request.getParameter("attributeDic");
        String attributeNameDic = request.getParameter("attributeNameDic");
        RestResponse restResponse = new RestResponse();
        List<DictionaryBean> dictionaryBeanList = null;
        try {
            dictionaryBeanList = dictionaryDao.queryDetailDictionary(attributeDic, attributeNameDic);
        } catch (Exception e) {
            //restResponse.setRetCode(ReturnCode.LOAD_ERROR);
        	e.printStackTrace();
            restResponse.setSuccess(false);
            //记录错误日志
            return restResponse.returnResult();
        }

        restResponse.setRetObject("dictionaryDetailElementList", dictionaryBeanList);
        //restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }

    /**
     * 新增
     * @param jobElement
     * @return
     */
    @POST
    @Path("/addDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> addDictionary(@Form DictionaryElement dictionaryElement) {
        RestResponse restResponse = new RestResponse();
        // 前台参数检查
       
        // 是否新增成功的标志
        Boolean success = false;
        int dictionaryKeyNumber;
        int dictionaryValueNumber;
        try {

            DictionaryBean dictionaryBean = new DictionaryBean();
            BeanUtils.copyProperties(dictionaryBean, dictionaryElement);
            String attribute = dictionaryBean.getAttr_name();
            String attributeKey = dictionaryBean.getAttr_key();
            String attributeValue = dictionaryBean.getAttr_value();
            //String description = dictionaryBean.getDescription();
            dictionaryKeyNumber = dictionaryDao.checkDictionaryByKey(attribute, attributeKey);
            dictionaryValueNumber = dictionaryDao.checkDictionaryByValue(attribute, attributeValue);
            
            
            if (dictionaryKeyNumber != 0) {
                restResponse.setRetCode(ReturnCode.REPEAT_ERROR);
                restResponse.setSuccess(true);
                return restResponse.returnResult();

            }else if(dictionaryValueNumber != 0){
            	restResponse.setRetCode(ReturnCode.THEREPEAT_ERROR);
                restResponse.setSuccess(true);
                return restResponse.returnResult();
            }else {
                success = dictionaryDao.addDictionary(dictionaryBean);
            }

        } catch (Exception e) {
            //restResponse.setRetCode(ReturnCode.ADD_ERROR);
        	e.printStackTrace();
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        if (success) {
            //restResponse.setRetCode(ReturnCode.SUCCESS);
            restResponse.setSuccess(true);

        } else {
            //restResponse.setRetCode(ReturnCode.ADD_ERROR);
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 查看
     * @param displayJobId
     * @return
     */
    @POST
    @Path("/infoDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> infoDictionary(@QueryParam("displayDictionaryId") String displayDictionaryId) {

        RestResponse restResponse = new RestResponse();
        DictionaryBean dictionaryBean = null;

        try {
            dictionaryBean = dictionaryDao.displayDictionary(displayDictionaryId);

        } catch (Exception e) {
        	e.printStackTrace();
            restResponse.setRetCode(ReturnCode.LOAD_ERROR);
            restResponse.setSuccess(false);

           return restResponse.returnResult();
        }
        if (null != dictionaryBean) {
            restResponse.setRetCode(ReturnCode.SUCCESS);
            restResponse.setSuccess(true);
            restResponse.setRetObject("dictionary", dictionaryBean);

        } else {
            restResponse.setRetCode(ReturnCode.LOAD_ERROR);
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 修改
     * @param roleElement
     * @return
     */
    @POST
    @Path("/editDictionary")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> editDictionary(@Form DictionaryElement dicElement, @QueryParam("id") String id,
            @FormParam("dicAtt") String dicAtt, @FormParam("dicAttValue") String dicAttValue,
            @FormParam("dicDescription") String dicDescription) {
        RestResponse restResponse = new RestResponse();
        //前台参数检测
        
        // 是否新增成功的标志
        Boolean success = true;
        int dictionaryKeyNumber=0;
        int dictionaryValueNumber=0;
        try {

            DictionaryBean dictionaryBean = new DictionaryBean();
            BeanUtils.copyProperties(dictionaryBean, dicElement);
            dictionaryBean.setId(id);

            
            dictionaryKeyNumber = dictionaryDao.checkDictionaryByKey(dicElement.getAttr_name(), dicElement.getAttr_key());
            
            dictionaryValueNumber = dictionaryDao.checkDictionaryByValue(dicElement.getAttr_name(), dicElement.getAttr_value());
           /* boolean booleanDicDescription = uptateDicDescription.equals(dictionaryBean.);
            boolean booleanDicAttribute = uptateDictionaryAttribute.equals(dictionaryBean.getAttribute());
            boolean booleanAttributeValue = updateDictionaryValue == dictionaryBean.getAttribute_value();

            if ((!(booleanDicAttribute && booleanAttributeValue)) && (dictionaryNumber != 0)) {
                restResponse.setRetCode(ReturnCode.REPEAT_ERROR);
                restResponse.setSuccess(true);
                return restResponse.returnResult();

            } else if ((!(booleanDicAttribute)) ) {
                restResponse.setRetCode(ReturnCode.THEREPEAT_ERROR);
                restResponse.setSuccess(true);
                return restResponse.returnResult();

            } else {
                success = dictionaryDao.updateDictionary(dictionaryBean);
            }*/
            
            success = dictionaryDao.updateDictionary(dictionaryBean);
        } catch (Exception e) {
        	e.printStackTrace();
            restResponse.setRetCode(ReturnCode.UPDATE_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        if (success) {
            restResponse.setRetCode(ReturnCode.SUCCESS);
            restResponse.setSuccess(true);

        } else {
            restResponse.setRetCode(ReturnCode.UPDATE_ERROR);
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 删除
     * @param deleteIds
     * @return 
     */
    @Path("/deleteDictionary")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteDictionary(@QueryParam("deleteIds") String deleteIds) {
        RestResponse restResponse = new RestResponse();

        // 是否新增成功的标志
        Boolean success = false;
        try {
            success = dictionaryDao.deleteDictionary(deleteIds);

        } catch (Exception e) {
        	e.printStackTrace();
            restResponse.setRetCode(ReturnCode.DELETE_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        if (success) {
            restResponse.setRetCode(ReturnCode.SUCCESS);
            restResponse.setSuccess(true);

        } else {
            restResponse.setRetCode(ReturnCode.DELETE_ERROR);
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 
     * 数据字典缓存公用方法
     * 通过属性获取对应的数据字典
     * 
     * @param attribute
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Path("/getKV")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDictionaryKV(@QueryParam("attribute") String attribute) {

        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
        try{
        	dictionaryBeanList = dictionaryDao.getDictionaryBeanList(attribute);
        }catch(Exception e){
        	e.printStackTrace();
        }
        restResponse.setRetObject("dictionaryBeanList", dictionaryBeanList);
        return restResponse.returnResult();
    }
    
    @Path("/getKV")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getDictionaryKV1(@QueryParam("attribute") String attribute) {

        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        List<DictionaryBean> dictionaryBeanList = new ArrayList<DictionaryBean>();
        try{
        	dictionaryBeanList = dictionaryDao.getDictionaryBeanList(attribute);
        }catch(Exception e){
        	e.printStackTrace();
        }
        restResponse.setRetObject("dictionaryBeanList", dictionaryBeanList);
        return restResponse.returnResult();
    }

    /**
     * 数据字典根据属性填写属性名
     * @param request
     * @return
     */
    @POST
    @Path("/queryAttributeName")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> queryAttributeName(@Context HttpServletRequest request) {

        String attribute = request.getParameter("attribute");
        RestResponse restResponse = new RestResponse();
        List<DictionaryBean> dictionaryBeanList = null;
        try {
            dictionaryBeanList = dictionaryDao.getDictionaryBeanList(attribute);

        } catch (Exception e) {
        	e.printStackTrace();
            restResponse.setRetCode(ReturnCode.LOAD_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        restResponse.setRetObject("queryAttributeNameElementList", dictionaryBeanList);

        if (!dictionaryBeanList.isEmpty()) {
            restResponse.setRetCode(ReturnCode.SUCCESS);
            restResponse.setSuccess(true);
            return restResponse.returnResult();

        } else {
            restResponse.setSuccess(false);
            return restResponse.returnResult();

        }
    }

    /**
     * 列出所有数据字典信息
     * @param request
     * @return
     */
    @POST
    @Path("/listDictionaryAttribute")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> listDictionaryAttribute() {
        RestResponse restResponse = new RestResponse();
        List<DictionaryBean> dictionaryBeanList = null;
        //初始化未分页的查询个数

        try {
            dictionaryBeanList = dictionaryDao.listDictionaryAttribute();

        } catch (Exception e) {
        	e.printStackTrace();
            restResponse.setRetCode(ReturnCode.LOAD_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }

        restResponse.setRetObject("dictionaryAttributeList", dictionaryBeanList);
        restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }
}
