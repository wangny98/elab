package com.device.business.sampleManage.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import jxl.demo.Demo;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.sampleManage.bean.AuthBean;
import com.device.business.sampleManage.bean.BaseSample;
import com.device.business.sampleManage.bean.DemoBean;
import com.device.business.sampleManage.element.AuthElement;
import com.device.business.sampleManage.element.DemoElement;
import com.device.business.sampleManage.element.SampleRegisterElement;

/**
 * @author Eric
 *
 */
public interface SampleRegisterMapper {

	/**查询列表
	 * @param sample
	 * @param rowBounds
	 * @return
	 */
	ArrayList<BaseSample> querySamples(BaseSample sample, RowBounds rowBounds);
	
	/**
	 * @param id
	 * @return
	 */
	int checkSampleSubmit(String id);
	
	/**查询列表
	 * @param sample
	 * @return
	 */
	ArrayList<BaseSample> querySamples(BaseSample sample);
	
	/**列表样本个数
	 * @param searchName
	 * @return
	 */
	int countSamples(BaseSample sample);
	
	/**插入
	 * @param sampleRegisterElement
	 * @return
	 */
	int insertSample(SampleRegisterElement sampleRegisterElement);
	
	/**更新
	 * @param sampleRegisterElement
	 * @return
	 */
	int updateSample(SampleRegisterElement sampleRegisterElement);
	
	/**刪除
	 * @param id
	 * @return
	 */
	int deleteSample(String id);
	
	/**提交
	 * @param sampleRegisterElement
	 * @return
	 */
	int submitSample(String id);
	
	/**根据日期获取编号
	 * @param date
	 * @return
	 */
	int countSamplesByDate(String date);
	
	/**获取
	 * @param id
	 * @return
	 */
	List<DemoBean> querySampleUnionDemo(String id);
	
	//////////////////////////////////////////////////////////////
	
	/**为了生成鉴定报告查询小样信息
	 * @param demoBean
	 * @return
	 */
	ArrayList<DemoBean> getDemoForAuthResult(DemoBean demoBean);
	
	/**查询小样
	 * @param demoBean
	 * @return
	 */
	ArrayList<DemoBean> queryDemo(DemoBean demoBean, RowBounds rowBounds); 
	
	/**
	 * @param demoBean
	 * @return
	 */
	ArrayList<DemoBean> queryDemo(DemoBean demoBean); 
	
	/**小样数目
	 * @param demoBean
	 * @return
	 */
	int countDemo(DemoBean demoBean);
	
	/**
	 * @param demoBean
	 * @param rowBounds
	 * @return
	 */
	ArrayList<DemoBean> queryDemo2(BaseSample baseSample, RowBounds rowBounds); 
	
	/**
	 * @param demoBean
	 * @return
	 */
	int countDemo2(BaseSample baseSample);
	
	/**插入小样
	 * @param demoElement
	 * @return
	 */
	int insertDemo(DemoElement demoElement);
	
	/**更新小样
	 * @param demoElement
	 * @return
	 */
	int updateDemo(DemoElement demoElement);
	
	/**删除小样
	 * @param id
	 * @return
	 */
	int deleteDemo(String id);
	
	/**在基本信息里刪除小樣
	 * @param id
	 * @return
	 */
	int deleteDemoBySampleID(String id);
	
	/**更新demo状态
	 * @param demoElement
	 * @return
	 */
	int updateDemoStatus(DemoElement demoElement);
	
	/**
	 * @param demoElement
	 * @return
	 */
	int updateDemoStatusByBasicID(DemoElement demoElement);
	
	//////////////////////////////////////////////////////////////

	/**退回送样单
	 * @param sampleRegisterElement
	 * @return
	 */
	int rejectSample(SampleRegisterElement sampleRegisterElement);
	
	/**分配小样
	 * @param demoElement
	 * @return
	 */
	int assignDemo(DemoElement demoElement);
	
	/**接收送样单
	 * @param sampleRegisterElement
	 * @return
	 */
	int acceptSample(SampleRegisterElement sampleRegisterElement);
	
	/**更新送样单状态
	 * @param sampleRegisterElement
	 * @return
	 */
	int updateSampleStatus(SampleRegisterElement sampleRegisterElement);
	
	//////////////////////////////////////////////////////////////
	
	/**插入鉴定信息
	 * @param authElement
	 * @return
	 */
	int insertAuth(AuthElement authElement);
	
	/**加载鉴定信息
	 * @param id
	 * @return
	 */
	AuthBean loadAuth(String id);
	
	/**更新鉴定信息
	 * @param authElement
	 * @return
	 */
	int updateAuth(AuthElement authElement);
	
	//////////////////////////////////////////////////////////////
	
	/**插入复核信息
	 * @param authElement
	 * @return
	 */
	int insertVerify(AuthElement authElement);
	
	/**更新复核信息
	 * @param authElement
	 * @return
	 */
	int updateVerify(AuthElement authElement);
	
	/**加载复核信息
	 * @param authBean
	 * @return
	 */
	AuthBean loadVerify(AuthBean authBean);
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param sampleRegisterElement
	 * @return
	 */
	int insertSign(SampleRegisterElement sampleRegisterElement);
	
	/**
	 * @param sampleRegisterElement
	 * @return
	 */
	int updateSign(SampleRegisterElement sampleRegisterElement);
	
	/**
	 * @param id
	 * @return
	 */
	BaseSample loadSign(String id);
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param demoElement
	 * @return
	 */
	int registerDestroy(DemoElement demoElement);
	
	/**
	 * @param id
	 * @return
	 */
	DemoBean loadDestroy(String id);
	
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param demoElement
	 * @return
	 */
	int registerReserve(DemoElement demoElement);
	
	/**
	 * @param id
	 * @return
	 */
	DemoBean loadReserve(String id);
	
	//////////////////////////////////////////////////////////////
	/**查询字典
	 * @param attr_name
	 * @return
	 */
	ArrayList<DictionaryBean> queryDictionary(String attr_name);
	
}
