package com.device.business.docManager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.docManager.bean.ContentNodeBean;
import com.device.business.docManager.dao.ContentManagerDao;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;

/**
 * 目录模板管理
 * <功能详细描述>
 * 
 * @author  周晶
 * @version  [版本号, 2013-9-6]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Path("/contentManager")
public class ContentManagerService {

    @Autowired
    private ContentManagerDao contentManagerRepository;

    /**}
     * 模板树
     * <功能详细描述>
     * @param typeId
     * @param uiProvider
     * @return
     * @see [类、类#方法、类#成员]
     */
    @POST
    @Path("/loadTree")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContentNodeBean> loadContentTree(@FormParam("typeIds") String typeIds,
            @FormParam("uiProvider") String uiProvider) {
        List<ContentNodeBean> nodes = new ArrayList<ContentNodeBean>();
        try {
            nodes = contentManagerRepository.getTree(typeIds, uiProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    /**
     * 添加树节点
     * <功能详细描述>
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    @POST
    @Path("/addNode")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> addNode(@Form ContentNodeBean bean) {
        RestResponse restResponse = new RestResponse();
        int result = 0;
        try {
            result = contentManagerRepository.addContentNode(bean);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(ReturnCode.ADD_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }
        restResponse.setRetCode(ReturnCode.SUCCESS);
        if (result > 0) {
            restResponse.setSuccess(true);
        } else {
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 修改树节点
     * <功能详细描述>
     * @param bean
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @POST
    @Path("/modifyNode")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> modifyNode(@Form ContentNodeBean bean, @FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        try {
            bean.setId(id);
            contentManagerRepository.updContentNode(bean);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(ReturnCode.UPDATE_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }
        restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }

    /**
     * 删除树节点
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @POST
    @Path("/deleteNode")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteNode(@FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        int result = 0;
        try {
            result = contentManagerRepository.delContentNode(id);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(ReturnCode.DELETE_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }
        restResponse.setRetCode(ReturnCode.SUCCESS);

        if (result > 0) {
            restResponse.setSuccess(true);
        } else {
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    /**
     * 获取树节点
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    @POST
    @Path("/loadNode")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> loadNode(@FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        ContentNodeBean bean = new ContentNodeBean();
        try {
            bean = contentManagerRepository.infoContentNode(id);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(ReturnCode.DELETE_ERROR);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }
        restResponse.setRetCode(ReturnCode.SUCCESS);
        restResponse.setRetObject("bean", bean);
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }
}
