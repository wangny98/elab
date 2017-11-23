package com.device.business.assetManage.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.dao.mapper.AssetHouseMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetHouseDao {
	@Autowired
	private AssetHouseMapper assetHouseMapper;
	
	public int insertHouseAsset(AssetElement assetElement){
		return assetHouseMapper.insertHouseAsset(assetElement);
	}
	
	public int deleteHouseAsset(String ids){
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetHouseMapper.deleteHouseAsset(tmpStrings[i]);
		}
		
		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int updateHouseAsset(AssetElement assetElement){
		return assetHouseMapper.updateHouseAsset(assetElement);
	}
	
	public int isHouseInfoExist(String fanumber){
		return assetHouseMapper.isHouseInfoExist(fanumber);
	}

}
