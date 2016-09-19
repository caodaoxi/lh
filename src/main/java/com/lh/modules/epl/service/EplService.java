/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.epl.service;

import com.lh.common.config.MyCache;
import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.common.utils.StringUtil;
import com.lh.common.utils.StringUtils;
import com.lh.modules.common.entity.PushAlarmEpl;
import com.lh.modules.common.entity.PushEpl;
import com.lh.modules.common.servie.CommonService;
import com.lh.modules.epl.dao.AlarmEplDao;
import com.lh.modules.epl.dao.AlarmEplRelationDao;
import com.lh.modules.epl.dao.AlarmThresholdDao;
import com.lh.modules.epl.dao.EplDao;
import com.lh.modules.epl.entity.AlarmEpl;
import com.lh.modules.epl.entity.AlarmEplRelation;
import com.lh.modules.epl.entity.AlarmThreshold;
import com.lh.modules.epl.entity.Epl;
import com.lh.modules.eplCategory.dao.EplCategoryDao;
import com.lh.modules.eplCategory.entity.EplCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 风控规则
 * @author 劉 焱
 * @date 2016-8-24
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class EplService extends CrudService<EplDao, Epl> {
	@Autowired
	private CommonService commonService;
	@Autowired
	private EplCategoryDao eplCategoryDao;
	@Autowired
	private AlarmEplRelationDao alarmEplRelationDao;
	@Autowired
	private AlarmEplDao alarmEplDao;
	@Autowired
	private AlarmThresholdDao alarmThresholdDao;
	
	@Override
	public Page<Epl> findPage(Page<Epl> page, Epl epl) {
		epl.setPage(page);
		List<Epl> list = dao.findList(epl);
		List<Epl> returnList = new ArrayList<Epl>();
		for(Epl epl1 : list){
			Epl record = epl1;
			String decrible = commonService.getEplSql(epl1.getThresholds(), epl1.getEplDescribe());
			record.setEplDescribe(decrible);
			returnList.add(record);
		}
		page.setList(returnList);
		return page;
	}

	@Transactional(readOnly = false)
	public void edit(Epl epl){
		boolean flag = true;
		List<Epl> parentTypes = findParentTypeList();
		for(Epl epl1 : parentTypes){
			if(epl.getParentName().trim().equals(epl1.getParentName())){
				flag = false;
				break;
			}
		}
		
		//新增父规则类型
		if(flag){
			EplCategory eplCategory = new EplCategory();
			eplCategory.setCategory(epl.getParentName());
			eplCategoryDao.insert(eplCategory);
			String parentId = eplCategoryDao.findUniqueByProperty("category",epl.getParentName()).getId();
			epl.setParentId(Integer.parseInt(parentId));
		}
		
		if(StringUtil.isEmpty(epl.getId()) && epl.getStatus() == 1){//新增且上线
			epl.setIsAlarm(2);
			dao.insert(epl);
	    	PushEpl obj = pushZookeeper(epl.getEplId());
	    	commonService.rtcEplPushData(obj, "add", MyCache.ZOOKEEPER_RISK_PATH);
		}else if(StringUtil.isEmpty(epl.getId()) && epl.getStatus() != 1){//新增且下线
			epl.setIsAlarm(2);
			dao.insert(epl);
		}else if(!StringUtil.isEmpty(epl.getId())){//修改
			Epl epl1 = findUniqueByProperty("id", epl.getId());
			dao.update(epl);
			PushEpl obj = pushZookeeper(epl.getEplId());
			if(epl1.getStatus() == epl.getStatus() && epl.getStatus() == 1){//修改前后都为上线状态
		    	commonService.rtcEplPushData(obj, "update", MyCache.ZOOKEEPER_RISK_PATH);
			}else if(epl1.getStatus() != epl.getStatus() && epl.getStatus() == 1){//由下线修改为上线状态
		    	commonService.rtcEplPushData(obj, "add", MyCache.ZOOKEEPER_RISK_PATH);
			}else {//由上线修改为下线状态
		    	commonService.rtcEplPushData(obj, "remove", MyCache.ZOOKEEPER_RISK_PATH);
			}
		}
	}
	
	/**
	 * 获取风控规则类型
	 * @return
	 */
	public List<Epl> findEplCategory() {
		List<Epl> eplList = dao.findEplCategory();
		return eplList;
	}
	/**
	 * 获取风控规则父类型
	 * @return
	 */
	public List<Epl> findParentTypeList() {
		List<Epl> eplList = dao.findParentTypeList();
		return eplList;
	}
	/**
	 * 获取zookeeper推送信息
	 * @param eplId
	 * @return
	 */
	public PushEpl pushZookeeper(Integer eplId) {
		return dao.pushZookeeper(eplId);
	}
	/**
	 * 获取风控规则报警信息
	 * @param epl
	 * @return
	 */
	public Epl findAlarm(Epl epl) {
		if(epl.getIsAlarm()==2){
			return epl;
		}
		epl = dao.findAlarmEpl(epl.getEplId());
		AlarmThreshold alarmThreshold = new AlarmThreshold();
		alarmThreshold.setEplId(epl.getAlarmEplId());
		List<AlarmThreshold> alarmThresholdList = alarmThresholdDao.findList(alarmThreshold);
		epl.setAlarmThresholdList(alarmThresholdList);
		
		return epl;
	}
	/**
	 * 保存报警规则设置
	 * @param epl
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public void saveAlarm(Epl epl) throws Exception {
		//查询报警情况, 并修改相应的报警类型
		Epl epl2 = dao.findUniqueByProperty("id", epl.getId());
		 if(epl2.getIsAlarm()==2){
			 AlarmEpl alarmEpl1 = alarmEplDao.findUniqueByProperty("epl_id", epl.getAlarmEplId());
			 if(alarmEpl1 != null){
				 throw new Exception("该报警编号已存在!");
			 }
			AlarmEpl alarmEpl = new AlarmEpl();
			alarmEpl.setEplId(epl.getAlarmEplId());
			alarmEpl.setEpl(epl.getAlarmSQL());
			alarmEpl.setEplName(epl.getAlarmName());
			alarmEpl.setEplDescribe(epl.getAlarmDescribe());
			alarmEpl.setAlarmGroupId(epl.getAlarmGroupId());
			alarmEpl.setAlarmType(epl.getAlarmType());
			alarmEpl.setStatus(epl.getStatus());
			alarmEpl.setAlarmTemplate(epl.getAlarmTemplate());
			alarmEplDao.insert(alarmEpl);
			AlarmEplRelation alarmEplRelation = new AlarmEplRelation();
			alarmEplRelation.setEplId(epl.getEplId());
			alarmEplRelation.setAlarmEplId(epl.getAlarmEplId());
			alarmEplRelationDao.insert(alarmEplRelation);
			
			Epl epl1 = new Epl();
			epl1.setId(epl.getId());
			epl1.setIsAlarm(1);
			dao.update(epl1);
		}else if(epl2.getIsAlarm()==1){
			AlarmEpl alarmEpl = new AlarmEpl();
			alarmEpl.setId(epl.getAlarmId().toString());
			alarmEpl.setEplId(epl.getAlarmEplId());
			alarmEpl.setEpl(epl.getAlarmSQL());
			alarmEpl.setEplName(epl.getAlarmName());
			alarmEpl.setEplDescribe(epl.getAlarmDescribe());
			alarmEpl.setAlarmGroupId(epl.getAlarmGroupId());
			alarmEpl.setAlarmType(epl.getAlarmType());
			alarmEpl.setStatus(epl.getStatus());
			alarmEpl.setAlarmTemplate(epl.getAlarmTemplate());
			alarmEplDao.update(alarmEpl);
		}

		 if(!StringUtil.isEmpty(epl.getAlarmThresholdStr())){
			 String[] alarmThresholdList = epl.getAlarmThresholdStr().split("\\|");
			 for(String alarmThresholdStr : alarmThresholdList) {
				 String[] thresholdStr = alarmThresholdStr.split(",");
				 AlarmThreshold alarmThreshold = new AlarmThreshold();
				 alarmThreshold.setEplId(Integer.parseInt(thresholdStr[1]));
				 alarmThreshold.setThresholdName(thresholdStr[2]);
				 alarmThreshold.setThresholdValue(Double.parseDouble(thresholdStr[3]));
				 alarmThreshold.setThresholdDescribe(thresholdStr[4]);
				 if(!StringUtils.isBlank(thresholdStr[0])){
					 alarmThreshold.setId(thresholdStr[0].trim());
					 alarmThresholdDao.update(alarmThreshold);
				 }else{
					 alarmThresholdDao.insert(alarmThreshold);
				 }
			 }	
		 }
		 
		 if(epl.getStatus() == 1){
			 PushAlarmEpl obj = alarmEplDao.pushZookeeper(epl.getAlarmEplId());
			 if(epl2.getIsAlarm()==2){
				 commonService.alarmEplPushData(obj, "add", MyCache.ZOOKEEPER_ALARM_PATH);
			 }else{
				 commonService.alarmEplPushData(obj, "update", MyCache.ZOOKEEPER_ALARM_PATH);
			 }
		 }
		 
	}

	/**
	 * 取消报警
	 * @param epl
	 */
	@Transactional(readOnly = false)
	public void deleteAlarm(Epl epl) {
		AlarmEplRelation alarmEplRelation = alarmEplRelationDao.findUniqueByProperty("epl_id", epl.getEplId());
		Integer alarmEplId = alarmEplRelation == null ? null : alarmEplRelation.getAlarmEplId();
		
		if(epl.getStatus() == 1){
			PushAlarmEpl obj = alarmEplDao.pushZookeeper(alarmEplId);
			commonService.alarmEplPushData(obj, "remove", MyCache.ZOOKEEPER_ALARM_PATH);
		}
		
		alarmEplRelationDao.deleteByEplId(epl.getEplId());
		alarmEplDao.deleteByEplId(alarmEplId);
		alarmThresholdDao.deleteThresholds(alarmEplId);
		
		epl.setIsAlarm(2);
		dao.update(epl);
	}
	
	/**
	 * 规则删除
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteSoft(Epl epl) {
		dao.updateStatus(epl);
	}

}