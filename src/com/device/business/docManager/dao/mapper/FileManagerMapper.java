package com.device.business.docManager.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.docManager.bean.FileBean;

public interface FileManagerMapper {
    int addFile(@Param("bean") FileBean bean);

    List<FileBean> listFile(@Param("searchName") String searchName, @Param("types") String types,
            @Param("dirId") String dirId, RowBounds rows);

    int countFile(@Param("searchName") String searchName, @Param("types") String types, @Param("dirId") String dirId);

    FileBean getFile(@Param("id") String id);

    int delFiles(@Param("dirId") String dirId);
    
    int delSingleFile(@Param("id") String id);

    int increasePreview(@Param("id") String id);

    int increaseDownload(@Param("id") String id);
}
