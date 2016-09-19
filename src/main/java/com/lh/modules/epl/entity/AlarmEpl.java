package com.lh.modules.epl.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 报警规则配置类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class AlarmEpl extends DataEntity<AlarmEpl> {

	private static final long serialVersionUID = 1L;

	private Integer eplId;//规则id
	
    private String eplName;//规则中文或英文名称

    private Integer alarmType;//报警类型，1：短信 2.邮件

    private Integer alarmGroupId;//报警接收组：关联自rtc_alarm_group主键id

    private String alarmTemplate;//报警内容模板

    private String epl;//规则sql，sql中阀值需要取阀值配置表的变量替换
    
    private String eplDescribe;//规则描述
    
    private Integer status;//状态：1,运行上线状态.2,下线状态

    public Integer getEplId() {
		return eplId;
	}

	public void setEplId(Integer eplId) {
		this.eplId = eplId;
	}

	public String getEplName() {
        return eplName;
    }

    public void setEplName(String eplName) {
        this.eplName = eplName == null ? null : eplName.trim();
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getAlarmGroupId() {
        return alarmGroupId;
    }

    public void setAlarmGroupId(Integer alarmGroupId) {
        this.alarmGroupId = alarmGroupId;
    }

    public String getAlarmTemplate() {
        return alarmTemplate;
    }

    public void setAlarmTemplate(String alarmTemplate) {
        this.alarmTemplate = alarmTemplate == null ? null : alarmTemplate.trim();
    }

    public String getEpl() {
        return epl;
    }

    public void setEpl(String epl) {
        this.epl = epl == null ? null : epl.trim();
    }

	public String getEplDescribe() {
		return eplDescribe;
	}
	
	public void setEplDescribe(String eplDescribe) {
		this.eplDescribe = eplDescribe == null ? null : eplDescribe.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}