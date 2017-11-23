package com.device.business.assetManage.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.dao.mapper.AssetInstrumentMapper;
import com.device.business.assetManage.element.AssetElement;

public class AssetInstrumentDao {

	@Autowired
	private AssetInstrumentMapper assetInstrumentMapper;
	
	public int insertInstrumentAsset(AssetElement assetElement){
		return assetInstrumentMapper.insertInstrumentAsset(assetElement);
	}
	
	public ArrayList<AssetBean> queryInstrumentAsset(AssetBean assetBean, int start, int limit){
		//分页工具
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<AssetBean> list = new  ArrayList<AssetBean>();
        list = assetInstrumentMapper.queryInstrumentAsset(assetBean, rows);
        return list;
	}
	
	public int countInstrumentAsset(AssetBean assetBean){
		return assetInstrumentMapper.countInstrumentAsset(assetBean);
	}
	
	public int deleteInstrumentAsset(String ids){
		String[] tmpStrings = ids.split(",");
		int sum = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetInstrumentMapper.deleteInstrumentAsset(tmpStrings[i]);
		}
		if (sum == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public ArrayList<AssetBean> queryInstrumentAssetById(String id){
		ArrayList<AssetBean> list = new  ArrayList<AssetBean>();
	    list = assetInstrumentMapper.queryInstrumentAssetById(id);
	    return list;
	}
	
	public int updateInstrumentAsset(AssetElement assetElement){
		return assetInstrumentMapper.updateInstrumentAsset(assetElement);
	}
	
	public int submitInstrumentAsset(String ids){
		String[] tmpStrings = ids.split(",");
		int sum = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetInstrumentMapper.submitInstrumentAsset(tmpStrings[i]);
		}
		if (sum == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}
}
