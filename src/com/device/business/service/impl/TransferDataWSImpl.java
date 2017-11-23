package com.device.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.dao.AssetDao;
import com.device.business.assetManage.dao.AssetHouseholdDao;
import com.device.business.assetManage.dao.AssetInstrumentDao;
import com.device.business.checkManager.dao.CheckManageDao;
import com.device.business.checkManager.element.CheckManageElement;
import com.device.business.classificationManager.dao.ClassificationDao;
import com.device.business.dictionary.bean.DictionaryBean;
import com.device.business.dictionary.dao.DictionaryDao;
import com.device.business.innerAllocate.dao.InnerAllocateDao;
import com.device.business.scrapManager.dao.ScrapManageDao;
import com.device.business.service.TransferDataWS;
import com.device.business.service.bean.AssetBean_info;
import com.device.business.service.bean.AssetBean_list;
import com.device.business.service.bean.Verify_list;
import com.device.business.transferManager.dao.TransferManageDao;
import com.device.business.userManage.bean.UserBean;
import com.device.business.userManage.dao.UserDao;
import com.device.util.PrimaryKeyUtil;

@WebService
public class TransferDataWSImpl implements TransferDataWS {
	@Autowired
	AssetDao assetDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	TransferManageDao transferDao;
	
	@Autowired
	InnerAllocateDao innerAllocateDaoDao;
	
	@Autowired
	ScrapManageDao scrapManageDao;
	
	@Autowired
	AssetHouseholdDao assetHouseholdDao;
 
	@Autowired
	AssetInstrumentDao assetInstrumentDao;
	
	@Autowired
	private CheckManageDao checkManageDao; 
	
	@Autowired
	ClassificationDao classificationDao;
	
	@Autowired
    DictionaryDao dictionaryDao;
	
	@WebMethod(operationName = "login")
	public String login(@WebParam(name="user")String user, @WebParam(name="pwd")String pwd) {
		String result = "";
		
		UserBean login_user = userDao.validateUser(user);

        if (!CommUtils.isNullOrBlank(login_user) && !CommUtils.isNullOrBlank(login_user.getId())) {

            if (!CommUtils.isNullOrBlank(login_user.getPassword()) && login_user.getPassword().equals(pwd)) {
            	result = login_user.getFullname();
            }
        }
        
        return result;
	}
	
	@WebMethod(operationName = "getAssets")
	public List<AssetBean_list> getAssets(@WebParam(name="id")String id, @WebParam(name="name")String name, @WebParam(name="type")String type, @WebParam(name="state")String state) {
		List<AssetBean_list> result = new ArrayList<AssetBean_list>();
		List<AssetBean> tmp = null;
		
		AssetBean assetBean = new AssetBean();
		assetBean.setFanumber(id);
		assetBean.setFaname(name);
		assetBean.setFaclassification(type);
		assetBean.setStatus(state);
		
		tmp  = assetDao.queryAssetSearch(assetBean);
		
		for(AssetBean asset : tmp) {
			AssetBean_list ret_asset = new AssetBean_list();
			ret_asset.setId(asset.getId());
			ret_asset.setAsset_name(asset.getFaname());
			ret_asset.setAsset_no(asset.getFanumber());
			ret_asset.setAsset_type(asset.getFaclassification());
			ret_asset.setAsset_status(asset.getStatusText());
			
			result.add(ret_asset);
		}
		
		return result;
	}

	@WebMethod(operationName = "getAssetById")
	public AssetBean_info getAssetById(@WebParam(name="assetId")String text) {
		AssetBean asset = assetDao.getAssetByID(text);

		AssetBean_info result = new AssetBean_info();
			
		result.setId(asset.getId());
		result.setAsset_name(asset.getFaname());
		result.setAsset_nation_no(asset.getFagnumber());
		result.setAsset_no(asset.getFanumber());
		result.setAsset_type(classificationDao.queryClassName(asset.getFaclassification()));
		result.setAsset_status(asset.getStatus());
		result.setAccount_time(asset.getFaaccountdate());
		result.setValue(asset.getFavalue());
		result.setVendor(asset.getFamanufacturer());
		result.setRepair_contact(asset.getFasuppliertel());
		result.setUser(asset.getFauser());
		result.setUse_age(asset.getFauseage());
		result.setAsset_size(asset.getFamodel());
		result.setIs_measure("0".equals(asset.getFameasure())? "否":"是");
		result.setIs_checked("否");
		result.setProduct_no(asset.getFafnum());
		
		List<DictionaryBean> db = dictionaryDao.getDictionaryBeanList("使用方向");
		String direction = asset.getFadfu();
		for (DictionaryBean bean : db) {
			if (direction.equals(bean.getAttr_key()))
				result.setUse_direction(bean.getAttr_value());
		}
		
		return result;
	}
	
