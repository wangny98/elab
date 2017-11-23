package com.device.business.transferManager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;

import com.device.business.transferManager.bean.ExamineBean;
import com.device.business.transferManager.dao.mapper.ExamineMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class ExamineManageDao {

	@Autowired
	ExamineMapper examineMaper;
	
	public List<ExamineBean> query(String searchName,int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<ExamineBean> list = new ArrayList<ExamineBean>();
		list = examineMaper.query("%"+searchName+"%", rows);
		return list;
	}
	
	public int count(String searchName){
		int i=0;
		i= examineMaper.count("%"+searchName+"%");
		return i;
	}
	
	public int add(ExamineBean bean){
		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setExamine_id(login.getUserId());
		bean.setExamine_name(login.getUserFullName());
		bean.setExamine_time(new Date());
		return examineMaper.add(bean);
	}
	
	public ExamineBean load(String id){
		return examineMaper.load(id);
	}
	
	public int update(ExamineBean bean){
		return examineMaper.update(bean);
	}
	
	public int delete(String id){
		return examineMaper.delete(id);
	}
	
}
