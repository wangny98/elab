package com.device.business.checkManager.dao.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.checkManager.bean.CheckManageBean;
import com.device.business.checkManager.bean.SearchBean;
import com.device.business.checkManager.element.CheckManageElement;

public interface CheckManageMapper {

	public List<AssetBean> queryPd(CheckManageBean checkManageBean, RowBounds rowBounds);
	
	public List<AssetBean> queryPd(CheckManageBean checkManageBean);
	
	public int countPd(CheckManageBean checkManageBean);
	
	public int countProperty();

	public int updPd(CheckManageElement checkManageElement );
	
	public int addPd(CheckManageElement checkManageElement );
	
	public int isPdExist(String fanumber);
}
