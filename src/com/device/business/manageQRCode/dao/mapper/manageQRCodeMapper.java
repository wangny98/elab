package com.device.business.manageQRCode.dao.mapper;

import com.device.business.manageQRCode.bean.manageQRCodeBean;

public interface manageQRCodeMapper {
	public int generateQRCode(manageQRCodeBean message);
	
	public int isExist(String fanumber);
	
	public void updateQRCode(manageQRCodeBean bean);
	
	public void deleteQRCode(String bean);
	
	public manageQRCodeBean getQRCode(String id);
}
