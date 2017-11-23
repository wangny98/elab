package com.device.business.recordManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.recordManager.bean.RecordControlListBean;


public interface RecordControlListMapper {
	ArrayList<RecordControlListBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    RecordControlListBean load(@Param("id") String id);

    int add(@Param("bean") RecordControlListBean bean);

    int update(@Param("bean") RecordControlListBean bean);
    
    int delete(@Param("id")String id);
}
