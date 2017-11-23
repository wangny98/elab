package com.device.business.showManage.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.showManage.bean.ScienceShowBean;
import com.device.business.showManage.dao.mapper.ScienceShowMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class ScienceShowDao {
	@Autowired
	ScienceShowMapper scienceShowMapper;
	
	public ArrayList<ScienceShowBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<ScienceShowBean> list = new ArrayList<ScienceShowBean>();
		list = scienceShowMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = scienceShowMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(ScienceShowBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());
		bean.setDelete_flag(1);
		return scienceShowMapper.add(bean);
	}
	
	public ScienceShowBean load(String id) {
		return scienceShowMapper.load(id);
	}

	public int update(ScienceShowBean bean) {
		return scienceShowMapper.update(bean);
	}

	public int delete(String id) {
		return scienceShowMapper.delete(id);
	}
}
