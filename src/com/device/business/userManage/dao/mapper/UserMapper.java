package com.device.business.userManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.roleManage.bean.RoleBean;
import com.device.business.userManage.bean.UserBean;

public interface UserMapper {

    ArrayList<UserBean> queryUsers(String searchName, RowBounds rowBounds);

    int countUser(String searchName);

    int addUser(@Param("bean") UserBean bean);

    int updateUser(@Param("bean") UserBean bean);

    UserBean loadUser(@Param("id") String id);

    int deleteUser(@Param("id") String id);

    ArrayList<RoleBean> queryUserRoleAssign(@Param("id") String id);

    int isRoleUser(@Param("userId") String userId, @Param("roleId") String roleId);

    int addRoleUser(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);

    int delRoleUser(@Param("userId") String userId);

    UserBean validateUser(@Param("username") String username);

}
