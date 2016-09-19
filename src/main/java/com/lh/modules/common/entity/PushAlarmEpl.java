package com.lh.modules.common.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 推送至zookeeper节点上的信息
 * @author 劉 焱
 * @date 2016-8-1
 * @tags
 */
public class PushAlarmEpl extends DataEntity<PushAlarmEpl> {
	
	private static final long serialVersionUID = 1L;
	private String eplName;
	private String epl;
	private String alarmType;
	private Integer groupId;
	private String alarmTemplate;
	private String phone;
	private String email;
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
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getAlarmTemplate() {
		return alarmTemplate;
	}
	public void setAlarmTemplate(String alarmTemplate) {
		this.alarmTemplate = alarmTemplate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getThresholds() {
		return thresholds;
	}
	public void setThresholds(String thresholds) {
		this.thresholds = thresholds;
	}
	

}
