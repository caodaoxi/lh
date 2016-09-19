package com.lh.modules.db.entity;

import com.lh.common.persistence.DataEntity;
import com.lh.common.utils.StringUtil;

/**
 * 外部数据源
 * @author 劉 焱
 * @date 2016-8-29
 * @tags
 */
public class Db extends DataEntity<Db> {

	private static final long serialVersionUID = 1L;

    private String username;//数据库用户名

    private String dbPassword;//数据库用户密码

    private Integer dbType;//数据库类型

    private String dbName;//数据库名称

    private String dbUrl;//数据库URL

    private String connectionLifecycle;//连接管理方式

    private Integer initialSize;//连接池初始大小

    private String cacheReferenceType;//缓存清理策略

    private String dbDescribe;//数据库描述

    private Integer sourceFactory;//连接池类型

    private Integer maxAgeSeconds;//缓存最大生命周期

    private Integer purgeIntervalSeconds;//缓存清理时间间隔

    private String dbProps;//数据库其他相关属性
    
    private String keyword;//关键字

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword == null ? null : dbPassword.trim();
    }

    public Integer getDbType() {
        return dbType;
    }

    public void setDbType(Integer dbType) {
        this.dbType = dbType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName == null ? null : dbName.trim();
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl == null ? null : dbUrl.trim();
    }

    public String getConnectionLifecycle() {
        return connectionLifecycle;
    }

    public void setConnectionLifecycle(String connectionLifecycle) {
        this.connectionLifecycle = connectionLifecycle == null ? null : connectionLifecycle.trim();
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public String getCacheReferenceType() {
        return cacheReferenceType;
    }

    public void setCacheReferenceType(String cacheReferenceType) {
        this.cacheReferenceType = cacheReferenceType == null ? null : cacheReferenceType.trim();
    }

    public String getDbDescribe() {
        return dbDescribe;
    }

    public void setDbDescribe(String dbDescribe) {
        this.dbDescribe = dbDescribe == null ? null : dbDescribe.trim();
    }

    public Integer getSourceFactory() {
        return sourceFactory;
    }

    public void setSourceFactory(Integer sourceFactory) {
        this.sourceFactory = sourceFactory;
    }

    public Integer getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(Integer maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public Integer getPurgeIntervalSeconds() {
        return purgeIntervalSeconds;
    }

    public void setPurgeIntervalSeconds(Integer purgeIntervalSeconds) {
        this.purgeIntervalSeconds = purgeIntervalSeconds;
    }

    public String getDbProps() {
        return dbProps;
    }

    public void setDbProps(String dbProps) {
        this.dbProps = dbProps == null ? null : dbProps.trim();
    }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = StringUtil.stringHandle(keyword);
	}
    
}
