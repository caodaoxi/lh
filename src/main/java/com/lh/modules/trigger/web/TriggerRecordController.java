package com.lh.modules.trigger.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lh.common.persistence.Page;
import com.lh.common.utils.StringUtils;
import com.lh.common.utils.excel.ExportExcel;
import com.lh.common.web.BaseController;
import com.lh.modules.epl.entity.Epl;
import com.lh.modules.epl.service.EplService;
import com.lh.modules.sys.entity.Org;
import com.lh.modules.sys.service.OrgService;
import com.lh.modules.trigger.entity.TriggerRecord;
import com.lh.modules.trigger.service.TriggerRecordService;

/**
 * 风控触发记录控制层
 * @author 劉 焱
 * @date 2016-7-25
 */
@Controller
@RequestMapping(value = "${adminPath}/trigger")
public class TriggerRecordController extends BaseController {
	@Autowired
	private TriggerRecordService recordService;
	@Autowired
	private EplService eplService;
	@Autowired
	private OrgService orgService;
	
	@ModelAttribute
	public TriggerRecord get(@RequestParam(required=false) String id) {
		TriggerRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recordService.get(id);
		}
		if (entity == null){
			entity = new TriggerRecord();
		}
		return entity;
	}
	/**
	 * 登录进入主页面
	 * @return
	 */
	@RequestMapping("home")
	public String info(){
		return  "modules/trigger/home";
	}
	
	@RequiresPermissions("trigger:record:list")
	@RequestMapping(value = {"record/list", "record", ""})
	public String triggerRecordList(TriggerRecord triggerRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TriggerRecord> page = recordService.findRecord(new Page<TriggerRecord>(request, response), triggerRecord);
		List<Org> orgList = orgService.findAll();
		List<Epl> eplCategories = eplService.findEplCategory();
		model.addAttribute("page", page);
		model.addAttribute("orgList", orgList);
		model.addAttribute("eplCategories", eplCategories);
		return "modules/trigger/recordList";
	}

	/**
	 * 查看，增加，编辑报告表单页面
	 */
	@RequiresPermissions(value={"trigger:record:view","trigger:record:add","trigger:record:edit"},logical=Logical.OR)
	@RequestMapping(value = "record/form")
	public String form(TriggerRecord triggerRecord, Model model) {
		long startTime = DateTime.now().getMillis();
		long endTime = DateTime.now().getMillis();
		if (StringUtils.isNotBlank(triggerRecord.getId())){
			triggerRecord = recordService.get(triggerRecord);
			JSONObject triggerRecordJson = JSONObject.fromObject(triggerRecord.getTriggerState());
			startTime = triggerRecordJson.getLong("start_time");
			endTime = triggerRecordJson.getLong("end_time");
			triggerRecord = recordService.replaceStrVar(triggerRecord);
		}
		model.addAttribute("triggerRecord", triggerRecord);
		model.addAttribute("details", recordService.queryDetailPage(0, 10, triggerRecord.getFundid(), startTime, endTime));
		return "modules/trigger/recordForm";
	}

	@RequiresPermissions(value={"trigger:record:add","trigger:record:edit"},logical=Logical.OR)
	@RequestMapping(value = "record/save")
	public String save(TriggerRecord triggerRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, triggerRecord)){
			return form(triggerRecord, model);
		}
		// 如果是修改，则状态为已发布，则不能再进行操作
		if (StringUtils.isNotBlank(triggerRecord.getId())){
			TriggerRecord e = recordService.get(triggerRecord.getId());
			if (e.getStatus() != 3){
				addMessage(redirectAttributes, "已处理，不能重复处理！");
				return "redirect:" + adminPath + "/trigger/record";
			}
		}
		recordService.save(triggerRecord);
		addMessage(redirectAttributes, "处理'" + triggerRecord.getEplName() + "'成功");
		return "redirect:" + adminPath + "/trigger/record/list";
	}
	
	@RequiresPermissions("trigger:record:del")
	@RequestMapping(value = "record/delete")
	public String delete(TriggerRecord triggerRecord, RedirectAttributes redirectAttributes) {
		recordService.delete(triggerRecord);
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:" + adminPath + "/trigger/record/list";
	}
	
	@RequiresPermissions("trigger:record:del")
	@RequestMapping(value = "record/deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			recordService.delete(recordService.get(id));
		}
		addMessage(redirectAttributes, "删除记录成功");
		return "redirect:" + adminPath + "/trigger/record/list";
	}

//	/**
//	 * 查看我的通知,重定向在当前页面打开
//	 */
//	@RequestMapping(value = "view")
//	public String view(TriggerRecord triggerRecord, Model model) {
//		if (StringUtils.isNotBlank(triggerRecord.getId())){
//			triggerRecord = recordService.get(triggerRecord.getId());
//			recordService.updateReadFlag(triggerRecord);
//			model.addAttribute("triggerRecord", triggerRecord);
//			return "modules/trigger/recordForm";
//		}
//		return "redirect:" + adminPath + "/trigger/record/self?repage";
//	}

