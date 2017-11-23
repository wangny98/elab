package com.device.business.sampleManage.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.sampleManage.bean.AuthBean;
import com.device.business.sampleManage.bean.BaseSample;
import com.device.business.sampleManage.bean.DemoBean;
import com.device.business.sampleManage.dao.mapper.SampleRegisterMapper;
import com.device.business.sampleManage.element.AuthElement;
import com.device.business.sampleManage.element.DemoElement;
import com.device.business.sampleManage.element.SampleRegisterElement;
import com.sun.org.apache.regexp.internal.recompile;

public class SampleRegisterDao {
	@Autowired
	SampleRegisterMapper sampleRegisterMapper;
	
	public List<BaseSample> querySampleRegister(BaseSample sample, int start, int limit){
		//分页工具
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<BaseSample> list = new  ArrayList<BaseSample>();
        list = sampleRegisterMapper.querySamples(sample, rows);
        for (int i = 0; i < list.size(); i++) {
        	List<DemoBean> demoBeans = sampleRegisterMapper.querySampleUnionDemo(list.get(i).getId());
        	int min  = 10;
        	for (int j = 0; j < demoBeans.size(); j++) {
				if (min > Integer.parseInt(demoBeans.get(j).getStatus())) {
					min = Integer.parseInt(demoBeans.get(j).getStatus());
				}
			}
        	if (Integer.parseInt(list.get(i).getStatus()) != 7) {
        		if (min == 1) {
    				list.get(i).setStatus("4");
    			}else if (min == 2) {
    				list.get(i).setStatus("6");
    			}
    		//	else if (min == 3) {
    		//		list.get(i).setStatus("7");
    		//	}
			}
		}
        return list;
	}
	
