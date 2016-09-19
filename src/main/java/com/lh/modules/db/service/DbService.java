package com.lh.modules.db.service;

import com.lh.common.service.CrudService;
import com.lh.modules.db.dao.DbDao;
import com.lh.modules.db.entity.Db;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 外部数据源
 * @author 劉 焱
 * @date 2016-8-29
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class DbService extends CrudService<DbDao, Db> {

}