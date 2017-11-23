package com.device.business.recordManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.recordManager.bean.RecordChangeBean;

public interface RecordChangeMapper {
	ArrayList<RecordChangeBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    RecordChangeBean load(@Param("id") String id);

    int add(@Param("bean") RecordChangeBean bean);

    int update(@Param("bean") RecordChangeBean bean);
    
    int delete(@Param("id")String id);
}
