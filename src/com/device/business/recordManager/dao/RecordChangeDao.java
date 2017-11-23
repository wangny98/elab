package com.device.business.recordManager.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.recordManager.bean.RecordChangeBean;
import com.device.business.recordManager.dao.mapper.RecordChangeMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RecordChangeDao {
	@Autowired
	RecordChangeMapper recordChangeMapper;
	
	public ArrayList<RecordChangeBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RecordChangeBean> list = new ArrayList<RecordChangeBean>();
		list = recordChangeMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = recordChangeMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(RecordChangeBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setDelete_flag(1);
		return recordChangeMapper.add(bean);
	}
	
	public RecordChangeBean load(String id) {
		return recordChangeMapper.load(id);
	}

	public int update(RecordChangeBean bean) {
		return recordChangeMapper.update(bean);
	}

	public int delete(String id) {
		return recordChangeMapper.delete(id);
	}
}
