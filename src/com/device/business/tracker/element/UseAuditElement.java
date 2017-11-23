package com.device.business.tracker.element;

import java.io.Serializable;
import java.util.Date;

import javax.ws.rs.FormParam;

import com.device.util.DateFormat;

/**
 * 
 * 对文档和文件夹的利用情况进行利用跟踪
 * 
 * @author  guoke
 * @version  [版本号, 2013-8-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UseAuditElement implements Serializable {

    /**
     * 序列化编码
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    @FormParam("startTime")
    @DateFormat("yyyy-MM-dd")
    private Date startTime;
    @FormParam("endTime")
    @DateFormat("yyyy-MM-dd")
    private Date endTime;
    /**
     * 操作内容（中文）
     */
    private String operateContent_EN;
    /**
     * 操作内容（英文）
     */
    private String operateContent_ZH;
    /**
     * ip地址
     */
    @FormParam("ipAdrr")
    private String ipAdrr;
    /**
     * 操作内容
     */
    @FormParam("operateContent")
    private String operateContent;
    /**
     * 操作标题（英文）
     */
    private String operateSubject_EN;
    /**
     * 操作标题（中文）
     */
    private String operateSubject_ZH;
    /**
     * 操作标题
     */
    @FormParam("operateSubject")
    private String operateSubject;
    /**
     *操作时间
     */
    @FormParam("operateTime")
    private Date operateTime;
    /**
     * 操作人主键
     */
    private String operatorId;
    /**
     * 操作人姓名
     */
    @FormParam("operatorName")
    private String operatorName;
    /**
     * 记录审计的电子文档的主键
     */
    private String docId;

    private int opr_state;

    public int getOpr_state() {
        return this.opr_state;
    }

    public void setOpr_state(int opr_state) {
        this.opr_state = opr_state;
    }

    public String getId() {
        return this.id;
    }

    public String getOperateContent_EN() {
        return this.operateContent_EN;
    }

    public String getOperateContent_ZH() {
        return this.operateContent_ZH;
    }

    public String getOperateSubject_EN() {
        return this.operateSubject_EN;
    }

    public String getOperateSubject_ZH() {
        return this.operateSubject_ZH;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOperateContent_EN(String operateContent_EN) {
        this.operateContent_EN = operateContent_EN;
    }

    public void setOperateContent_ZH(String operateContent_ZH) {
        this.operateContent_ZH = operateContent_ZH;
    }

    public void setOperateSubject_EN(String operateSubject_EN) {
        this.operateSubject_EN = operateSubject_EN;
    }

    public void setOperateSubject_ZH(String operateSubject_ZH) {
        this.operateSubject_ZH = operateSubject_ZH;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    public String getOperateContent() {
        return operateContent;
    }

    public void setOperateSubject(String operateSubject) {
        this.operateSubject = operateSubject;
    }

    public String getOperateSubject() {
        return operateSubject;
    }

    public void setIpAdrr(String ipAdrr) {
        this.ipAdrr = ipAdrr;
    }

    public String getIpAdrr() {
        return ipAdrr;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }
}
