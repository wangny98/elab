package com.device.business.assetManage.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.dao.mapper.AssetVehicleMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetVehicleDao {
	@Autowired
	private AssetVehicleMapper assetVehicleMapper;
	
	public int insertAssetVehicle(AssetElement assetElement){
		return assetVehicleMapper.insertAssetVehicle(assetElement);
	}
	
	public int deleteAssetVehicle(String ids){
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetVehicleMapper.deleteAssetVehicle(tmpStrings[i]);
		}
		
		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int updateAssetVehicle(AssetElement assetElement){
		return assetVehicleMapper.updateAssetVehicle(assetElement);
	}
	
	public int isVehicleInfoExist(String fanumber){
		return assetVehicleMapper.isVehicleInfoExist(fanumber);
	}
}
