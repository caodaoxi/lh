package com.lh.modules.common.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 推送至zookeeper节点上的信息
 * @author 劉 焱
 * @date 2016-8-1
 * @tags
 */
public class PushEpl extends DataEntity<PushEpl> {

	private static final long serialVersionUID = 1L;
	private String eplName;
	private String epl;
	private String thresholds;
	public String getEplName() {
		return eplName;
	}
	public void setEplName(String eplName) {
		this.eplName = eplName;
	}
	public String getEpl() {
		return epl;
	}
	public void setEpl(String epl) {
		this.epl = epl;
	}
	public String getThresholds() {
		return thresholds;
	}
	public void setThresholds(String thresholds) {
		this.thresholds = thresholds;
	}

}
