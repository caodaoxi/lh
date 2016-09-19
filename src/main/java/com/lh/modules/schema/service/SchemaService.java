/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.schema.service;

import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.modules.schema.dao.SchemaDao;
import com.lh.modules.schema.entity.Schema;
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
public class SchemaService extends CrudService<SchemaDao, Schema> {

	@Autowired
	private SchemaDao schemaDao;

	public Page<Schema> findPage(Page<Schema> page, Schema schema) {
		return super.findPage(page, schema);

	}
}
