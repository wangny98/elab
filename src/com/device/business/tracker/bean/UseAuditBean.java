package com.device.business.tracker.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 对文档和文件夹的利用情况进行利用跟踪
 * 
 * @author  guoke
 * @version  [版本号, 2013-8-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UseAuditBean implements Serializable {

    /**
     * 序列化编码
     */
    private static final long serialVersionUID = -6560499960559209845L;
    /**
     * 主键
     */
    private String id;
    /**
     * 操作内容（中文）
     */
    private String operateContent_EN;
    /**
     * 操作内容（英文）
     */
    private String operateContent_ZH;
    /**
     * 操作标题（英文）
     */
    private String operateSubject_EN;
    /**
     * ip地址
     */
    private String ipAdrr;
    /**
     * 操作标题（中文）
     */
    private String operateSubject_ZH;
    /**
     *操作时间
     */
    private Date operateTime;
    /**
     * 操作人主键
     */
    private String operatorId;
    /**
     * 操作人姓名
     */
    private String operatorName;
    /**
     * 记录审计的电子文档的主键
     */
    private String docId;

    public String getId() {
        return this.id;
    }

    public int opr_state = 0;

    public int getOpr_state() {
        return this.opr_state;
    }

    public void setOpr_state(int opr_state) {
        this.opr_state = opr_state;
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
