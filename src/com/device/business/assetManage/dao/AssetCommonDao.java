package com.device.business.assetManage.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.dao.mapper.AssetCommonMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetCommonDao {
	@Autowired
	private AssetCommonMapper assetCommonMapper;
	
	
	public int insertCommonAsset(AssetElement assetElement){
		return assetCommonMapper.insertCommonAsset(assetElement);
	}
	
	public int deleteCommonAsset(String ids){
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetCommonMapper.deleteCommonAsset(tmpStrings[i]);
		}
		
		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int updateCommonAsset(AssetElement assetElement){
		return assetCommonMapper.updateCommonAsset(assetElement);
	}
}
