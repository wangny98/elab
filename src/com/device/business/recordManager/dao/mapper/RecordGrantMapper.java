package com.device.business.recordManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.recordManager.bean.RecordGrantBean;

public interface RecordGrantMapper {
	ArrayList<RecordGrantBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    RecordGrantBean load(@Param("id") String id);

    int add(@Param("bean") RecordGrantBean bean);

    int update(@Param("bean") RecordGrantBean bean);
    
    int delete(@Param("id")String id);
}
