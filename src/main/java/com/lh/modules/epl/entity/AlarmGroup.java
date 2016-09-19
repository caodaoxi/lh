package com.lh.modules.epl.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 风控报警组配置类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class AlarmGroup extends DataEntity<AlarmGroup> {

	private static final long serialVersionUID = 1L;

    private Integer groupId;

    private String groupName;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}