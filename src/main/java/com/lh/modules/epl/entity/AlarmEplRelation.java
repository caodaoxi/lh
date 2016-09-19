package com.lh.modules.epl.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 风控规则与报警规则关联
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class AlarmEplRelation extends DataEntity<AlarmEplRelation> {

	private static final long serialVersionUID = 1L;

    private Integer eplId;

    private Integer alarmEplId;

    public Integer getEplId() {
        return eplId;
    }

    public void setEplId(Integer eplId) {
        this.eplId = eplId;
    }

    public Integer getAlarmEplId() {
        return alarmEplId;
    }

    public void setAlarmEplId(Integer alarmEplId) {
        this.alarmEplId = alarmEplId;
    }
}