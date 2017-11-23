package com.device.business.tracker.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.tracker.dao.UseAuditRepository;
import com.device.business.tracker.element.UseAuditElement;
import com.device.util.RestResponse;

@Path("/useAudit")
public class UseAuditService {
    @Autowired
    UseAuditRepository auditService;

    /**
     * 查询业务审计操作
     * <功能详细描述>
     * @param auditEl 保存查询条件
     * @param start 分页偏移量
     * @param limit 每页大小
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> search(@Form UseAuditElement auditEl, @FormParam("start") int start,
            @FormParam("limit") int limit) {
        RestResponse response = new RestResponse();
        try {
            //执行查询的业务逻辑
            response = auditService.queryAuditLog(auditEl, start, limit);
            //查询成功，向前台返回成功消息
            response.setSuccess(true);
        } catch (Exception e) {
            //查询失败，向前台返回信息加载失败的消息
            response.setSuccess(false);
            e.printStackTrace();
        }
        return response.returnResult();
    }

    /**
     * 获取电子文档的业务操作
     * @param docId 电子文档的主键
     * @param start 分页偏移量
     * @param limit 每页大小
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Path("/getAuditsByDocId")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> queryDocAuditLogs(@FormParam("docId") String docId, @FormParam("start") int start,
            @FormParam("limit") int limit) {
        RestResponse response = new RestResponse();
        try {
            //执行查询的业务逻辑
            response = auditService.queryDocAuditLogs_ZH(docId, start, limit);
            //查询成功，向前台返回成功消息
            response.setSuccess(true);
        } catch (Exception e) {
            //查询失败，向前台返回信息加载失败的消息
            response.setSuccess(false);
            //日志记录
            e.printStackTrace();
        }
        return response.returnResult();
    }

    /**
     * 根据主键获取业务操作
     * @param id 业务操作主键
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Path("/infoAuditOperation")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> infoAuditOperation(@FormParam("id") String id) {
        RestResponse response = new RestResponse();
        try {//执行业务逻辑
            response = auditService.infoAuditOperation(id);
        } catch (Exception e) {
            response.setSuccess(false);
            //日志记录
            e.printStackTrace();
        }
        return response.returnResult();
    }

    /**
     * 导出业务操作
     * @param auditEl
     * @param options 1:导出所有；2：导出查询到的
     * @see [类、类#方法、类#成员]
     */
    @Path("/exportData")
    @POST
    @Produces(MediaType.TEXT_HTML)
    public void exportAuditOperation(@FormParam("exportConditions") String auditEl, @FormParam("options") int options,
            @Context HttpServletResponse response) {
        try {
            //放前面解决excel打开乱码
            response.setContentType("application/csv;charset=gbk");
            response.setHeader("Content-disposition", "attachment;filename=DMUseAuditOperation.csv");
            //调用服务层业务逻辑，执行导出
            auditService.exportAuditOperation(auditEl, options, response);
        } catch (Exception e) {
            //日志记录
            e.printStackTrace();
        }
    }
}
