package com.lh.modules.epl.entity;

import java.util.List;

import com.lh.common.persistence.DataEntity;
import com.lh.common.utils.StringUtil;

/**
 * Created by caodaoxi on 16-8-19.
 */
public class Epl extends DataEntity<Epl> {

	private static final long serialVersionUID = 1L;

	private Integer eplId;//风控规则id

    private String eplName;//规则中文或英文名称

    private Integer parentId;//父类规则id
    
    private String parentName;//父类规则名称

    private String epl;//风控规则sql，sql中阀值需要取阀值配置表的变量替换

    private String eplDescribe;//规则描述

    private Integer status;//状态：1,运行上线状态.2,下线状态

    private String textState;//中文描述模板
    
    private Integer isAlarm;//是否报警:1报警,2不报警

    private String thresholds;//阀值
    
    private String keyword;//搜索关键词
    
    private List<Threshold> thresholdList;//阀值
    
    private String thresholdListStr;//阀值
    
    private Integer alarmId;//报警类型ID
    
    private Integer alarmEplId;//报警类型ID[自增]
    
    private String alarmName;//报警类型名称
    
    private AlarmEpl alarmEpl;//报警规则
    
    private String alarmEplStr;//报警规则
    
    private List<AlarmThreshold> alarmThresholdList;//报警规则阀值
    
    private String alarmThresholdStr;//报警规则阀值
    
    private Integer alarmGroupId;//报警规则接收组
    
    private Integer alarmType;//报警规则类型
    
    private String alarmSQL;//报警规则sql
    
    private String alarmTemplate;//报警规则模板
    
    private String alarmDescribe;//报警规则描述
    
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName == null ? null : parentName.trim();
	}

	public String getEpl() {
        return epl;
    }

    public void setEpl(String epl) {
        this.epl = StringUtil.stringHandle(epl)==null ? null : StringUtil.stringHandle(epl).replaceAll("&gt;", ">").replaceAll("&lt;", "<");
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

    public String getTextState() {
        return textState;
    }

    public void setTextState(String textState) {
        this.textState = textState == null ? null : textState.trim();
    }

    public Integer getIsAlarm() {
		return isAlarm;
	}

	public void setIsAlarm(Integer isAlarm) {
		this.isAlarm = isAlarm;
	}

	public String getThresholds() {
        return thresholds;
    }

    public void setThresholds(String thresholds) {
        this.thresholds = thresholds == null ? null : thresholds.trim();
    }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	public List<Threshold> getThresholdList() {
		return thresholdList;
	}

	public String getThresholdListStr() {
		return thresholdListStr;
	}

	public void setThresholdListStr(String thresholdListStr) {
		this.thresholdListStr = thresholdListStr;
	}

	public void setThresholdList(List<Threshold> thresholdList) {
		this.thresholdList = thresholdList;
	}

	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public Integer getAlarmEplId() {
		return alarmEplId;
	}

	public void setAlarmEplId(Integer alarmEplId) {
		this.alarmEplId = alarmEplId;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName == null ? null : alarmName.trim();
	}

	public AlarmEpl getAlarmEpl() {
		return alarmEpl;
	}

	public void setAlarmEpl(AlarmEpl alarmEpl) {
		this.alarmEpl = alarmEpl;
	}

	public String getAlarmEplStr() {
		return alarmEplStr;
	}

	public void setAlarmEplStr(String alarmEplStr) {
		this.alarmEplStr = StringUtil.stringHandle(alarmEplStr);
	}

	public List<AlarmThreshold> getAlarmThresholdList() {
		return alarmThresholdList;
	}

	public void setAlarmThresholdList(List<AlarmThreshold> alarmThresholdList) {
		this.alarmThresholdList = alarmThresholdList;
	}

	public String getAlarmThresholdStr() {
		return alarmThresholdStr;
	}

	public void setAlarmThresholdStr(String alarmThresholdStr) {
		this.alarmThresholdStr = StringUtil.stringHandle(alarmThresholdStr);
	}

	public Integer getAlarmGroupId() {
		return alarmGroupId;
	}

	public void setAlarmGroupId(Integer alarmGroupId) {
		this.alarmGroupId = alarmGroupId;
	}

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmSQL() {
		return alarmSQL;
	}

	public void setAlarmSQL(String alarmSQL) {
		this.alarmSQL = StringUtil.stringHandle(alarmSQL)==null ? null : StringUtil.stringHandle(alarmSQL).replaceAll("&gt;", ">").replaceAll("&lt;", "<");
	}

	public String getAlarmTemplate() {
		return alarmTemplate;
	}

	public void setAlarmTemplate(String alarmTemplate) {
		this.alarmTemplate = StringUtil.stringHandle(alarmTemplate);
	}

	public String getAlarmDescribe() {
		return alarmDescribe;
	}

	public void setAlarmDescribe(String alarmDescribe) {
		this.alarmDescribe = StringUtil.stringHandle(alarmDescribe);
	}

}
