package com.lh.modules.schema.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.schema.entity.SchemaField;

/**
 * Created by caodaoxi on 16-7-30.
 */
@MyBatisDao
public interface SchemaFieldDao extends CrudDao<SchemaField> {
    public SchemaField querySchemaFieldByFieldName(SchemaField schemaField);
    public int deleteFields(int schemaId);

}
