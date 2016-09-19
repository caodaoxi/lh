/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.udf.service;

import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.modules.udf.dao.UdfDao;
import com.lh.modules.udf.entity.Udf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预警触发记录
 * @author 劉 焱
 * @date 2016-8-19
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class UdfService extends CrudService<UdfDao, Udf> {
	/**
	 * 分页查询
	 * @param page
	 * @param udf
	 * @return
	 */
	public Page<Udf> find(Page<Udf> page, Udf udf) {
		udf.setPage(page);
		List<Udf> list = dao.findList(udf);
		page.setList(list);
		return page;
	}
}