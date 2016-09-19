/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.lh.modules.schema.web;

import com.lh.common.persistence.Page;
import com.lh.common.utils.StringUtils;
import com.lh.common.web.BaseController;
import com.lh.modules.schema.entity.Schema;
import com.lh.modules.schema.entity.SchemaField;
import com.lh.modules.schema.service.SchemaFieldService;
import com.lh.modules.schema.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 发送短信
 * @author lgf
 * @version 2016-01-07
 */
@Controller
@RequestMapping(value = "${adminPath}/rtc/schema")
public class SchemaController extends BaseController {

	@Autowired
	private SchemaService schemaService;
	@Autowired
	private SchemaFieldService schemaFieldService;

	@RequestMapping(value = {"list", ""})
	public String list(Schema schema, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Schema> page = schemaService.findPage(new Page<Schema>(request, response), schema);
		model.addAttribute("page", page);
		return "modules/schema/schema";
	}

	@RequestMapping(value = "field")
	public String getSchemaField(SchemaField schemaField, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SchemaField> p = new Page<SchemaField>(request, response);
		p.setCount(100);
		p.setPageSize(100);
		Page<SchemaField> page = schemaFieldService.findPage(p, schemaField);
		model.addAttribute("page", page);
		return "modules/schema/field";
	}

	@RequestMapping(value = "form")
	public String editSchema(Schema schema, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(schema.getId() != null) {
			Schema sc = schemaService.findUniqueByProperty("id", schema.getId());
			SchemaField schemaField = new SchemaField();
			schemaField.setSchemaId(sc.getSchemaId());
			List<SchemaField> schemaFields = schemaFieldService.findList(schemaField);
			sc.setSchemaFieldList(schemaFields);
			model.addAttribute("schema", sc);
			return "modules/schema/schemaForm";
		}
		return "modules/schema/schemaForm";
	}

	@RequestMapping(value = "deleteField")
	@ResponseBody
	public String deleteField(SchemaField schemaField, HttpServletRequest request, HttpServletResponse response, Model model) {
		schemaFieldService.delete(schemaField);
		return "ok";
	}

	@RequestMapping(value = "saveField")
	@ResponseBody
	public SchemaField saveField(SchemaField schemaField, HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean isNew = schemaField.getId() == null ? true : false;
		try {
			schemaField.setFieldDescribe(URLDecoder.decode(schemaField.getFieldDescribe(),   "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		schemaFieldService.save(schemaField);
		if (isNew){
			schemaField = schemaFieldService.findUniqueByProperty("field_name", schemaField.getFieldName());
		}
		return schemaField;
	}


	@RequestMapping(value = "save")
	public String save(Schema schema, Model model, RedirectAttributes redirectAttributes) {
		schemaService.save(schema);
		String[] schemaFieldListStr = schema.getSchemaFieldListStr().split("\\|");
		for(String schemaFieldStr : schemaFieldListStr) {
			String[] fieldList = schemaFieldStr.split(",");
			SchemaField schemaField = new SchemaField();
			if(!StringUtils.isBlank(fieldList[0])) schemaField.setId(fieldList[0].trim());
			schemaField.setSchemaId(Integer.parseInt(fieldList[1]));
			schemaField.setFieldName(fieldList[2]);
			schemaField.setFieldType(fieldList[3]);
			schemaField.setFieldDescribe(fieldList[4]);
			schemaFieldService.save(schemaField);
		}
		return "redirect:" + adminPath + "/rtc/schema/list";
	}

	@RequestMapping(value = "delete")
	public String deleteAll(Schema schema, RedirectAttributes redirectAttributes) {
		schemaService.delete(schema);
		schemaFieldService.deleteFields(schema.getSchemaId());
		return "redirect:" + adminPath + "/rtc/schema/list";
	}
}