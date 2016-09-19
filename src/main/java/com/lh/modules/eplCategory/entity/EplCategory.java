package com.lh.modules.eplCategory.entity;

import com.lh.common.persistence.DataEntity;

/**
 * 报警规则配置类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
public class EplCategory extends DataEntity<EplCategory> {

	private static final long serialVersionUID = 1L;

    private String category;//规则父类名称

    private String categoryDescribe;//类别描述

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category == null ? null : category.trim();
	}

	public String getCategoryDescribe() {
		return categoryDescribe;
	}

	public void setCategoryDescribe(String categoryDescribe) {
		this.categoryDescribe = categoryDescribe == null ? null : categoryDescribe.trim();
	}

}