package com.lh.modules.eplCategory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lh.common.service.CrudService;
import com.lh.modules.eplCategory.dao.EplCategoryDao;
import com.lh.modules.eplCategory.entity.EplCategory;

/**
 * 报警规则配置业务实现层
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class EplCategoryService extends CrudService<EplCategoryDao, EplCategory> {
	/**
	 * 查询风控规则父类类型
	 */
	public List<EplCategory> findTypeList() {
		return dao.findTypeList();
	}

}
