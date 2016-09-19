package com.lh.modules.udf.web;

import com.lh.common.persistence.Page;
import com.lh.common.utils.StringUtils;
import com.lh.common.web.BaseController;
import com.lh.modules.udf.entity.Udf;
import com.lh.modules.udf.service.UdfService;
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
 * 风控触发记录控制层
 * @author 劉 焱
 * @date 2016-7-25
 * @tags
 */
@Controller
@RequestMapping(value = "${adminPath}/rtc/udf")
public class UdfController extends BaseController {

	@Autowired
	private UdfService udfService;
		
	@RequiresPermissions("rtc:udf:list")
	@RequestMapping(value = {"list", ""})
	public String list(Udf udf, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Udf> page = udfService.find(new Page<Udf>(request, response), udf);
		model.addAttribute("page", page);
		return "modules/udf/udf";
	}


	@RequiresPermissions(value={"rtc:udf:add","rtc:udf:edit"},logical= Logical.OR)
	@RequestMapping(value = "save")
	public String save(Udf udf, Model model, RedirectAttributes redirectAttributes) {
		udfService.save(udf);
		return "redirect:" + adminPath + "/rtc/udf/list";
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"rtc:udf::view","rtc:udf:add","rtc:udf:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Udf udf, Model model) {
		if (StringUtils.isNotBlank(udf.getId())){
			udf = udfService.get(udf);
		}
		model.addAttribute("udf", udf);
		return "modules/udf/udfForm";
	}

	@RequiresPermissions("rtc:udf:del")
	@RequestMapping(value = "delete")
	public String delete(Udf udf, RedirectAttributes redirectAttributes) {
		udfService.delete(udf);
		addMessage(redirectAttributes, "删除通知成功");
		return "redirect:" + adminPath + "/rtc/udf/?repage";
	}
 
}
