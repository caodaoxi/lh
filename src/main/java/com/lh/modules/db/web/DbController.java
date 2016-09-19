package com.lh.modules.db.web;

import com.lh.common.persistence.Page;
import com.lh.common.web.BaseController;
import com.lh.modules.db.entity.Db;
import com.lh.modules.db.service.DbService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 外部数据源
 * @author 劉 焱
 * @date 2016-8-29
 * @tags
 */
@Controller
@RequestMapping(value = "${adminPath}/rtc/db")
public class DbController extends BaseController {
	@Autowired
	private DbService dbService;

	@RequiresPermissions("rtc:db:list")
	@RequestMapping(value = {"list", ""})
	public String list(Db db, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Db> page = dbService.findPage(new Page<Db>(request, response), db);
		model.addAttribute("page", page);
		
		return "modules/db/db";
	}

	@RequiresPermissions(value={"rtc:db:add","rtc:db:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Db db, Model model, RedirectAttributes redirectAttributes) {
		dbService.save(db);

		return "redirect:" + adminPath + "/rtc/db/list";
	}
	
	@RequiresPermissions("rtc:db:del")
	@RequestMapping(value = "delete")
	public String delete(Db db, RedirectAttributes redirectAttributes) {
		dbService.delete(db);
    	
		return "redirect:" + adminPath + "/rtc/db/list";
	}

	@RequiresPermissions(value={"rtc:db:add","rtc:db:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String editDb(Db db, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(db.getId() != null) {
			db = dbService.findUniqueByProperty("id", db.getId());
			model.addAttribute("db", db);
			return "modules/db/dbForm";
		}
		return "modules/db/dbForm";
	}
	
	@RequiresPermissions("rtc:db:view")
	@RequestMapping(value = "form1")
	public String chickDb(Db db, HttpServletRequest request, HttpServletResponse response, Model model) {
		db = dbService.findUniqueByProperty("id", db.getId());
		model.addAttribute("db", db);
		return "modules/db/dbForm";
	}
}