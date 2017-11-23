package com.device.business.assetManage.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.dao.mapper.AssetHouseholdMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetHouseholdDao {

	@Autowired
	private AssetHouseholdMapper assetHouseholdMapper;
	
	public int insertAssetHousehold(AssetElement assetElement){
		return assetHouseholdMapper.insertAssetHousehold(assetElement);
	}
	
	public ArrayList<AssetBean> queryAssetHousehold(AssetBean assetBean, int start, int limit){
		//分页工具
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<AssetBean> list = new  ArrayList<AssetBean>();
        list = assetHouseholdMapper.queryAssetHousehold(assetBean, rows);
        return list;
	}
	
	public  ArrayList<AssetBean> queryAssetHouseholdById(String id){
		ArrayList<AssetBean> list = new  ArrayList<AssetBean>();
        list = assetHouseholdMapper.queryAssetHouseholdById(id);
        return list;
	}
	
	public int countAssetHousehold(AssetBean assetBean){
		return assetHouseholdMapper.countAssetHousehold(assetBean);
	}
	
	public int  deleteAssetHousehold(String ids){
		String[] tmpStrings = ids.split(",");
		int sum  = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetHouseholdMapper.deleteAssetHousehold(tmpStrings[i]);
		}
		if(sum == tmpStrings.length){
			return 1;
		} else {
			return 0;
		}
	}
	
	public int updateAssetHousehold(AssetElement assetElement){
		return assetHouseholdMapper.updateAssetHousehold(assetElement);
	}
	
	public int submitAssetHousehold(String ids){
		String[] tmpStrings = ids.split(",");
		int sum  = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetHouseholdMapper.submitAssetHousehold(tmpStrings[i]);
		}
		if(sum == tmpStrings.length){
			return 1;
		} else {
			return 0;
		}
	}
}
