package com.device.business.tracker.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.jboss.resteasy.spi.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVWriter;

import com.device.business.login.element.LoginElement;
import com.device.business.tracker.bean.UseAuditBean;
import com.device.business.tracker.dao.mapper.UseAuditMapper;
import com.device.business.tracker.element.UseAuditElement;
import com.device.business.userManage.dao.mapper.UserMapper;
import com.device.core.util.CommUtils;
import com.device.core.util.JsonUtils;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

/**
 * 
 * 记录业务操作的服务类
 * 
 * @author  guoke
 * @version  [版本号, 2013-8-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UseAuditRepository {
    /**
     * 调用业务操作的数据访问层
     */
    @Autowired
    UseAuditMapper auditDao;
    /**
     * 记录当前登录状态
     */
    LoginElement loginElement;
    /**
     * 注入用户操作类
     */
    @Autowired
    UserMapper userDao;

    /**
       * 若业务过程中的元数据发生变化，需要跟踪，则调用此方法
       * @param operateSubject 操作主题
       * @param source 原来的bean
       * @param dest 带插入数据库的bean
       * @return 添加业务操作审计是否成功的标志
       * @see [类、类#方法、类#成员]
       */
    public boolean addAuditLog(String operateSubject_ZH, String operateSubject_EN, Object source, Object dest) {
        //保存业务操作的主要信息，如操作主题、操作内容等。
        UseAuditBean auditBean = new UseAuditBean();
        //设置操作主题
        auditBean.setOperateSubject_EN(operateSubject_EN);
        auditBean.setOperateSubject_ZH(operateSubject_ZH);
        try {
            //设置操作内容
            setOperateContent(auditBean, source, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //插入数据库
        return addBean(auditBean);
    }

    /**
     * 记录业务操作中元数据的变化
     * @param auditBean 记录业务操作
     * @param source  原来的bean
     * @param dest 带插入数据库的bean
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see [类、类#方法、类#成员]
     */
    private void setOperateContent(UseAuditBean auditBean, Object source, Object dest) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        loginElement = AuthorityContext.getLoginElement();
        //若原来的bean和待插入的bean不属于同一类型，则抛出异常
        if (source.getClass() != dest.getClass()) {
            throw new IllegalArgumentException();
        }
        //保存元数据变化信息，格式为：属性名：原来的属性->变化后的属性
        StringBuffer operateContent = new StringBuffer();
        StringBuffer operateContent_ZH = new StringBuffer();
        StringBuffer operateContent_EN = new StringBuffer();
        String sourceFieldValue;
        String destFieldValue;
        //保存元数据变化的中文信息
        operateContent_ZH.append("元数据已经发生如下变化：<br/>");
        //保存元数据变化的英文信息
        operateContent_EN.append("DataMeta has changed as below：<br/>");
        //反射获取待记录业务操作的bean的属性
        Iterator<String> fields = obtainGetMethod(source.getClass()).keySet().iterator();
        //遍历反射获取的bean属性
        for (; fields.hasNext();) {
            //属性
            String field = fields.next();
            //反射获取的方法
            Method getMethod = (Method) obtainGetMethod(source.getClass()).get(field);
            //反射执行get方法，获取原来的属性值
            sourceFieldValue = (String) getMethod.invoke(source);
            //反射执行get方法，获取变化后的属性值
            destFieldValue = (String) getMethod.invoke(dest);
            //若sourceFieldValue和destFieldValue不同，则记录到业务操作内容中
            if (!sourceFieldValue.equals(destFieldValue)) {
                operateContent.append(field + ":" + sourceFieldValue + "->" + destFieldValue + "<br/>");
            }
        }
        //设置元数据变化的中文信息
        auditBean.setOperateContent_ZH(operateContent_ZH.append(operateContent).toString());
        //设置元数据变化的英文信息
        auditBean.setOperateContent_EN(operateContent_EN.append(operateContent).toString());
    }

    /**
     * 反射获取字节码对应的get方法
     * @param clazz 查询要返回的bean对应的字节码
     * @return 字节码对应的get方法和对应的属性名称
     * @see [类、类#方法、类#成员]
     */
    protected Map<String, Object> obtainGetMethod(Class<?> clazz) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
            String fieldName = clazz.getDeclaredFields()[i].getName();
            //不获取get方法的属性
            if ("serialVersionUID".equalsIgnoreCase(fieldName) || ("id").equalsIgnoreCase(fieldName)
                    || ("deleteflag").equalsIgnoreCase(fieldName) || ("state").equalsIgnoreCase(fieldName)) {
                continue;
            }
            //将field的首字母变成大写，重新赋值
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            try {
                //根据方法名称和参数反射获取对应的get方法
                map.put(fieldName, clazz.getMethod("get" + fieldName));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    /**
     * 添加业务跟踪操作
     * <功能详细描述>
     * @param operateSubject 操作主题
     * @param operateContent 操作内容
     * @return
     * @see [类、类#方法、类#成员]
     */
    /*public boolean addAuditLog(String operateSubject_ZH, String operateSubject_EN, String operateContent_ZH,
            String operateContent_EN, String docId) {
        RestResponse response = new RestResponse();
        response.setSuccess(true);
        UseAuditBean auditBean = new UseAuditBean();
        //设置操作主题
        auditBean.setOperateSubject_EN(operateSubject_EN);
        auditBean.setOperateSubject_ZH(operateSubject_ZH);
        //设置操作内容
        auditBean.setOperateContent_ZH(operateContent_ZH);
        auditBean.setOperateContent_EN(operateContent_EN);
        if (!CommUtils.isNullOrBlank(docId)) {
            auditBean.setDocId(docId);
        }
        //添加到数据库        
        return addBean(auditBean);
    }*/
    public boolean addAuditLog(String operateSubject_ZH, String operateContent_ZH, int opr_state) {
        RestResponse response = new RestResponse();
        response.setSuccess(true);
        UseAuditBean auditBean = new UseAuditBean();
        auditBean.setOpr_state(opr_state);
        auditBean.setOperateSubject_ZH(operateSubject_ZH);
        auditBean.setOperateContent_ZH(operateContent_ZH);

        //添加到数据库        
        return addBean(auditBean);
    }

    @Transactional
    private boolean addBean(UseAuditBean auditBean) {
        String id = PrimaryKeyUtil.getSeq();
        String userFullName = new String();
        String userId = new String();
        String ipAddr = new String();
        loginElement = AuthorityContext.getLoginElement();
        if (CommUtils.isNullOrBlank(loginElement)) {
            /*userId = request.getParameter("loginId").trim();
            userFullName = userDao.loadUser(userId).getFullname();
            request.getLocalAddr();
            ipAddr = getIpAddr(request);*/
        } else {
            userId = loginElement.getUserId();
            userFullName = loginElement.getUserFullName();
            ipAddr = loginElement.getIpAddress();
        }
        auditBean.setId(id);
        //设置操作人的主键
        auditBean.setOperatorId(userId);
        //设置操作时间
        auditBean.setOperateTime(new Date());
        //设置ip地址
        auditBean.setIpAdrr(ipAddr);
        //设置操作人姓名
        auditBean.setOperatorName(userFullName);
        if (auditDao.addAudit(auditBean) <= 0) {
            return false;
        }
        return true;
    }

    /**
     * 查询业务操作（英文）
     * @param auditEl 保存查询条件的element
     * @return
     * @see [类、类#方法、类#成员]
     */
    public RestResponse queryAuditLog_EN(UseAuditElement auditEl, int start, int limit) {
        RestResponse response = new RestResponse();
        RowBounds rows = new RowBounds(start, limit);
        //获取分页查询到的业务操作列表
        List<UseAuditElement> auditBeans = auditDao.queryAudits_EN(auditEl, rows);
        response.setSuccess(true);
        response.setRetObject("auditLogs", auditBeans);
        //设置满足查询条件的记录总数
        response.setRetObject("total", auditDao.countAudits_EN(auditEl));
        return response;
    }

    /**
     * 查询业务操作
     * @param auditEl
     * @return
     * @see [类、类#方法、类#成员]
     */
    public RestResponse queryAuditLog(UseAuditElement auditEl, int start, int limit) throws ApplicationException {
        setQueryParam(auditEl);
        /* loginElement = AuthorityContext.getLoginElement();
         //若当前语言环境为中文，则查询中文版的业务操作
         if (loginElement.getLanguage().indexOf("zh") >= 0 || loginElement.getLanguage().indexOf("ZH") >= 0) {
             return queryAuditLog_ZH(auditEl, start, limit);
         } else {
             //若当前语言环境为英文，则查询英文版的业务操作
             return queryAuditLog_EN(auditEl, start, limit);
         }*/
        return queryAuditLog_ZH(auditEl, start, limit);
    }

    /**
     * 查询业务操作（中文）
     * @param auditEl
     * @return
     * @see [类、类#方法、类#成员]
     */
    public RestResponse queryAuditLog_ZH(UseAuditElement auditEl, int start, int limit) {
        RestResponse response = new RestResponse();
        RowBounds rows = new RowBounds(start, limit);
        //获取分页查询到的业务操作列表
        List<UseAuditElement> auditBeans = auditDao.queryAudits_ZH(auditEl, rows);
        response.setSuccess(true);
        response.setRetObject("auditLogs", auditBeans);
        //设置满足查询条件的记录总数
        response.setRetObject("total", auditDao.countAudits_ZH(auditEl));
        return response;
    }

    /**
     * 设置查询参数
     * @param auditEl
     * @see [类、类#方法、类#成员]
     */
    private void setQueryParam(UseAuditElement auditEl) {
        if (!CommUtils.isNullOrBlank(auditEl.getOperatorName())) {
            //将操作人姓名转换为数据库的语法格式
            auditEl.setOperatorName(CommUtils.convertStringToBase(auditEl.getOperatorName().trim()));
        }
        if (!CommUtils.isNullOrBlank(auditEl.getOperateSubject())) {
            //将操作主题转换为数据库的语法格式
            auditEl.setOperateSubject(CommUtils.convertStringToBase(auditEl.getOperateSubject().trim()));
        }
        if (!CommUtils.isNullOrBlank(auditEl.getOperateContent())) {
            //将操作内容转换为数据库的语法格式
            auditEl.setOperateContent(CommUtils.convertStringToBase(auditEl.getOperateContent().trim()));
        }
        if (!CommUtils.isNullOrBlank(auditEl.getIpAdrr())) {
            //将操作IP转换为数据库的语法格式
            auditEl.setIpAdrr(CommUtils.convertStringToBase(auditEl.getIpAdrr().trim()));
        }
        try {
            if (!CommUtils.isNullOrBlank(auditEl.getStartTime())) {
                //将操作起始时间转换为数据库的语法格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(auditEl.getStartTime());
                auditEl.setStartTime(formatter.parse(date));
            }
            if (!CommUtils.isNullOrBlank(auditEl.getEndTime())) {
                //将操作结束时间转换为数据库的语法格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(auditEl.getEndTime());
                auditEl.setEndTime(formatter.parse(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据主键获取业务操作
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    public RestResponse infoAuditOperation(String id) throws ApplicationException {
        /* loginElement = AuthorityContext.getLoginElement();
         //若当前语言环境为中文，则根据主键获取中文业务操作
         if (loginElement.getLanguage().indexOf("zh") >= 0 || loginElement.getLanguage().indexOf("ZH") >= 0) {
             return infoAuditOperation_ZH(id);
         } else {
             //若当前语言环境为英文，则根据主键获取英文业务操作
             return infoAuditOperation_EN(id);
         }*/
        return infoAuditOperation_ZH(id);
    }

    /**
     * 根据主键获取业务操作(英文)
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    private RestResponse infoAuditOperation_EN(String id) {
        RestResponse response = new RestResponse();
        //根据主键获取英文业务操作
        UseAuditElement bean = auditDao.infoUseAuditById_EN(id);
        response.setSuccess(true);
        response.setRetObject("auditOperation", bean);
        return response;
    }

    /**
     * 根据主键获取业务操作(中文)
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    private RestResponse infoAuditOperation_ZH(String id) {
        RestResponse response = new RestResponse();
        //根据主键获取中文业务操作
        UseAuditElement bean = auditDao.infoUseAuditById_ZH(id);
        response.setSuccess(true);
        response.setRetObject("auditOperation", bean);
        return response;
    }

    /**
     * 导出业务操作
     * @param searchConditions 查询条件
     * @param options 1:导出所有；2：导出查询到的
     * @see [类、类#方法、类#成员]
     */
    public void exportAuditOperation(String searchConditions, int options, HttpServletResponse resp)
            throws ApplicationException {
        loginElement = AuthorityContext.getLoginElement();
        List<UseAuditElement> toExportBeans;
        //若导出条件为导出所有，执行。。。
        if (1 == options) {
            /*  //若当前语言环境为中文，则导出中文的业务操作
              if (loginElement.getLanguage().indexOf("zh") >= 0 || loginElement.getLanguage().indexOf("ZH") >= 0) {
                  toExportBeans = auditDao.queryAudits_ZH(null);
              } else {
                  //若当前语言环境为英文，则导出英文的业务操作
                  toExportBeans = auditDao.queryAudits_EN(null);
              }*/
            toExportBeans = auditDao.queryAudits_ZH(null);
            //若导出条件为导出查询到的，执行。。。
        } else {
            UseAuditElement searchBean = (UseAuditElement) JsonUtils.jsonToObj(searchConditions, UseAuditElement.class);
            setQueryParam(searchBean);
            /* if (loginElement.getLanguage().indexOf("zh") >= 0 || loginElement.getLanguage().indexOf("ZH") >= 0) {
                 //根据查询条件获取中文的业务操作信息
                 toExportBeans = auditDao.queryAudits_ZH(searchBean);
             } else {
                 //根据查询条件获取英文的业务操作信息
                 toExportBeans = auditDao.queryAudits_EN(searchBean);
             }*/
            toExportBeans = auditDao.queryAudits_ZH(searchBean);
        }
        executeExport(toExportBeans, resp);
    }

    /**
     * 执行导出操作
     * @param toExportBeans 待导出的bean
     * @param resp HttpServletResponse
     * @see [类、类#方法、类#成员]
     */
    private void executeExport(List<UseAuditElement> toExportBeans, HttpServletResponse resp) {
        //输出流
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //csv输出流（装饰者模式）
        CSVWriter writer = new CSVWriter(out);
        //要导出的列和值
        List<String[]> allElements = new ArrayList<String[]>();
        //遍历时的临时变量，保存导出的bean
        UseAuditElement toExportBean;
        //遍历时的临时变量，保存导出时间
        String time;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < toExportBeans.size(); i++) {
            toExportBean = toExportBeans.get(i);
            //格式化导出时间
            time = formatter.format(toExportBean.getOperateTime()).toString();
            //设置导出的属性值
            allElements.add(new String[] { toExportBean.getOperatorName(), toExportBean.getIpAdrr(), time,
                    toExportBean.getOperateSubject(), toExportBean.getOperateContent() });
        }
        //执行导出
        writer.writeAll(allElements);
        out.flush();
        out.close();
    }

    /**
     * 取得request IP Address
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取电子文档的业务操作
     * @param docId 电子文档的主键
     * @param start 分页偏移量
     * @param limit 每页大小
     * @return
     * @see [类、类#方法、类#成员]
     */
    public RestResponse queryDocAuditLogs_ZH(String docId, int start, int limit) throws ApplicationException {
        //分页工具
        RowBounds rows = new RowBounds(start, limit);
        //根据电子文档主键获取中文业务操作
        List<UseAuditElement> auditBeans = auditDao.queryDocAuditLogs_ZH(docId, rows);
        RestResponse response = new RestResponse();
        response.setSuccess(true);
        int count = auditDao.countDocAuditLogs_ZH(docId);
        response.setRetObject("total", count);
        response.setRetObject("auditLogs", auditBeans);
        return response;
    }
}
