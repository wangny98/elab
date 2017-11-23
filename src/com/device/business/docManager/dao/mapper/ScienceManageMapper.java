package com.device.business.docManager.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.device.business.docManager.bean.ScienceBean;

public interface ScienceManageMapper {

    int add(@Param("bean") ScienceBean bean);

    int update(@Param("bean") ScienceBean bean);

    int delete(@Param("id") String id);

    int deleteMore(@Param("dirId") String dirId);

    ScienceBean info(@Param("id") String id);
}
