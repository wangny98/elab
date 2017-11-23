package com.device.business.organizationManage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import com.device.business.login.element.LoginElement;
import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.organizationManage.bean.OrganizationBean;
import com.device.business.organizationManage.dao.mapper.OrganizationMapper;

import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class OrganizationDao {
	@Autowired
	OrganizationMapper organizationMapper;
	
	public List<OrganizationBean> queryOrganization(String searchName, int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<OrganizationBean> list = new ArrayList<OrganizationBean>();
		list = organizationMapper.queryOrganization("%"+searchName+"%", rows);
		return list;
	}
	
	public int countOrganization(String searchName){
		int i=0;
		i= organizationMapper.countOrganization("%"+searchName+"%");
		return i;
	}
	
	@Transactional
	public int addOrganization(OrganizationBean bean,String parent_id){
		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_id(login.getUserId());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());
		organizationMapper.addOrganization(bean);
		return organizationMapper.addOrgUnion(PrimaryKeyUtil.getSeq(), parent_id, bean.getId());
	}
	
	public OrganizationBean loadOrganization(String id){
		return organizationMapper.loadOrganization(id);
	}
	
	public int updOrganization(OrganizationBean bean){
		return organizationMapper.updateOrganization(bean);
	}
	
	@Transactional
	public int deleteOrganization(String id){
		organizationMapper.deleteOrgUnion(id);
		return organizationMapper.deleteOrganization(id);
	}
	
	/**
     * 根据根据历史版本ID查询历史版本组织机构树
     * @param versionId 历史版本ID
     * @return OrgTreeBean 组织机构树实体
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public OrgTreeBean getTree(String rootId) {
        OrgTreeBean root = new OrgTreeBean();
        //根节点ID默认为0
        root.setId("0");
        root.setLeaf(false);
        //根节点TYPE为1代表公司
        root.setType(1);
        List<OrgTreeBean> list = this.listNode(rootId);
        this.buildTree(root, list);
        return root;
    }
    
    public List<OrgTreeBean> listNode(String treeId) {
        List<OrgTreeBean> list = null;
        list = organizationMapper.listAllTreeNode();
        return list;
    }
    
    public void buildTree(OrgTreeBean bean, List<OrgTreeBean> list) {
        ListIterator<OrgTreeBean> it = list.listIterator();
        List<OrgTreeBean> listOther = new ArrayList<OrgTreeBean>();
        while (it.hasNext()) {
            OrgTreeBean node = (OrgTreeBean) it.next();
            //node.setIconCls(getIconCls(node.getType()));
            if (node.getParent().equals(bean.getId()) && !node.isHasParent()) {
                //添加子节点
                bean.getChildren().add(node);
                node.setHasParent(true);
                //标识为非叶节点
                bean.setLeaf(false);
                it.remove();
                listOther.addAll(list);
                buildTree(node, listOther);
            }
        }
    }
    
    public String queryDeptCode(String id){
    	return organizationMapper.queryDeptCode(id);
    }
}
