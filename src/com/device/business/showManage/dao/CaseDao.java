package com.device.business.showManage.dao;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.showManage.bean.CaseBean;
import com.device.business.showManage.dao.mapper.CaseMapper;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class CaseDao {
	
	@Autowired
	CaseMapper caseMapper;
	
	public ArrayList<CaseBean> query(String searchName, int start,int limit){
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<CaseBean> list = new ArrayList<CaseBean>();
		list = caseMapper.query("%" + searchName + "%", rows);
		return list;
	}
	
	public int count(String searchName) {
		int i = 0;
		i = caseMapper.count("%" + searchName + "%");
		return i;
	}
	
	public int add(CaseBean bean) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());
		bean.setDelete_flag(1);
		return caseMapper.add(bean);
	}
	
	public CaseBean load(String id) {
		return caseMapper.load(id);
	}

	public int update(CaseBean bean) {
		return caseMapper.update(bean);
	}

	public int delete(String id) {
		return caseMapper.delete(id);
	}
}
