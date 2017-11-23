package com.device.business.roleManage.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RootBean {

    private String id;

    private String title;

    private boolean expanded = true;

    private int sortseq;

    private boolean border;

    private boolean rootVisible;

    private List<NodeBean> children = new ArrayList<NodeBean>();

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getSortseq() {
        return this.sortseq;
    }

    public void setSortseq(int sortseq) {
        this.sortseq = sortseq;
    }

    public boolean isBorder() {
        return this.border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public boolean isRootVisible() {
        return this.rootVisible;
    }

    public void setRootVisible(boolean rootVisible) {
        this.rootVisible = rootVisible;
    }

    public List<NodeBean> getChildren() {
        return this.children;
    }

    public void setChildren(List<NodeBean> children) {
        this.children = children;
    }

}
