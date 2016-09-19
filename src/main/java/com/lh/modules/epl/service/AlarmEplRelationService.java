package com.lh.modules.epl.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lh.common.service.CrudService;
import com.lh.modules.epl.dao.AlarmEplRelationDao;
import com.lh.modules.epl.entity.AlarmEplRelation;

/**
 * 风控规则与报警规则关联
 * @author 劉 焱
 * @date 2016-8-31
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class AlarmEplRelationService extends CrudService<AlarmEplRelationDao, AlarmEplRelation> {

}