	@WebMethod(operationName = "checkin")
	public boolean checkin(@WebParam(name="checker")String checker, @WebParam(name="result")String result, @WebParam(name="memo")String memo, 
			@WebParam(name="assetid")String assetid) {
		boolean rel = false;
		
		CheckManageElement element = new CheckManageElement();
		
		AssetBean asset = assetDao.getAssetByID(assetid);
		
		element.setFanumber(asset.getFanumber());
		element.setPdresult(result);
		element.setPdname(checker);
		element.setRemark(memo);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		element.setPddate(format.format(new Date()));
		
		if (checkManageDao.isPdExist(element.getFanumber()) == 0) {
			element.setId(PrimaryKeyUtil.getSeq());
			checkManageDao.addPd(element);
			
			rel = true;
		}else {
			checkManageDao.updPd(element);
			
			rel = true;
		}
		
		return rel;
	}
	
	@WebMethod(operationName = "getVerifyList")
	public List<Verify_list> getVerifyList(@WebParam(name="user")String user) {
		List<Verify_list> result = new ArrayList<Verify_list>();
		
		/*//部门外调拨
		List<TransferBean> outer_list = new ArrayList<TransferBean>();
		outer_list = transferDao.queryTransfer("", 0, 100,"(3)");
		
		//部门内调拨
		InnerAllocateBean innerAllocateBean  = new InnerAllocateBean();
		innerAllocateBean.setStatus("3");
		List<InnerAllocateBean> inner_list = new  ArrayList<InnerAllocateBean>();
		inner_list = innerAllocateDaoDao.queryInnerAllocate(innerAllocateBean, 0, 100);
		
		//报废申请
		List<ScrapBean> scrap_list = new ArrayList<ScrapBean>();
		scrap_list = scrapManageDao.queryTransfer("", 0, 100, "(3)");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for(TransferBean item : outer_list) {
			Verify_list tmp = new Verify_list();
			
			tmp.setId(item.getId());
			tmp.setVerify_dept(item.getOrg_unit());
			tmp.setVerify_no(item.getTransfer_no());
			tmp.setVerify_time(format.format(item.getCreate_time()));
			tmp.setVerify_type("部门外部调拨申请");
			tmp.setVerify_user(item.getUser_name());
			
			result.add(tmp);
		}
		
		for(InnerAllocateBean item : inner_list) {
			Verify_list tmp = new Verify_list();
			
			tmp.setId(String.valueOf(item.getId()));
			tmp.setVerify_dept(item.getAlloc_depart());
			tmp.setVerify_no(String.valueOf(item.getId()));
			tmp.setVerify_time(item.getAlloc_date());
			tmp.setVerify_type("部门内部调拨申请");
			tmp.setVerify_user(item.getAlloc_user());
			
			result.add(tmp);
		}
		
		for(ScrapBean item : scrap_list) {
			Verify_list tmp = new Verify_list();
			
			tmp.setId(item.getId());
			tmp.setVerify_dept(item.getScrap_department());
			tmp.setVerify_no(item.getScrap_no());
			tmp.setVerify_time(format.format(item.getCreate_time()));
			tmp.setVerify_type("报废申请");
			tmp.setVerify_user(item.getScrap_user_name());
			
			result.add(tmp);
		}*/
		
		return result;
	}
	
