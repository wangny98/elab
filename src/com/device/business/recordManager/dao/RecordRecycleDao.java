package com.device.business.recordManager.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.recordManager.bean.RecordRecycleBean;
import com.device.business.recordManager.dao.mapper.RecordRecycleMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RecordRecycleDao {
	@Autowired
	RecordRecycleMapper recordRecycleMapper;
	
	public ArrayList<RecordRecycleBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RecordRecycleBean> list = new ArrayList<RecordRecycleBean>();
		list = recordRecycleMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = recordRecycleMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(RecordRecycleBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setDelete_flag(1);
		return recordRecycleMapper.add(bean);
	}
	
	public RecordRecycleBean load(String id) {
		return recordRecycleMapper.load(id);
	}

	public int update(RecordRecycleBean bean) {
		return recordRecycleMapper.update(bean);
	}

	public int delete(String id) {
		return recordRecycleMapper.delete(id);
	}
}
