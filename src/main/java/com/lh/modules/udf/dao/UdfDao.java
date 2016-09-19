package com.lh.modules.udf.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.udf.entity.Udf;


/**
 * 风控触发记录持久层
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@MyBatisDao
public interface UdfDao extends CrudDao<Udf>{
}