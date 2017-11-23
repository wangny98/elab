package com.device.business.roleManage.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.roleManage.bean.CheckNodeBean;
import com.device.business.roleManage.bean.NodeBean;
import com.device.business.roleManage.bean.RootBean;
import com.device.business.roleManage.dao.mapper.FunctionMapper;
import com.device.business.roleManage.dao.mapper.RoleMapper;
import com.device.util.IdAppendUtil;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class FunctionDao {
    @Autowired
    RoleDao roleDao;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    FunctionMapper functionMapper;

    /** ********************************buildTreeOther******************************* */
    public void buildTreeOther(NodeBean bean, Set<NodeBean> set) {
        Iterator<NodeBean> it = set.iterator();
        Set<NodeBean> setOther = new HashSet<NodeBean>();
        while (it.hasNext()) {
            NodeBean node = (NodeBean) it.next();
            //node.setIconCls(getIconCls(node.getType()));
            if (node.getParent_id().equals(bean.getId()) && !node.isHasParent()) {
                //添加子节点
                bean.getChildren().add(node);
                node.setHasParent(true);
                //标识为非叶节点
                bean.setLeaf(false);
                it.remove();
                setOther.addAll(set);
                buildTreeOther(node, setOther);
            }
        }

    }

    public List<CheckNodeBean> loadAssignFunc(String roleId) throws IllegalAccessException, InvocationTargetException {
        //List<NodeBean> list = new ArrayList<NodeBean>();

        String funcCode = roleDao.loadRole(roleId).getFunc_code();

        String funcCodes = "(" + funcCode + ")";
        
        List<CheckNodeBean> checkFunc = roleMapper.getAllCheckedFunc(funcCodes);
        for(int i=0;i<checkFunc.size();i++){
        	if(checkFunc.get(i).getCheckedNum()==1){
        		checkFunc.get(i).setChecked(true);
        	}
        }
        List<NodeBean> allModule = roleMapper.getAllModule();
        for(NodeBean m:allModule){
        	CheckNodeBean CheckNodeBean = new CheckNodeBean();
        	BeanUtils.copyProperties(CheckNodeBean, m);
        	checkFunc.add(CheckNodeBean);
        }

        /*List<NodeBean> checkFunc = roleMapper.getFunc(funcCodes);

        List<NodeBean> allFunc = roleMapper.getAllFunc();

        List<NodeBean> allModule = roleMapper.getAllModule();
        allFunc.addAll(allModule);

         全部节点 
        List<CheckNodeBean> allFuncOther = new ArrayList<CheckNodeBean>();

        for (NodeBean n : allFunc) {

            CheckNodeBean CheckNodeBean = new CheckNodeBean();
            BeanUtils.copyProperties(CheckNodeBean, n);
            if (checkFunc.contains(n)) {
                CheckNodeBean.setChecked(true);
            }
            allFuncOther.add(CheckNodeBean);
        }*/
        CheckNodeBean virtualBean = new CheckNodeBean();
        virtualBean.setId("0");
        virtualBean.setLeaf(false);
        virtualBean.setHasParent(false);
        this.buildTree(virtualBean, checkFunc);

        return virtualBean.getChildren();

    }

    public void buildTree(CheckNodeBean bean, List<CheckNodeBean> list) {
        ListIterator<CheckNodeBean> it = list.listIterator();
        List<CheckNodeBean> listOther = new ArrayList<CheckNodeBean>();
        while (it.hasNext()) {
            CheckNodeBean node = (CheckNodeBean) it.next();
            if (node.getParent_id().equals(bean.getId()) && !node.isHasParent()) {
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

    public int updateFuncCode(String roleId, String codes) {
        return functionMapper.updateCodes(roleId, codes);
    }
    
    
    ///////////////////////////分配菜单///////////////////////////////
    
    public List<CheckNodeBean>  loadAssignModule(String roleId){
    	String funcCode = roleDao.loadRole(roleId).getFunc_code();
    	String funcCodes=null;
    	if(funcCode==null){
    		List<String> strList= functionMapper.getAllCodes();
    		funcCode = IdAppendUtil.Collection2String(strList);
    		funcCodes = funcCode;
    	}else{
    		funcCodes = "(" + funcCode + ")";
    	}
        List<CheckNodeBean> allModule = new ArrayList<CheckNodeBean>();
        List<CheckNodeBean> checkModule = functionMapper.getCheckModule(funcCodes);
        for(int i=0;i<checkModule.size();i++){
        	if(checkModule.get(i).getCheckedNum()==1){
        		checkModule.get(i).setChecked(true);
        	}
        }
        allModule.addAll(checkModule);
        
        List<CheckNodeBean> notCheckModule = functionMapper.getNotCheckModule(funcCodes);
        allModule.addAll(notCheckModule);
    	
        Comparator<CheckNodeBean> comparator = new Comparator<CheckNodeBean>() {
            public int compare(CheckNodeBean o1, CheckNodeBean o2) {
                return o1.getSortseq() - o2.getSortseq();
            }
        };
        Collections.sort(allModule, comparator);
        
    	CheckNodeBean virtualBean = new CheckNodeBean();
        virtualBean.setId("0");
        virtualBean.setLeaf(false);
        virtualBean.setHasParent(false);
        this.buildTree(virtualBean, allModule);

        return virtualBean.getChildren();
    }
    
    /******                                       菜单显示                                           *****/
    
    public ArrayList<Map<String, Object>> loadFuncTree(String userId) {
    	String funcCodes = "(" + roleDao.getUserFunc(userId) + ")";

        List<NodeBean> assignModule = functionMapper.getAssignModule(funcCodes);
        
        Comparator<NodeBean> comparator = new Comparator<NodeBean>() {
            public int compare(NodeBean o1, NodeBean o2) {
                return o1.getSortseq() - o2.getSortseq();
            }
        };
        Collections.sort(assignModule, comparator);
        NodeBean virtualBean = new NodeBean();
        virtualBean.setId("0");
        virtualBean.setLeaf(false);
        virtualBean.setHasParent(false);

        roleDao.buildTree(virtualBean, assignModule);
        ArrayList<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        List<NodeBean> rootNode = virtualBean.getChildren();
        for (NodeBean n : rootNode) {
            RootBean root = new RootBean();
            Map<String, Object> map = new HashMap<String, Object>();
            root.setTitle(n.getText());
            root.setSortseq(n.getSortseq());
            root.setChildren(n.getChildren());
            map.put("root", root);
            maps.add(map);
        }
        return maps;
    }
}
