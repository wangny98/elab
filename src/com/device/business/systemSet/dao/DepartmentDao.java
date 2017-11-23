package com.device.business.systemSet.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.systemSet.bean.DepartmentBean;
import com.device.business.systemSet.dao.mapper.DepartmentMapper;
import com.device.business.systemSet.dao.mapper.UnitMapper;
import com.device.util.PrimaryKeyUtil;

public class DepartmentDao {
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private UnitMapper unitMapper;
	
	@Autowired
	private UnionDao unionDao;
	
	public List<DepartmentBean> list(String name,int start,int limit){
		List<DepartmentBean> list = new ArrayList<DepartmentBean>();
		RowBounds rowBounds = new RowBounds(start, limit);
		list = departmentMapper.listDepartment("%"+name+"%", rowBounds);
		return list;
	}
	
	public DepartmentBean get(String id){
		return departmentMapper.getDepartment(id);
	}
	
	@Transactional
	public int add(DepartmentBean bean,String parentId){
		String id = PrimaryKeyUtil.getSeq();
		unionDao.addUnion(parentId, id);
		bean.setId(id);
		return departmentMapper.addDepartment(bean);
	}
	
	public int modify(DepartmentBean bean){
		return departmentMapper.updDepartment(bean);
	}
	
	@Transactional
	public int delete(String id){
		unionDao.delUnion(id);
		return departmentMapper.delDepartment(id);
	}
	
	public OrgTreeBean getTreeNode(){
		OrgTreeBean root = new OrgTreeBean();
		List<OrgTreeBean> listUnit = unitMapper.getNodes();
		List<OrgTreeBean> listDepartment = departmentMapper.getNodes();
		listUnit.addAll(listDepartment);
		
		root.setId("0");
		root.setLeaf(false);
        //根节点TYPE为1代表公司
        root.setType(1);
        this.buildTree(root, listUnit);
		return root;
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
}
