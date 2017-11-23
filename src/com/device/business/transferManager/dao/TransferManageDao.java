package com.device.business.transferManager.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.login.element.LoginElement;
import com.device.business.transferManager.dao.mapper.TransferMapper;
import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.business.transferManager.bean.TransferBean;
import com.device.filter.AuthorityContext;
import com.device.util.IdAppendUtil;
import com.device.util.PrimaryKeyUtil;

public class TransferManageDao {
	@Autowired
	TransferMapper transferMapper;

	public List<TransferBean> queryTransfer(String searchName, int start,
			int limit,String states) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<TransferBean> list = new ArrayList<TransferBean>();
		list = transferMapper.query("%" + searchName + "%", rows,states);
		return list;
	}

	public int count(String searchName,String states) {
		int i = 0;
		i = transferMapper.count("%" + searchName + "%",states);
		return i;
	}

	@Transactional
	public int add(TransferBean bean, String property_ids) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_id(login.getUserId());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());

		if (property_ids != null) {
			String[] ids = property_ids.split(",");

			String table_name = null;

			for (int i = 0; i < ids.length; i++) {
				transferMapper.addUnion(bean.getId(), table_name, ids[i]);
			}
			if(bean.getState()!=null&&bean.getState().equals("7")){

				transferMapper.updatePropertyStatue4Basic(IdAppendUtil.Array2String(ids), 3);
			}
		}
		return transferMapper.add(bean);
	}

	public TransferBean load(String id) {
		return transferMapper.load(id);
	}

	public int update(TransferBean bean) {
		return transferMapper.update(bean);
	}

	public int delete(String id) {
		return transferMapper.delete(id);
	}
/******************************************************************/
	public List<BasePropertyBean> listBaseProperty(String searchName,
			int start, int limit) {

		RowBounds rows = new RowBounds(start, limit);
		ArrayList<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		list = transferMapper.listBaseProperty("%" + searchName + "%", rows);
		return list;
	}

	public int countBaseProperty(String searchName) {
		int count = 0;
		count = transferMapper.countBaseProperty("%" + searchName + "%");
		return count;
	}

	public List<BasePropertyBean> queryCheckProperty(String transfer_id,
			int start, int limit) {
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();

		RowBounds rows = new RowBounds(start, limit);
		System.out.println("transfer_id : "+transfer_id);
		ArrayList<String> idList= transferMapper.getPropertyConcat(transfer_id, rows);
		String ids =IdAppendUtil.Collection2String(idList);
		System.out.println("ids : "+ids);
		list = transferMapper.getCheckBaseProperty(ids);

		return list;
	}

	public int countCheckProperty(String transfer_id) {
		int count = 0;
		count = transferMapper.countCheckBaseProperty(transfer_id);
		return count;
	}
	/******************************************************************/	
	@Transactional
	public void updatePropertyStatue(String transfer_id,String state) {
		
		System.out.println("transfer_id : "+transfer_id+"; state : "+state);
		ArrayList<String> idList= transferMapper.getPropertyConcatAll(transfer_id);
		String ids =IdAppendUtil.Collection2String(idList);
		System.out.println("ids : "+ids);
		transferMapper.updateTransferState(transfer_id,state);
		int status = 1;
		if(state.equals("2")||state.equals("3")){
			
			if(state.equals("2"))//撤销
			{
				status=1;
			}else if(state.equals("3")){
				status=2;
			}
			
			transferMapper.updatePropertyStatue4Basic(ids, status);
			transferMapper.updatePropertyStatue4House(ids, status);
			transferMapper.updatePropertyStatue4Instr(ids, status);
		}
		if(state.equals("4")||state.equals("5")){
			LoginElement login = AuthorityContext.getLoginElement();
			transferMapper.updateExamineInfo(transfer_id,new Date(),login.getUserFullName());
		}
	}
}
