package com.device.business.classificationManager.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.device.business.organizationManage.bean.OrgTreeBean;

public class ClassTreeBean {
	/**
	 * 结点的编号id
	 */
	private String id;
	/**
	 * 结点全称
	 */
	private String text;

	/**
	 * 孩子结点
	 */
	private List<ClassTreeBean> children = new ArrayList<ClassTreeBean>();
	/**
	 * 是否是叶子结点
	 */
	private boolean leaf = true;
	/**
	 * 父节点的编号id
	 */
	private String parent;

	/**
	 * 父节点的全称
	 */
	private String parentName;

	/**
	 * 节点类别
	 */
	private Integer type;

	/**
	 * 节点展开
	 * 
	 */
	private boolean expanded = true;
	/**
	 * 结点图标样式
	 */
	private String iconCls;

	/**
	 * 是否有父类
	 */
	private boolean hasParent;

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

	public boolean isLeaf() {
		return this.leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public List<ClassTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<ClassTreeBean> children) {
		this.children = children;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public boolean isExpanded() {
		return expanded;
	}
}
