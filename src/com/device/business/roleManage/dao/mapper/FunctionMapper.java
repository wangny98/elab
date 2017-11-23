package com.device.business.roleManage.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.device.business.roleManage.bean.CheckNodeBean;
import com.device.business.roleManage.bean.NodeBean;

public interface FunctionMapper {

    int updateCodes(@Param("roleId") String roleId, @Param("codes") String codes);
    
    List<String> getAllCodes();
    
    List<CheckNodeBean> getCheckModule(@Param("codes") String codes);
    
    List<CheckNodeBean> getNotCheckModule(@Param("codes") String codes);
    
    List<NodeBean> getAssignModule(@Param("codes") String codes);
}
