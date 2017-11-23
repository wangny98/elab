package com.device.business.docManager.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.docManager.bean.FileBean;
import com.device.business.docManager.bean.ScienceBean;
import com.device.business.docManager.dao.FileDao;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;

@Path("/fileManager")
public class FileManagerService {

    @Autowired
    FileDao fileDao;

    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> search(@FormParam("searchName") String searchName, @FormParam("types") String types,
            @FormParam("start") int start, @FormParam("limit") int limit, @FormParam("nodeId") String nodeId) {
        RestResponse response = new RestResponse();
        List<FileBean> list = new ArrayList<FileBean>();
        int count = 0;

        try {
            list = fileDao.listFile(searchName, types, start, limit, nodeId);
            count = fileDao.countFile(searchName, types, nodeId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setRetCode(ReturnCode.SystemException);
            response.setSuccess(false);
            return response.returnResult();
        }

        response.setSuccess(true);
        response.setRetObject("fileList", list);
        response.setRetObject("total", count);
        response.setRetCode(ReturnCode.SUCCESS);
        return response.returnResult();
    }

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
        try {
            String name = request.getParameter("name");
            String author_1 = request.getParameter("author_1");
            String author_2 = request.getParameter("author_2");
            String author_3 = request.getParameter("author_3");
            String org_name = request.getParameter("org_name");
            String publication = request.getParameter("publication");
            String publication_timeStr = request.getParameter("publication_time");

            String key_word = request.getParameter("key_word");
            ScienceBean bean = new ScienceBean();
            bean.setName(name);
            bean.setAuthor_1(author_1);
            bean.setAuthor_2(author_2);
            bean.setAuthor_3(author_3);
            bean.setOrg_name(org_name);
            bean.setPublication(publication);
            bean.setKey_word(key_word);

            if (!CommUtils.isNullOrBlank(publication_timeStr)) {
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date publication_time = format1.parse(publication_timeStr);
                bean.setPublication_time(publication_time);
            }
            //System.out.println("bean : " + bean);
            returnValue = fileDao.importDoc(request, bean);
        } catch (Exception e) {
            returnValue = "{success:false}";
            //记录错误日志
            e.printStackTrace();
        }

    	System.out.println("returnValue : "+returnValue);
        return returnValue;
    }

    /**
     * 下载文档
     * @param request
     * @param response
     * @param fileId
     * @return
     */
    @GET
    @Path("/exportFile")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> exportFile(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @QueryParam("fileId") String fileId) {
        RestResponse restResponse = new RestResponse();
        int result = 0;
        try {
            result = fileDao.exportFile(fileId, response,request);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(result);
            restResponse.setSuccess(false);
        }
        if (result == 100) {
            return null;
        } else {
            restResponse.setRetCode(result);
            restResponse.setSuccess(false);
            return restResponse.returnResult();
        }
    }

    /**
     * 电子文档预览
     * 
     * @param docId 电子文档主键
     * @param response Http返回
     * @see [类、类#方法、类#成员]
     */
    @Path("/docPreview")
    @POST
    @Produces("text/json;charset=utf-8")
    public Map<String, Object> docPreview(@FormParam("docId") String docId, @Context HttpServletResponse response,
            @Context HttpServletRequest request) {
        RestResponse restResponse = new RestResponse();
        try {
            //OutputStream out = response.getOutputStream();
            restResponse = fileDao.docPreview(docId);
        } catch (Exception e) {
            //记录错误日志
            e.printStackTrace();
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    @Path("/deleteDoc")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> exportFile(@FormParam("id") String id) {
        RestResponse restResponse = new RestResponse();
        int result = 0;
        try {
            result = fileDao.deleteDoc(id);
        } catch (Exception e) {
            e.printStackTrace();
            restResponse.setRetCode(result);
            restResponse.setSuccess(false);
        }
        if (result > 0) {
            restResponse.setSuccess(true);
        } else {
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }
}
