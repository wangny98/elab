package com.device.business.classificationManager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.classificationManager.bean.ClassTreeBean;
import com.device.business.classificationManager.bean.ClassificationBean;
import com.device.business.classificationManager.dao.mapper.ClassificationMapper;
import com.device.business.login.element.LoginElement;
import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.organizationManage.bean.OrganizationBean;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class ClassificationDao {

	@Autowired
	ClassificationMapper classificationMapper;
	
	/**
     * 根据根据历史版本ID查询历史版本组织机构树
     * @param versionId 历史版本ID
     * @return OrgTreeBean 组织机构树实体
     * @throws
     * @see [类、类#方法、类#成员]
     */
    public ClassTreeBean getTree(String rootId) {
    	ClassTreeBean root = new ClassTreeBean();
        //根节点ID默认为0
        root.setId("0");
        root.setLeaf(false);
        //根节点TYPE为1代表公司
        root.setType(1);
        List<ClassTreeBean> list = this.listNode(rootId);
        this.buildTree(root, list);
        return root;
    }
    
    public List<ClassTreeBean> listNode(String treeId) {
        List<ClassTreeBean> list = null;
        list = classificationMapper.listAllTreeNode();
        return list;
    }
    
    public void buildTree(ClassTreeBean bean, List<ClassTreeBean> list) {
        ListIterator<ClassTreeBean> it = list.listIterator();
        List<ClassTreeBean> listOther = new ArrayList<ClassTreeBean>();
        while (it.hasNext()) {
        	ClassTreeBean node = (ClassTreeBean) it.next();
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
    
	@Transactional
	public int addClassification(ClassificationBean bean,String parent_id){
		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_id(login.getUserId());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());
		classificationMapper.addClassification(bean);
		return classificationMapper.addClaUnion(PrimaryKeyUtil.getSeq(), parent_id, bean.getId());
	}
	
	public List<ClassificationBean> queryClassification(String searchName, int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<ClassificationBean> list = new ArrayList<ClassificationBean>();
		list = classificationMapper.queryClassification("%"+searchName+"%", rows);
		return list;
	}
	
	public int countClassification(String searchName){
		int i=0;
		i= classificationMapper.countClassification("%"+searchName+"%");
		return i;
	}
	
	public int updClassification(ClassificationBean bean){
		return classificationMapper.updateClassification(bean);
	}
	
	public ClassificationBean loadClassification(String id){
		return classificationMapper.loadClassification(id);
	}
	
	@Transactional
	public int deleteClassification(String id){
		classificationMapper.deleteClaUnion(id);
		return classificationMapper.deleteClassification(id);
	}
	
	public String queryClassCode(String id){
		return classificationMapper.queryClassCode(id);
	}
	
	public String queryClassName(String id){
		return classificationMapper.queryClassName(id);
	}
	
}
