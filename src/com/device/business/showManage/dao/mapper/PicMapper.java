package com.device.business.showManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.showManage.bean.PicBean;


public interface PicMapper {
	ArrayList<PicBean> query(@Param("searchName") String searchName, RowBounds rowBounds);

    int count(@Param("searchName") String searchName);

    PicBean load(@Param("id") String id);

    int add(@Param("bean") PicBean bean);
    

    int update(@Param("bean") PicBean bean);
    
    int delete(@Param("id")String id);
}
