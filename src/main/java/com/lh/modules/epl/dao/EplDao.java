package com.lh.modules.epl.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.common.entity.PushEpl;
import com.lh.modules.epl.entity.Epl;

import java.util.List;

/**
 * 风控规则
 * @author 劉 焱
 * @date 2016-8-24
 * @tags
 */
@MyBatisDao
public interface EplDao extends CrudDao<Epl> {
	public List<Epl> findEplCategory();
	public List<Epl> findParentTypeList();
	public PushEpl pushZookeeper(Integer eplId);
	public Epl findAlarmEpl(Integer eplId);
	public void updateStatus(Epl epl);
}