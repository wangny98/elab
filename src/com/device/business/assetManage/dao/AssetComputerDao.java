package com.device.business.assetManage.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.dao.mapper.AssetComputerMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetComputerDao {

	@Autowired
	private AssetComputerMapper assetComputerMapper;
	
	public int insertComputerAsset(AssetElement assetElement){
		return assetComputerMapper.insertComputerAsset(assetElement);
	}
	
	public int deleteComputerAsset(String ids){
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetComputerMapper.deleteComputerAsset(tmpStrings[i]);
		}
		
		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int updateComputerAsset(AssetElement assetElement){
		return assetComputerMapper.updateComputerAsset(assetElement);
	}
	
	public int isComputerInfoExist(String fanumber){
		return assetComputerMapper.isComputerInfoExist(fanumber);
	}
}
