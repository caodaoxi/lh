package com.lh.modules.udf.entity;

import com.lh.common.persistence.DataEntity;

/**
 * Created by caodaoxi on 16-8-30.
 */
public class Udf extends DataEntity<Udf> {
    private String methodName;
    private String cacheReferenceType;
    private String methodDescribe;
    private int maxAgeSeconds;
    private int purgeIntervalSeconds;
    private String keyword;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getCacheReferenceType() {
        return cacheReferenceType;
    }

    public void setCacheReferenceType(String cacheReferenceType) {
        this.cacheReferenceType = cacheReferenceType;
    }

    public String getMethodDescribe() {
        return methodDescribe;
    }

    public void setMethodDescribe(String methodDescribe) {
        this.methodDescribe = methodDescribe;
    }

    public int getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(int maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public int getPurgeIntervalSeconds() {
        return purgeIntervalSeconds;
    }

    public void setPurgeIntervalSeconds(int purgeIntervalSeconds) {
        this.purgeIntervalSeconds = purgeIntervalSeconds;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
