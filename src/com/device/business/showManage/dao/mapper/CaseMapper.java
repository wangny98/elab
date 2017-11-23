package com.device.business.showManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.showManage.bean.CaseBean;

public interface CaseMapper {
	ArrayList<CaseBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    CaseBean load(@Param("id") String id);

    int add(@Param("bean") CaseBean bean);

    int update(@Param("bean") CaseBean bean);
    
    int delete(@Param("id")String id);
}
