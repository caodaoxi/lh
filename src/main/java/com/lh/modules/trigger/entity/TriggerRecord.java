package com.lh.modules.trigger.entity;

import com.lh.common.persistence.DataEntity;
import com.lh.common.utils.StringUtil;
import com.lh.common.utils.excel.annotation.ExcelField;

/**
 * 风控规则触发记录类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class TriggerRecord  extends DataEntity<TriggerRecord>{
	
	private static final long serialVersionUID = 1L;
	@ExcelField(title="规则id", align=2, sort=20)
	private Integer eplId; //规则id
	@ExcelField(title="规则名称", align=2, sort=3)
	private String eplName; //规则名称
	@ExcelField(title="触发时的统计状态", align=2, sort=20)
	private String triggerState; //触发时的统计状态
	@ExcelField(title="规则触发时的时间", align=2, sort=20)
	private String triggerTime;  //规则触发时的时间
	@ExcelField(title="处理状态", align=2, sort=20)
	private Integer status;  //处理状态
	private String opinion; //处理意见
	private String dealTime; //处理时间
	@ExcelField(title="资金账号", align=2, sort=2)
	private Long fundid;   //资金账号

	private String textState; //统计状态文本模板

	private String eplDescribe; //规则描述

	private String thresholds;  //阀值
	private String orgid;  //机构id
	@ExcelField(title="机构名称", align=2, sort=1)
	private String orgname; //机构名称

	private String startTime;  //开始时间

	private String endTime;	//结束时间

	private String readNum;		// 已读
	private String unReadNum;	// 未读

	private boolean isSelf;		// 是否只查询自己的通知

	private String readFlag;	// 本人阅读状态
    
	private String time;//统计时间
	
	private int totalCount;//总数
	
	private int dealCount;//已处理数量
	
	private int undealCount;//未处理数量
	
	private int ignoreCount;//忽略数量
	
	private int verificationCount;//处理中的数量
	
	private String startDate;//查询开始时间
	
	private String endDate;//查询结束时间
	
	public TriggerRecord() {
		super();
	}

	public TriggerRecord(String id){
		super(id);
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time+":00";
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public int getUndealCount() {
		return undealCount;
	}

	public void setUndealCount(int undealCount) {
		this.undealCount = undealCount;
	}

	public int getIgnoreCount() {
		return ignoreCount;
	}

	public void setIgnoreCount(int ignoreCount) {
		this.ignoreCount = ignoreCount;
	}

	public int getVerificationCount() {
		return verificationCount;
	}

	public void setVerificationCount(int verificationCount) {
		this.verificationCount = verificationCount;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = StringUtil.stringHandle(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = StringUtil.stringHandle(endDate);
	}

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

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState == null ? null : triggerState.trim();
    }

    public String getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(String triggerTime) {
        this.triggerTime = StringUtil.subDateLength(triggerTime);
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion == null ? null : opinion.trim();
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = StringUtil.subDateLength(dealTime);
	}

	public Long getFundid() {
		return fundid;
	}

	public void setFundid(Long fundid) {
		this.fundid = fundid;
	}

	public String getTextState() {
		return textState;
	}

	public void setTextState(String textState) {
		this.textState = textState == null ? null : textState.trim();
	}

	public String getEplDescribe() {
		return eplDescribe;
	}

	public void setEplDescribe(String eplDescribe) {
		this.eplDescribe = eplDescribe == null ? null : eplDescribe.trim();
	}

	public String getThresholds() {
		return thresholds;
	}

	public void setThresholds(String thresholds) {
		this.thresholds = thresholds == null ? null : thresholds.trim();
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid == null ? null : orgid.trim();
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname == null ? null : orgname.trim();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = StringUtil.subDateLength(startTime);
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = StringUtil.subDateLength(endTime);
	}
	
}