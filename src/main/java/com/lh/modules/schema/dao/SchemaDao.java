package com.lh.modules.schema.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.schema.entity.Schema;

/**
 * Created by caodaoxi on 16-7-30.
 */
@MyBatisDao
public interface SchemaDao extends CrudDao<Schema> {
}
