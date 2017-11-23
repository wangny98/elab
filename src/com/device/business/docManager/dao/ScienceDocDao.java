package com.device.business.docManager.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.docManager.bean.ScienceBean;
import com.device.business.docManager.dao.mapper.ScienceManageMapper;

public class ScienceDocDao {
    @Autowired
    ScienceManageMapper scienceManageMapper;

    public ScienceBean load(String id) {
        ScienceBean bean = scienceManageMapper.info(id);
        return bean;
    }

    public int modify(ScienceBean bean) {
        int result = 0;
        result = scienceManageMapper.update(bean);
        return result;
    }
}
