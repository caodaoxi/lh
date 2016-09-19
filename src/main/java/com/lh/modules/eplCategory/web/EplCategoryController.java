package com.lh.modules.eplCategory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lh.common.web.BaseController;
import com.lh.modules.eplCategory.service.EplCategoryService;

/**
 * 风控规则父类
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@Controller
@RequestMapping(value = "${adminPath}/rtc/eplCategory")
public class EplCategoryController extends BaseController {
	@Autowired
	private EplCategoryService eplCategoryService;

}
