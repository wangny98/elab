package com.device.business.recordManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.recordManager.bean.RecordRecycleBean;

public interface RecordRecycleMapper {
	ArrayList<RecordRecycleBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    RecordRecycleBean load(@Param("id") String id);

    int add(@Param("bean") RecordRecycleBean bean);

    int update(@Param("bean") RecordRecycleBean bean);
    
    int delete(@Param("id")String id);
}
