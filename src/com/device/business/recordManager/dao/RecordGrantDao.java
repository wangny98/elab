package com.device.business.recordManager.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.recordManager.bean.RecordGrantBean;
import com.device.business.recordManager.dao.mapper.RecordGrantMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RecordGrantDao {
	@Autowired
	RecordGrantMapper recordGrantMapper;
	
	public ArrayList<RecordGrantBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RecordGrantBean> list = new ArrayList<RecordGrantBean>();
		list = recordGrantMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = recordGrantMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(RecordGrantBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setDelete_flag(1);
		return recordGrantMapper.add(bean);
	}
	
	public RecordGrantBean load(String id) {
		return recordGrantMapper.load(id);
	}

	public int update(RecordGrantBean bean) {
		return recordGrantMapper.update(bean);
	}

	public int delete(String id) {
		return recordGrantMapper.delete(id);
	}
}
