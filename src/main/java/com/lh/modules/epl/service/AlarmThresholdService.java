package com.lh.modules.epl.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.lh.common.config.MyCache;
import com.lh.common.service.CrudService;
import com.lh.modules.common.entity.PushAlarmEpl;
import com.lh.modules.common.servie.CommonService;
import com.lh.modules.epl.dao.AlarmEplDao;
import com.lh.modules.epl.dao.AlarmThresholdDao;
import com.lh.modules.epl.entity.AlarmThreshold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 报警规则阀值
 * @author 劉 焱
 * @date 2016-9-5
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class AlarmThresholdService extends CrudService<AlarmThresholdDao, AlarmThreshold> {
	@Autowired
	private AlarmEplDao alarmEplDao;
	@Autowired
	private CommonService commonService;

	/**
	 * 根据eplid删除阀值
	 * @param eplId
	 */
	@Transactional(readOnly = false)
	public void deleteThresholds(Integer eplId) {
		dao.deleteThresholds(eplId);
	}

	/**
	 * 删除报警阀值并通知zookeeper节点
	 * @param alarmThreshold
	 */
	@Transactional(readOnly = false)
	public void deleteAlarmThreshold(AlarmThreshold alarmThreshold) {
		dao.delete(alarmThreshold);
		
		if(alarmThreshold.getStatus() == 1){
			PushAlarmEpl obj = alarmEplDao.pushZookeeper(alarmThreshold.getEplId());
			commonService.alarmEplPushData(obj, "remove", MyCache.ZOOKEEPER_ALARM_PATH);
		}		
	}

	/**
	 * 修改报警阀值并通知zookeeper节点
	 * @param alarmThreshold
	 * @return
	 */
	@Transactional(readOnly = false)
	public AlarmThreshold saveAlarmThreshold(AlarmThreshold alarmThreshold) {
		boolean isNew = alarmThreshold.getId() == null ? true : false;
		try {
			alarmThreshold.setThresholdDescribe(URLDecoder.decode(alarmThreshold.getThresholdDescribe(),   "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (isNew){
			dao.insert(alarmThreshold);
			alarmThreshold = dao.findUniqueByProperty("id", alarmThreshold.getId());
		}else{
			dao.update(alarmThreshold);
		}
		
		if(alarmThreshold.getStatus() == 1){
			PushAlarmEpl obj = alarmEplDao.pushZookeeper(alarmThreshold.getEplId());
			commonService.alarmEplPushData(obj, "update", MyCache.ZOOKEEPER_ALARM_PATH);
		}	
		
		return alarmThreshold;
	}
}
