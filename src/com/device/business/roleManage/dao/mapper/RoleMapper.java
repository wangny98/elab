package com.device.business.roleManage.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.roleManage.bean.CheckNodeBean;
import com.device.business.roleManage.bean.NodeBean;
import com.device.business.roleManage.bean.RoleBean;
import com.device.business.roleManage.bean.RootBean;

public interface RoleMapper {

    ArrayList<RoleBean> queryRoles(String searchName, RowBounds rowBounds);

    ArrayList<RoleBean> queryAllRoles();

    ArrayList<RoleBean> queryAllCheckRoles(@Param("userId") String userId);

    int countRole(String searchName);

    int addRole(@Param("bean") RoleBean bean);

    int updateRole(@Param("bean") RoleBean bean);

    RoleBean loadRole(@Param("id") String id);

    int deleteRole(@Param("id") String id);

    List<String> getRoleId(@Param("userId") String userId);

    /* **************************************** */

    List<NodeBean> getFunc(@Param("codes") String codes);

    List<NodeBean> getAllFunc();
    
    List<CheckNodeBean> getAllCheckedFunc(@Param("codes") String codes);

    List<NodeBean> getModule(@Param("ids") String ids, @Param("codeLevel") int codeLevel,
            @Param("codeLevelMax") int codeLevelMax);

    List<RootBean> getRoots();

    List<NodeBean> getAllModule();

    /* **************************************** */

}
