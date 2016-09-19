package com.lh.modules.epl.service;

import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.modules.epl.dao.ThresholdDao;
import com.lh.modules.epl.entity.Threshold;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 接口Service
 * @author caodaoxi
 * @version 2016-05-28
 */
@Service
@Transactional(readOnly = true)
public class ThresholdService extends CrudService<ThresholdDao, Threshold> {

	public Page<Threshold> findPage(Page<Threshold> page, Threshold threshold) {
		return super.findPage(page, threshold);
	}

	@Transactional(readOnly = false)
	public void deleteThresholds(Integer eplId) {
		dao.deleteThresholds(eplId);
	}

//	@Transactional(readOnly = false)
//	public int saveSchemaField(SchemaField entity) {
//		if (entity.getIsNewRecord()){
//			entity.preInsert();
//			return dao.insert(entity);
//		}else{
//			entity.preUpdate();
//			dao.update(entity);
//			return Integer.parseInt(entity.getId());
//		}
//	}
//
//
//	@Transactional(readOnly = false)
//	public int querySchemaFieldByFieldName(SchemaField entity) {
//		if (entity.getIsNewRecord()){
//			entity.preInsert();
//			return dao.insert(entity);
//		}else{
//			entity.preUpdate();
//			dao.update(entity);
//			return Integer.parseInt(entity.getId());
//		}
//	}
//
//	@Transactional(readOnly = false)
//	public int deleteFields(int schemaId) {
//		return dao.deleteFields(schemaId);
//	}
}
