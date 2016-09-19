/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.sys.dao;

import com.lh.common.persistence.CrudDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.sys.entity.Org;

/**
 * 区域DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface OrgDao extends CrudDao<Org> {
	
}