	public int checkSampleSubmit(String ids){
		String[] id = ids.split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			int r = sampleRegisterMapper.checkSampleSubmit(id[i]);
			if (r != 0) {
				result++;
			}
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public List<BaseSample> querySamples(BaseSample sample){
		//分页工具
        ArrayList<BaseSample> list = new  ArrayList<BaseSample>();
        list = sampleRegisterMapper.querySamples(sample);
        return list;
	}
	
	public int countsampleRegister(BaseSample sample){
		int i=0;
		i= sampleRegisterMapper.countSamples(sample);
		return i;
	}
	
	public int insertsampleRegister(SampleRegisterElement sampleRegisterElement){
		return sampleRegisterMapper.insertSample(sampleRegisterElement);
	}
	
	public int deleteSample(String ids){
		String[] id = ids.split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			int r = sampleRegisterMapper.deleteSample(id[i]);
			result += r;
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public int submitSample(String ids){
		String[] id = ids.split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			int r = sampleRegisterMapper.submitSample(id[i]);
			result += r;
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public int countSamplesByDate(String date){
		return sampleRegisterMapper.countSamplesByDate(date);
	}
	
	public int updateSample(SampleRegisterElement sampleRegisterElement){
		return sampleRegisterMapper.updateSample(sampleRegisterElement);
	}
	
	//////////////////////////////////////////////////////////////
	
	public ArrayList<DemoBean> getDemoForAuthResult(DemoBean demoBean){
		return sampleRegisterMapper.getDemoForAuthResult(demoBean);
	}
	
	public ArrayList<DemoBean> queryDemo(DemoBean demoBean, int start, int limit){
		RowBounds rows = new RowBounds(start, limit);
	    ArrayList<DemoBean> list = new  ArrayList<DemoBean>();
	    list = sampleRegisterMapper.queryDemo(demoBean, rows);
	    return list;
	}
	
	public ArrayList<DemoBean> queryDemo(DemoBean demoBean){
	    ArrayList<DemoBean> list = new  ArrayList<DemoBean>();
	    list = sampleRegisterMapper.queryDemo(demoBean);
	    return list;
	}
	
	public int countDemo(DemoBean demoBean){
		return sampleRegisterMapper.countDemo(demoBean);
	}
	
	public ArrayList<DemoBean> queryDemo2(BaseSample baseSample, int start, int limit){
		RowBounds rows = new RowBounds(start, limit);
	    ArrayList<DemoBean> list = new  ArrayList<DemoBean>();
	    list = sampleRegisterMapper.queryDemo2(baseSample, rows);
	    return list;
	}

	public int countDemo2(BaseSample baseSample){
		return sampleRegisterMapper.countDemo2(baseSample);
	}
	
	public int insertDemo(DemoElement demoElement){
		return sampleRegisterMapper.insertDemo(demoElement);
	}
	
	public int updateDemo(DemoElement demoElement){
		return sampleRegisterMapper.updateDemo(demoElement);
	}
	
	public int deleteDemo(String id){
		return sampleRegisterMapper.deleteDemo(id);
	}
	
	public int deleteDemoBySampleID(String ids){
		String[] id = ids.split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			int r = sampleRegisterMapper.deleteDemoBySampleID(id[i]);
			result += r;
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public int updateDemoStatus(DemoElement demoElement){
		return sampleRegisterMapper.updateDemoStatus(demoElement);
	}
	
	public int updateDemoStatusByBasicID(DemoElement demoElement){
		return sampleRegisterMapper.updateDemoStatusByBasicID(demoElement);
	}
	//////////////////////////////////////////////////////////////
	
	public int rejectSample(SampleRegisterElement sampleRegisterElement){
		String[] id = sampleRegisterElement.getId().split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			sampleRegisterElement.setId(id[i]);
			int r = sampleRegisterMapper.rejectSample(sampleRegisterElement);
			result += r;
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public int assignDemo(DemoElement demoElement){
		String[] id = demoElement.getId().split(",");
		int result = 0;
		for (int i = 0; i < id.length; i++) {
			demoElement.setId(id[i]);
			int r = sampleRegisterMapper.assignDemo(demoElement);
			result += r;
		}
		if (id.length == (result)) {
			return 1;
		}else{
			return 0;
		}
	}
	
	public int acceptSample(SampleRegisterElement sampleRegisterElement){
		return sampleRegisterMapper.acceptSample(sampleRegisterElement);
	}
	
	public int updateSampleStatus(SampleRegisterElement sampleRegisterElement){
		return sampleRegisterMapper.updateSampleStatus(sampleRegisterElement);
	}
	
	//////////////////////////////////////////////////////////////
	
	public int insertAuth(AuthElement authElement){
		return sampleRegisterMapper.insertAuth(authElement);
	}
	
	public AuthBean loadAuth(String id){
		return sampleRegisterMapper.loadAuth(id);
	}
	
	public int updateAuth(AuthElement authElement){
		return sampleRegisterMapper.updateAuth(authElement);
	}
	
	//////////////////////////////////////////////////////////////
	
	public int insertVerify(AuthElement authElement){
		return sampleRegisterMapper.insertVerify(authElement);
	}
	
	public int updateVerify(AuthElement authElement){
		return sampleRegisterMapper.updateVerify(authElement);
	}
	
	public AuthBean loadVerify(AuthBean authBean){
		return sampleRegisterMapper.loadVerify(authBean);
	}
	
	//////////////////////////////////////////////////////////////
	
	public int insertSign(SampleRegisterElement sampleRegisterElement){
		return  sampleRegisterMapper.insertSign(sampleRegisterElement);
	}
	
	public int updateSign(SampleRegisterElement sampleRegisterElement){
		return sampleRegisterMapper.updateSign(sampleRegisterElement);
	}
	
	public BaseSample loadSign(String id){
		return sampleRegisterMapper.loadSign(id);
	}
	
	//////////////////////////////////////////////////////////////
	
	public int registerDestroy(DemoElement demoElement){
		return sampleRegisterMapper.registerDestroy(demoElement);
	}
	
	public DemoBean loadDestroy(String id){
		return sampleRegisterMapper.loadDestroy(id);
	}
	//////////////////////////////////////////////////////////////
	
	public int registerReserve(DemoElement demoElement){
		return sampleRegisterMapper.registerReserve(demoElement);
	}
	
	public DemoBean loadReserve(String id){
		return sampleRegisterMapper.loadReserve(id);
	}
	
	//////////////////////////////////////////////////////////////
	public List<DictionaryBean> queryDictionary(String attr_name){
		ArrayList<DictionaryBean> list = new  ArrayList<DictionaryBean>();
        list = sampleRegisterMapper.queryDictionary(attr_name);
        
        return list;
	}
}
