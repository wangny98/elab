package com.device.business.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.device.business.service.bean.AssetBean_info;
import com.device.business.service.bean.AssetBean_list;
import com.device.business.service.bean.Verify_list;

@WebService
public interface TransferDataWS  {
	
	/**
	 * 登陆
	 * 
	 * @param user
	 * @param pwd
	 * @return
	 */
	@WebMethod(operationName = "login")
	public String login(@WebParam(name="user")String user, @WebParam(name="pwd")String pwd); 
	
	/**
	 * 根据条件查询资产列表
	 * 
	 * @param id 资产编号
	 * @param name  资产名称
	 * @param type  资产类型
	 * @param state 资产状态
	 * @return
	 */
	@WebMethod(operationName = "getAssets")
	public List<AssetBean_list> getAssets(@WebParam(name="id")String id, @WebParam(name="name")String name, @WebParam(name="type")String type, @WebParam(name="state")String state); 
	
	/**
	 * 根据资产编号查询资产详细信息
	 * 
	 * @param text 资产编号
	 * @return
	 */
	@WebMethod(operationName = "getAssetById")
	public AssetBean_info getAssetById(@WebParam(name="assetId")String text); 
	
	/**
	 * 盘点管理
	 * 
	 * @param checker
	 * @param result
	 * @param memo
	 * @param assetno
	 * @return
	 */
	@WebMethod(operationName = "checkin")
	public boolean checkin(@WebParam(name="checker")String checker, @WebParam(name="result")String result, @WebParam(name="memo")String memo, 
			@WebParam(name="assetid")String assetid);
	
	/**
	 * 查询申请单列表
	 * 
	 * @param user 用户名
	 * @return
	 */
	@WebMethod(operationName = "getVerifyList")
	public List<Verify_list> getVerifyList(@WebParam(name="user")String user); 
	
	
	/**
	 * 根据申请单号和申请类型查询当前申请单中的资产列表
	 * 
	 * @param verifyId 申请ID
	 * @param verifyType 申请类型
	 * @return
	 */
	@WebMethod(operationName = "getAssetsByVerify")
	public List<AssetBean_list> getAssetsByVerify(@WebParam(name="verifyId")String verifyId, @WebParam(name="verifyType")String verifyType); 
	
	/**
	 * 根据申请单号和申请类型审核申请单
	 * 
	 * @param verifyId
	 * @param verifyType
	 * @param result 1：通过；0：不通过
	 * @return
	 */
	@WebMethod(operationName = "verify")
	public boolean verify(@WebParam(name="verifyId")String verifyId, @WebParam(name="verifyType")String verifyType, @WebParam(name="result")int result); 
}
