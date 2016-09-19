/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.sys.dao;

import com.lh.common.persistence.TreeDao;
import com.lh.common.persistence.annotation.MyBatisDao;
import com.lh.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author jeeplus
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
