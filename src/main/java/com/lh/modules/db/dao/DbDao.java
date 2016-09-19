package com.lh.modules.db.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.db.entity.Db;

/**
 * 外部数据源
 * @author 劉 焱
 * @date 2016-8-29
 * @tags
 */
@MyBatisDao
public interface DbDao extends CrudDao<Db> {
}