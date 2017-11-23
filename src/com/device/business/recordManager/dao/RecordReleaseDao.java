package com.device.business.recordManager.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.recordManager.bean.RecordReleaseBean;
import com.device.business.recordManager.dao.mapper.RecordReleaseMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RecordReleaseDao {
	@Autowired
	RecordReleaseMapper recordReleaseMapper;
	
	public ArrayList<RecordReleaseBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RecordReleaseBean> list = new ArrayList<RecordReleaseBean>();
		list = recordReleaseMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = recordReleaseMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(RecordReleaseBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setDelete_flag(1);
		return recordReleaseMapper.add(bean);
	}
	
	public RecordReleaseBean load(String id) {
		return recordReleaseMapper.load(id);
	}

	public int update(RecordReleaseBean bean) {
		return recordReleaseMapper.update(bean);
	}

	public int delete(String id) {
		return recordReleaseMapper.delete(id);
	}
}
