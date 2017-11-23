package com.device.business.recordManager.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.recordManager.bean.RecordControlListBean;
import com.device.business.recordManager.dao.mapper.RecordControlListMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class RecordControlDao {
	@Autowired
	RecordControlListMapper recordControlListMapper;
	
	public ArrayList<RecordControlListBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RecordControlListBean> list = new ArrayList<RecordControlListBean>();
		list = recordControlListMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = recordControlListMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(RecordControlListBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setDelete_flag(1);
		return recordControlListMapper.add(bean);
	}
	
	public RecordControlListBean load(String id) {
		return recordControlListMapper.load(id);
	}

	public int update(RecordControlListBean bean) {
		return recordControlListMapper.update(bean);
	}

	public int delete(String id) {
		return recordControlListMapper.delete(id);
	}
}
