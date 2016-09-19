/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.schema.service;

import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.modules.schema.dao.SchemaFieldDao;
import com.lh.modules.schema.entity.SchemaField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 接口Service
 * @author caodaoxi
 * @version 2016-05-28
 */
@Service
@Transactional(readOnly = true)
public class SchemaFieldService extends CrudService<SchemaFieldDao, SchemaField> {

	@Autowired
	private SchemaFieldDao schemaFieldDao;

	public Page<SchemaField> findPage(Page<SchemaField> page, SchemaField schemaField) {
		return super.findPage(page, schemaField);

	}

	@Transactional(readOnly = false)
	public int saveSchemaField(SchemaField entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			return dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
			return Integer.parseInt(entity.getId());
		}
	}


	@Transactional(readOnly = false)
	public int querySchemaFieldByFieldName(SchemaField entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			return dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
			return Integer.parseInt(entity.getId());
		}
	}

	@Transactional(readOnly = false)
	public int deleteFields(int schemaId) {
		return dao.deleteFields(schemaId);
	}
}
