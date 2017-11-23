package com.device.business.roleManage.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NodeBean {

    private String id;

    private String text;

    private int idfex;

    //private boolean checked;

    private int sortseq;

    private String iconCls;

    private boolean expanded = true;

    private boolean leaf = true;

    private boolean hasParent;

    private String parent_id;

    private List<NodeBean> children = new ArrayList<NodeBean>();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getSortseq() {
        return this.sortseq;
    }

    public void setSortseq(int sortseq) {
        this.sortseq = sortseq;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isHasParent() {
        return this.hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public List<NodeBean> getChildren() {
        return this.children;
    }

    public void setChildren(List<NodeBean> children) {
        this.children = children;
    }

    public int getIdfex() {
        return this.idfex;
    }

    public void setIdfex(int idfex) {
        this.idfex = idfex;
    }

    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

}
