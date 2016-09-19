package com.lh.modules.epl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lh.common.service.CrudService;
import com.lh.modules.common.entity.PushAlarmEpl;
import com.lh.modules.epl.dao.AlarmEplDao;
import com.lh.modules.epl.entity.AlarmEpl;
import com.lh.modules.epl.entity.Epl;

/**
 * 报警规则配置业务实现层
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class AlarmEplService extends CrudService<AlarmEplDao, AlarmEpl> {
	
	/**
	 * 查询告警规则列表
	 */
	public List<Epl> findTypeList() {
		return dao.findTypeList();
	}

	/**
	 * zookeeper节点推送
	 * @param alarmEplId
	 * @return
	 */
	public PushAlarmEpl pushZookeeper(Integer alarmEplId) {
		return dao.pushZookeeper(alarmEplId);
	}

}
