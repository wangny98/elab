package com.device.business.roleManage.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CheckNodeBean {
    private String id;

    private String text;

    private String idfex;

    private boolean checked;
    
    private int checkedNum;

    private int sortseq;

    private String iconCls;

    private boolean expanded = true;

    private boolean leaf = true;

    private boolean hasParent;

    private String parent_id;

    private List<CheckNodeBean> children = new ArrayList<CheckNodeBean>();

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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIdfex() {
        return this.idfex;
    }

    public void setIdfex(String idfex) {
        this.idfex = idfex;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getSortseq() {
        return this.sortseq;
    }

    public void setSortseq(int sortseq) {
        this.sortseq = sortseq;
    }

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
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

    public String getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<CheckNodeBean> getChildren() {
        return this.children;
    }

    public void setChildren(List<CheckNodeBean> children) {
        this.children = children;
    }

	public int getCheckedNum() {
		return checkedNum;
	}

	public void setCheckedNum(int checkedNum) {
		this.checkedNum = checkedNum;
	}

}