//	/**
//	 * 查看我的通知-数据
//	 */
//	@RequestMapping(value = "viewData")
//	@ResponseBody
//	public TriggerRecord viewData(TriggerRecord triggerRecord, Model model) {
//		if (StringUtils.isNotBlank(triggerRecord.getId())){
////			recordService.updateReadFlag(triggerRecord);
//			return triggerRecord;
//		}
//		return null;
//	}

//	public JSONArray getAxis(List<TriggerStatistics> triggerStatistics, String key, String period) {
//		JSONArray jsonArray = new JSONArray();
//		for(int i = 0; i < triggerStatistics.size(); i++) {
//			JSONArray triggerStatisticArray = new JSONArray();
//			try {
//				if("minute".equals(period)){
//					triggerStatisticArray.add(triggerStatistics.get(i).getTriggerDate());
////					triggerStatisticArray.add(DateUtils.stringToMillisecond(triggerStatistics.get(i).getTriggerDate(), MyConst.YYYY_MM_DD_HH_MM));
//				}else if("hour".equals(period)){
//					triggerStatisticArray.add(DateUtils.stringToMillisecond(triggerStatistics.get(i).getTriggerDate(), MyConst.YYYY_MM_DD_HH));
//				}else{
//					triggerStatisticArray.add(DateUtils.stringToMillisecond(triggerStatistics.get(i).getTriggerDate(), MyConst.YYYY_MM_DD));
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			if("processed".equals(key)) {
//				triggerStatisticArray.add(triggerStatistics.get(i).getProcessed());
//			} else if("ignored".equals(key)) {
//				triggerStatisticArray.add(triggerStatistics.get(i).getIgnored());
//			} else if("unprocessed".equals(key)) {
//				triggerStatisticArray.add(triggerStatistics.get(i).getUnprocessed());
//			} else if("processing".equals(key)) {
//				triggerStatisticArray.add(triggerStatistics.get(i).getProcessing());
//			} else if("total".equals(key)) {
//				triggerStatisticArray.add(triggerStatistics.get(i).getTotal());
//			}
//			jsonArray.add(triggerStatisticArray);
//		}
//		return jsonArray;
//	}
//	public double[] getYAxis(List<TriggerStatistics> triggerStatistics, String key) {
//		double[] vs = new double[triggerStatistics.size()];
//		for(int i = 0; i < triggerStatistics.size(); i++) {
//			if("processed".equals(key)) {
//				vs[i] = triggerStatistics.get(i).getProcessed();
//			} else if("ignored".equals(key)) {
//				vs[i] = triggerStatistics.get(i).getIgnored();
//			} else if("unprocessed".equals(key)) {
//				vs[i] = triggerStatistics.get(i).getUnprocessed();
//			} else if("processing".equals(key)) {
//				vs[i] = triggerStatistics.get(i).getProcessing();
//			} else if("total".equals(key)) {
//				vs[i] = triggerStatistics.get(i).getTotal();
//			}
//		}
//		return vs;
//	}
//
//	public String[] getXAxis(List<TriggerStatistics> triggerStatistics) {
//		String[] vs = new String[triggerStatistics.size()];
//		for(int i = 0; i < triggerStatistics.size(); i++) {
//			vs[i] = triggerStatistics.get(i).getTriggerDate();
//		}
//		return vs;
//	}
//
//
//	public String arryToString(String[] array) {
//		StringBuilder builder = new StringBuilder();
//		builder.append("[");
//		for(int i = 0; i < array.length; i++) {
//			builder.append("'").append(array[i]).append("'");
//			if(i < array.length -1) {
//				builder.append(",");
//			}
//		}
//		builder.append("]");
//		return builder.toString();
//	}

	@RequiresPermissions("trigger:record:export")
	@RequestMapping(value = "record/export", method= RequestMethod.POST)
	public String export(TriggerRecord triggerRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "triggerRecord-" + DateTime.now().toString("yyyy-MM-dd") + ".xlsx";
			Page<TriggerRecord> page = recordService.findRecord(new Page<TriggerRecord>(request, response, -1), triggerRecord);
			new ExportExcel("触发记录列表", TriggerRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/trigger/record/list?repage";
	}

//	@RequestMapping("info3")
//	@ResponseBody
//	public Map<String, Object> info3(String period) throws Exception {
//		Map<String, Object> data = recordService.getTriggerStatisticsByPeriod2(period, null);
//		
//		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("data", data);
//		response.put("period", period);
//
//		return response;
//	}
		
	@RequiresPermissions("trigger:statistics:list")
	@RequestMapping(value = {"statisticsList", ""})
	public String statisticsList(TriggerRecord triggerRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TriggerRecord> page = recordService.findStatistics(new Page<TriggerRecord>(request, response), triggerRecord);
		List<Epl> eplCategories = eplService.findEplCategory();
		model.addAttribute("page", page);
		model.addAttribute("eplCategories", eplCategories);
		return "modules/trigger/statisticsList";
	}
	
 
}
