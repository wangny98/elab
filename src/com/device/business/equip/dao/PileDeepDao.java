package com.device.business.equip.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.PperdeepBean;
import com.device.business.equip.bean.PperdeepBeanExample;
import com.device.business.equip.dao.mapper.PperdeepBeanMapper;

public class PileDeepDao {

	@Autowired
	PperdeepBeanMapper mapper;
	
	public Map<String, Object> selectMachineInfo(String pileNumber) {
		List<Map<String, Object>> list = mapper.selectMachineInfo(pileNumber,new RowBounds(1,1));
		if(null!=list&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public List<PperdeepBean> selectByDown(String pileNumber){
		PperdeepBeanExample example = new PperdeepBeanExample();
		example.or().andPileNumberEqualTo(pileNumber).andPileDeepLessThanOrEqualTo(new BigDecimal(0));
		example.setOrderByClause("Pile_Deep");
		return mapper.selectByExample(example);
	};
	
	public List<PperdeepBean> selectByUP(String pileNumber){
		PperdeepBeanExample example = new PperdeepBeanExample();
		example.or().andPileNumberEqualTo(pileNumber).andPileDeepGreaterThanOrEqualTo(new BigDecimal(0));
		example.setOrderByClause("Pile_Deep DESC");
		return mapper.selectByExample(example);
	};
	
	public List<PperdeepBean> selectByAll(String pileNumber){
		PperdeepBeanExample example = new PperdeepBeanExample();
		example.or().andPileNumberEqualTo(pileNumber);
		example.setOrderByClause("Pile_Deep");
		return mapper.selectByExample(example);
	};
	
	public  List<PperdeepBean> selectByAbs(String pileNumber){
		PperdeepBeanExample example = new PperdeepBeanExample();
		example.or().andPileNumberEqualTo(pileNumber);
		return mapper.selectByABS(example);
	};
}
