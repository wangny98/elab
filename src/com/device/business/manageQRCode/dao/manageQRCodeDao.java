package com.device.business.manageQRCode.dao;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.manageQRCode.bean.manageQRCodeBean;
import com.device.business.manageQRCode.dao.mapper.manageQRCodeMapper;

public class manageQRCodeDao {
	@Autowired
	manageQRCodeMapper qrMapper;
	
	public int generateQRCode(manageQRCodeBean message){
		//判断是否已经存在，如果存在则进更新
		String fanumberString = message.getAsset_id();
		int r = qrMapper.isExist(fanumberString);
		if(r != 0) {
			qrMapper.deleteQRCode(message.getAsset_id());
		}
		
		return qrMapper.generateQRCode(message);
	}
	
	public void deleteQRCode(String bean){
		qrMapper.deleteQRCode(bean);
	}
	
	public manageQRCodeBean getQRCode(String id) {
		return qrMapper.getQRCode(id);
	}
	
	public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	       }
	       return flag;
	     }

}
