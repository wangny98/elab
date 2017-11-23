package com.device.business.docManager.service;

import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.docManager.bean.ScienceBean;
import com.device.business.docManager.dao.ScienceDocDao;
import com.device.business.docManager.element.ScienceElement;
import com.device.util.RestResponse;

@Path("/scienceManager")
public class ScienceDocService {
    @Autowired
    ScienceDocDao scienceDocDao;

    @POST
    @Path("/load")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> load(@FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        ScienceBean bean = new ScienceBean();
        try {
            bean = scienceDocDao.load(id);
            restResponse.setRetObject("bean", bean);
            if (bean.getName() == null) {
                restResponse.setSuccess(false);
                restResponse.setRetCode(101);//信息不存在
            } else {
                restResponse.setSuccess(true);
            }
        } catch (Exception e) {
            //记录错误日志
            e.printStackTrace();
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    @POST
    @Path("/modify")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> modify(@Form ScienceElement element, @FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        ScienceBean bean = new ScienceBean();
        int result = 0;
        try {
            BeanUtils.copyProperties(element, bean);
            bean.setId(id);
            result = scienceDocDao.modify(bean);
            if (result > 0) {
                restResponse.setSuccess(true);
            } else {
                restResponse.setSuccess(false);
            }
        } catch (Exception e) {
            //记录错误日志
            e.printStackTrace();
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }
}
