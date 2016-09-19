/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.sys.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 用户Entity
 * @author jeeplus
 * @version 2013-12-05
 */
public class Org extends DataEntity<Org> {

	private static final long serialVersionUID = 1L;
	private int orgid;
	private String orgname;

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
}