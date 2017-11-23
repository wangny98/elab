package com.device.business.roleManage.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.roleManage.bean.NodeBean;
import com.device.business.roleManage.bean.RoleBean;
import com.device.business.roleManage.bean.RootBean;
import com.device.business.roleManage.dao.mapper.RoleMapper;
import com.device.business.tracker.dao.UseAuditRepository;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RoleDao {

    /**
     * 审计日志业务操作
     */
    @Autowired
    UseAuditRepository actionTracker;

    @Autowired
    RoleMapper roleMapper;

    public List<RoleBean> queryRole(String searchName, int start, int limit) {
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<RoleBean> list = new ArrayList<RoleBean>();
        list = roleMapper.queryRoles("%" + searchName + "%", rows);
        return list;
    }

    public List<RoleBean> queryAllRole(String userId) {
        ArrayList<RoleBean> list = new ArrayList<RoleBean>();
        list = roleMapper.queryAllCheckRoles(userId);
        return list;
    }

    public int countRole(String searchName) {
        int i = 0;
        i = roleMapper.countRole("%" + searchName + "%");
        return i;
    }

    public int addRole(RoleBean bean) {
        LoginElement login = AuthorityContext.getLoginElement();
        bean.setId(PrimaryKeyUtil.getSeq());
        bean.setCreator_id(login.getUserId());
        bean.setCreator_name(login.getUserFullName());
        bean.setCreator_time(new Date());
        return roleMapper.addRole(bean);
    }

    public RoleBean loadRole(String id) {
        return roleMapper.loadRole(id);
    }

    public int updRole(RoleBean bean) {
        actionTracker.addAuditLog("角色管理", "修改角色 ", 1);

        return roleMapper.updateRole(bean);
    }

    public int deleteRole(String id) {
        return roleMapper.deleteRole(id);
    }

    public List<String> getUserRoleId(String userId) {
        List<String> roleId = new ArrayList<String>();
        roleId = roleMapper.getRoleId(userId);
        return roleId;
    }

    /***************************************************/
    public List<NodeBean> loadAllTree() {
        List<NodeBean> moduleList = new ArrayList<NodeBean>();

        moduleList = roleMapper.getAllModule();
        NodeBean virtualBean = new NodeBean();
        virtualBean.setId("0");
        virtualBean.setLeaf(false);
        virtualBean.setHasParent(false);
        this.buildTree(virtualBean, moduleList);

        return moduleList;
    }

    public String getUserFunc(String userId) {
        List<String> roleIdList = this.getUserRoleId(userId);
        String funcCode = new String("''");
        for (int i = 0; i < roleIdList.size(); i++) {
            RoleBean role = this.loadRole(roleIdList.get(i));
            if (role != null && role.getFunc_code() != null) {
                funcCode = funcCode.concat("," + role.getFunc_code());
            }
        }
        return funcCode;
    }

    public ArrayList<Map<String, Object>> loadFuncTree(String userId) {
        /*String roleId = this.getUserRoleId(userId);
        RoleBean role = this.loadRole(roleId);*/

        String funcCodes = "(" + this.getUserFunc(userId) + ")";

        List<NodeBean> funcList = roleMapper.getFunc(funcCodes);

        List<NodeBean> moduleList = new ArrayList<NodeBean>();

        moduleList = this.getModuleList(funcList);

        Comparator<NodeBean> comparator = new Comparator<NodeBean>() {
            public int compare(NodeBean o1, NodeBean o2) {
                return o1.getSortseq() - o2.getSortseq();
            }
        };
        Collections.sort(moduleList, comparator);
        /*List<RootBean> rooList = new ArrayList<RootBean>();
        rooList = roleMapper.getRoots();*/
        NodeBean virtualBean = new NodeBean();
        virtualBean.setId("0");
        virtualBean.setLeaf(false);
        virtualBean.setHasParent(false);

        this.buildTree(virtualBean, moduleList);

        //////////////////////////////////////
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

    //���ѡ�е�Func��ȡModule
    public List<NodeBean> getModuleList(List<NodeBean> funcList) {
        String moduleIds3 = new String();
        String moduleIds2 = new String();
        String moduleIds1 = new String();

        Set<NodeBean> moduleSet = new HashSet<NodeBean>();
        List<NodeBean> moduleList = new ArrayList<NodeBean>();

        moduleIds3 = this.getNodeIds(funcList);
        List<NodeBean> module3 = roleMapper.getModule(moduleIds3, 100, 1000);
        System.out.println("module3.size : " + module3.size());
        moduleSet.addAll(module3);

        moduleIds2 = this.getNodeIds(module3);
        List<NodeBean> module2 = roleMapper.getModule(moduleIds2, 10, 100);
        List<NodeBean> systemSet = roleMapper.getModule(moduleIds3, 10, 100);
        module2.addAll(systemSet);
        System.out.println("module2.size : " + module2.size());
        moduleSet.addAll(module2);

        moduleIds1 = this.getNodeIds(module2);
        List<NodeBean> module1 = roleMapper.getModule(moduleIds1, 0, 10);
        System.out.println("module1.size : " + module1.size());
        moduleSet.addAll(module1);

        System.out.println("moduleSet.size : " + moduleSet.size());
        moduleList.addAll(moduleSet);
        return moduleList;
    }

    public Set<String> loadAllCode() {
        Set<String> s = new HashSet<String>();
        List<NodeBean> funcAll = roleMapper.getAllFunc();
        Iterator<NodeBean> it = funcAll.iterator();
        while (it.hasNext()) {
            s.add(String.valueOf(it.next().getIdfex()));
        }
        return s;
    }

    public Set<String> loadFuncCode(String userId) {
        Set<String> s = new HashSet<String>();
        /*String roleId = this.getUserFunc(userId);
        RoleBean role = this.loadRole(roleId);*/
        String[] strs = this.getUserFunc(userId).split(",");
        for (int i = 0; i < strs.length; i++) {
            s.add(strs[i]);
        }
        return s;
    }

    /***********************************************************************************/
    public String getNodeIds(List<NodeBean> list) {
        String str = new String("('");
        for (NodeBean n : list) {
            str = str.concat("','" + n.getParent_id());
        }
        str = str.concat("')");
        System.out.println("ids : " + str);
        return str;
    }

    public void buildTree(NodeBean bean, List<NodeBean> list) {
        ListIterator<NodeBean> it = list.listIterator();
        List<NodeBean> listOther = new ArrayList<NodeBean>();
        while (it.hasNext()) {
            NodeBean node = (NodeBean) it.next();
            //node.setIconCls(getIconCls(node.getType()));
            //System.out.println("node : " + node);
            if (node.getParent_id().equals(bean.getId()) && !node.isHasParent()) {
                //����ӽڵ�
                bean.getChildren().add(node);
                node.setHasParent(true);
                //��ʶΪ��Ҷ�ڵ�
                bean.setLeaf(false);
                it.remove();
                listOther.addAll(list);
                buildTree(node, listOther);
            }
        }
    }

    public void buildTreeOther(NodeBean bean, Set<NodeBean> set) {
        Iterator<NodeBean> it = set.iterator();
        Set<NodeBean> listOther = new HashSet<NodeBean>();
        while (it.hasNext()) {
            NodeBean node = (NodeBean) it.next();
            //node.setIconCls(getIconCls(node.getType()));
            //System.out.println("node : " + node);
            if (node.getParent_id().equals(bean.getId()) && !node.isHasParent()) {
                //����ӽڵ�
                bean.getChildren().add(node);
                node.setHasParent(true);
                //��ʶΪ��Ҷ�ڵ�
                bean.setLeaf(false);
                it.remove();
                listOther.addAll(set);
                buildTreeOther(node, listOther);
            }
        }
    }

}
