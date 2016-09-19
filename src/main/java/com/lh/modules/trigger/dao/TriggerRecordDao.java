package com.lh.modules.trigger.dao;

import java.util.List;
import java.util.Map;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.trigger.entity.TriggerRecord;
import com.lh.modules.trigger.entity.TriggerStatistics;

/**
 * 风控触发记录持久层
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@MyBatisDao
public interface TriggerRecordDao  extends CrudDao<TriggerRecord>{

	public List<TriggerStatistics> getTriggerStatistics(Map<String, String> requestMap);
	public List<TriggerStatistics> getTriggerStatisticsByPeriod(Map<String, String> requestMap);

	public List<TriggerStatistics> getTriggerStatisticsNew(Map<String, String> requestMap);

	public List<TriggerStatistics> getTriggerStatisticsOld(Map<String, String> requestMap);

	public List<TriggerRecord> findLatest(Map<String, String> map);
	/**
	 * 获取列表统计信息
	 * @param triggerRecord
	 * @return
	 */
	public List<TriggerRecord> findStatisticsList(TriggerRecord triggerRecord);
	/**
	 * 获取列表统计汇总信息
	 * @param triggerRecord
	 * @return
	 */
	public TriggerRecord findStatisticsTotalList(TriggerRecord triggerRecord);
	/**
	 * 获取未处理各类型触发记录数量
	 * @return
	 */
	public List<Map<String, Object>> findUndealTypes(Map<String, String> map);
	/**
	 * 获取当天各状态触发记录数量
	 * @param map
	 * @return
	 */
	public TriggerStatistics findUndealAndTotalCount(Map<String, String> map);
}