package com.device.business.userManage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.login.element.LoginElement;
import com.device.business.organizationManage.bean.OrganizationBean;
import com.device.business.organizationManage.dao.mapper.OrganizationMapper;
import com.device.business.roleManage.bean.RoleBean;
import com.device.business.systemSet.bean.DepartmentBean;
import com.device.business.systemSet.dao.mapper.DepartmentMapper;
import com.device.business.userManage.bean.UserBean;
import com.device.business.userManage.dao.mapper.UserMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class UserDao {

    @Autowired
    UserMapper userMapper;
    
    @Autowired
    DepartmentMapper departmentMapper;
    
    @Autowired
    OrganizationMapper organizationMapper;

    public List<UserBean> queryUser(String searchName, int start, int limit) {
        //分页工具
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<UserBean> list = new ArrayList<UserBean>();
        list = userMapper.queryUsers("%" + searchName + "%", rows);
        return list;
    }

    public int countUser(String searchName) {
        int i = 0;
        i = userMapper.countUser("%" + searchName + "%");
        return i;
    }

    public int addUser(UserBean bean) {
        LoginElement login = AuthorityContext.getLoginElement();
        bean.setId(PrimaryKeyUtil.getSeq());
        bean.setCreator_id(login.getUserId());
        bean.setCreator_name(login.getUserFullName());
        bean.setCreator_time(new Date());
        return userMapper.addUser(bean);
    }

    public int updateUser(UserBean bean) {
        return userMapper.updateUser(bean);
    }

    public UserBean loadUser(String id) {
    	UserBean bean = new UserBean();
    	bean =  userMapper.loadUser(id);
    	if(bean.getDepartment_id()!=null){
    		/*OrganizationBean org = organizationMapper.loadOrganization(bean.getDepartment_id());
    		if(org!=null)
    			bean.setDepartmentName(org.getOrg_name());*/
    		DepartmentBean depart = departmentMapper.getDepartment(bean.getDepartment_id());
    		if(depart!=null)
    			bean.setDepartmentName(depart.getDepart_name());
    	}
        return bean;
    }

    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }

    public List<RoleBean> listAssignRole(String id) {
        return userMapper.queryUserRoleAssign(id);
    }

    @Transactional
    public void assignRoleUser(String userId, String[] roleIds) {
        userMapper.delRoleUser(userId);
        for (int i = 0; i < roleIds.length; i++) {
            /*int u = userMapper.isRoleUser(userId, roleIds[i]);
            if (u == 0) {*/
                userMapper.addRoleUser(PrimaryKeyUtil.getSeq(), userId, roleIds[i]);
            /*}*/
        }
    }

    public UserBean validateUser(String username) {
        return userMapper.validateUser(username);
    }

}
