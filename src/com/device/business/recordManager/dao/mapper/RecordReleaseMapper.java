package com.device.business.recordManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.recordManager.bean.RecordReleaseBean;


public interface RecordReleaseMapper {
	ArrayList<RecordReleaseBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    RecordReleaseBean load(@Param("id") String id);

    int add(@Param("bean") RecordReleaseBean bean);

    int update(@Param("bean") RecordReleaseBean bean);
    
    int delete(@Param("id")String id);
}
