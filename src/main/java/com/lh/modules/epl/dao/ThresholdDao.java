package com.lh.modules.epl.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.epl.entity.Threshold;

/**
 * 风控规则阀值
 * @author 劉 焱
 * @date 2016-8-24
 * @tags
 */
@MyBatisDao
public interface ThresholdDao extends CrudDao<Threshold> {
    public Threshold queryThresholdByThreshold(Threshold threshold);
    public int deleteThresholds(Integer eplId);

}
