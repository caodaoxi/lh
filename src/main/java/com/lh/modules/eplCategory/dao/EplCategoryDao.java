package com.lh.modules.eplCategory.dao;

import java.util.List;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.eplCategory.entity.EplCategory;

/**
 * 风控规则父类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@MyBatisDao
public interface EplCategoryDao extends CrudDao<EplCategory> {

	List<EplCategory> findTypeList();

}