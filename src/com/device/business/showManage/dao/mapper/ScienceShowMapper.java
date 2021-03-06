package com.device.business.showManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.showManage.bean.ScienceShowBean;



public interface ScienceShowMapper {
	ArrayList<ScienceShowBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    ScienceShowBean load(@Param("id") String id);

    int add(@Param("bean") ScienceShowBean bean);

    int update(@Param("bean") ScienceShowBean bean);
    
    int delete(@Param("id")String id);
}