	@WebMethod(operationName = "getAssetsByVerify")
	public List<AssetBean_list> getAssetsByVerify(@WebParam(name="verifyId")String verifyId, @WebParam(name="verifyType")String verifyType) {
		List<AssetBean_list> result = new ArrayList<AssetBean_list>();
		/*if("部门外部调拨申请".equals(verifyType)) { //部门外调拨
			
			List<BasePropertyBean> outer_list = transferDao.queryCheckProperty(verifyId, 0, 100);
			for(BasePropertyBean asset: outer_list) {
				AssetBean_list tmp = new AssetBean_list();
				
				tmp.setId(asset.getId());
				tmp.setAsset_name(asset.getFaname());
				tmp.setAsset_no(asset.getFanumber());
				tmp.setAsset_type(asset.getFaclassification());
				tmp.setAsset_status("外部调拨审核中");
				
				result.add(tmp);
			}
			
		} else if ("部门内部调拨申请".equals(verifyType)) {
			
			String[] ids = innerAllocateDaoDao.queryInnerAllocateAssetID(verifyId).split(",");
			String[] tables = innerAllocateDaoDao.queryInnerAllocateTabletype(verifyId).split(",");
			for(int i = 0; i < ids.length; i++) {
				AssetBean_list tmp = new AssetBean_list();
					
				List<AssetBean> assets = new ArrayList<AssetBean>();
				if("1".equals(tables[i]))
					assets = assetDao.queryAssetById(ids[0]);
				else if("2".equals(tables[i]))
					assets = assetInstrumentDao.queryInstrumentAssetById(ids[0]);
				else 
					assets = assetHouseholdDao.queryAssetHouseholdById(ids[0]);
				
				if(assets.size() > 0) {
					AssetBean asset = assets.get(0);
					tmp.setId(asset.getId());
					tmp.setAsset_name(asset.getFaname());
					tmp.setAsset_no(asset.getFanumber());
					tmp.setAsset_type(asset.getFaclassification());
					tmp.setAsset_status(asset.getStatusText());
					
					result.add(tmp);
				}
			}
		} else { //报废申请
			List<BasePropertyBean> scrap = scrapManageDao.queryCheckProperty(verifyId, 0, 100);
			
			for(BasePropertyBean asset: scrap) {
				AssetBean_list tmp = new AssetBean_list();
				
				tmp.setId(asset.getId());
				tmp.setAsset_name(asset.getFaname());
				tmp.setAsset_no(asset.getFanumber());
				tmp.setAsset_type(asset.getFaclassification());
				tmp.setAsset_status("报废审核中");
				
				result.add(tmp);
			}
		}*/
		
		return result;
	}
	
	@WebMethod(operationName = "verify")
	public boolean verify(@WebParam(name="verifyId")String verifyId, @WebParam(name="verifyType")String verifyType, @WebParam(name="result")int result) {
		boolean res = false;
		/*String state = "3";
		
		if(result == 0) {
			state = "4";
		} else if (result == 1) {
			state = "5";
		}
		
		if("部门外部调拨申请".equals(verifyType)) { //部门外调拨
			transferDao.updatePropertyStatue(verifyId, state);
			res = true;
		} else if ("部门内部调拨申请".equals(verifyType)) {
			if(result == 0) {
				InnerAllocateApplyElement innerAllocateApplyElement = new InnerAllocateApplyElement();
				innerAllocateApplyElement.setId(verifyId);
				innerAllocateApplyElement.setStatus("4");
				innerAllocateDaoDao.submitInnerAllocate(innerAllocateApplyElement);
				String[] fanumber = innerAllocateDaoDao.queryInnerAllocateAssetID(verifyId).split(",");
				String[] tablename = innerAllocateDaoDao.queryInnerAllocateTabletype(verifyId).split(",");
				for (int k = 0; k < fanumber.length; k++) {
					TableName t = new TableName(); 
					String tabString = t.getTable(tablename[k]);
					InnerAllocateBean innerAllocateBean = new InnerAllocateBean();
					innerAllocateBean.setTabletype(tabString);
					innerAllocateBean.setStatus("1");
					innerAllocateBean.setAsset_id(fanumber[k]);
					assetDao.updateAssetStatus(innerAllocateBean);
				}
			} else if (result == 1) {
				LoginElement login = AuthorityContext.getLoginElement();
				InnerAllocateApplyElement innerAllocateApplyElement = new InnerAllocateApplyElement();
				innerAllocateApplyElement.setId(verifyId);
				innerAllocateApplyElement.setStatus("5");
				innerAllocateApplyElement.setVerify_date(new Date());
				innerAllocateApplyElement.setVerifyer(login.getUserFullName());
				innerAllocateDaoDao.verifyInnerAllocate(innerAllocateApplyElement);
				String[] fanumber = innerAllocateDaoDao.queryInnerAllocateAssetID(verifyId).split(",");
				String[] tablename = innerAllocateDaoDao.queryInnerAllocateTabletype(verifyId).split(",");
				for (int k = 0; k < fanumber.length; k++) {
					TableName t = new TableName(); 
					String tabString = t.getTable(tablename[k]);
					InnerAllocateBean innerAllocateBean = new InnerAllocateBean();
					innerAllocateBean.setTabletype(tabString);
					innerAllocateBean.setStatus("3");
					innerAllocateBean.setAsset_id(fanumber[k]);
					assetDao.updateAssetStatus(innerAllocateBean);
				}
			}
			res = true;
		} else { //报废申请
			scrapManageDao.updatePropertyStatue(verifyId, state);
			res = true;
		}*/
		
		return res;
	}

}
