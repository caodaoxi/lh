package com.lh.modules.epl.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.epl.entity.AlarmThreshold;

/**
 * 风控报警规则阀值
 * @author 劉 焱
 * @date 2016-9-5
 * @tags
 */
@MyBatisDao
public interface AlarmThresholdDao extends CrudDao<AlarmThreshold> {

	void deleteThresholds(Integer eplId);

}
