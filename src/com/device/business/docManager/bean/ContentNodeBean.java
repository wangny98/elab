package com.device.business.docManager.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ContentNodeBean {

    private String id;

    @FormParam("parentId")
    private String parentId;

    @FormParam("typeId")
    private String typeId;

    @FormParam("name")
    private String name;

    private String text;

    private String iconCls = "icon-comtent";

    private boolean expanded = true;

    private String qtip;

    @FormParam("remark")
    private String remark;

    private String creatorId;

    private String creatorName;

    private Date creatorTime;

    private int deleteFlag;

    private boolean isHasParent = false;

    private boolean leaf = true;

    private String uiProvider;

    private String path;

    private List<ContentNodeBean> children = new ArrayList<ContentNodeBean>();

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return this.creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatorTime() {
        return this.creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public int getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void setHasParent(boolean isHasParent) {
        this.isHasParent = isHasParent;
    }

    public boolean isHasParent() {
        return isHasParent;
    }

    public void setChildren(List<ContentNodeBean> children) {
        this.children = children;
    }

    public List<ContentNodeBean> getChildren() {
        return children;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setQtip(String qtip) {
        this.qtip = qtip;
    }

    public String getQtip() {
        return qtip;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getUiProvider() {
        return uiProvider;
    }

    public void setUiProvider(String uiProvider) {
        this.uiProvider = uiProvider;
    }

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
