package com.lh.modules.epl.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 报警规则阀值配置
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class AlarmThreshold extends DataEntity<AlarmThreshold> {

	private static final long serialVersionUID = 1L;

    private Integer eplId;

    private String thresholdName;

    private Double thresholdValue;

    private String thresholdDescribe;
    
    private Integer status;

    public Integer getEplId() {
        return eplId;
    }

    public void setEplId(Integer eplId) {
        this.eplId = eplId;
    }

    public String getThresholdName() {
        return thresholdName;
    }

    public void setThresholdName(String thresholdName) {
        this.thresholdName = thresholdName == null ? null : thresholdName.trim();
    }

    public Double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public String getThresholdDescribe() {
        return thresholdDescribe;
    }

    public void setThresholdDescribe(String thresholdDescribe) {
        this.thresholdDescribe = thresholdDescribe == null ? null : thresholdDescribe.trim();
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}