package com.lh.modules.epl.dao;

import java.util.List;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.epl.entity.AlarmGroup;

/**
 * 报警接收组
 * @author 劉 焱
 * @date 2016-9-5
 * @tags
 */
@MyBatisDao
public interface AlarmGroupDao extends CrudDao<AlarmGroup> {

	List<AlarmGroup> findList();

}
