package com.lh.modules.epl.web;

import com.lh.common.config.MyCache;
import com.lh.common.persistence.Page;
import com.lh.common.web.BaseController;
import com.lh.modules.common.entity.PushAlarmEpl;
import com.lh.modules.common.entity.PushEpl;
import com.lh.modules.common.servie.CommonService;
import com.lh.modules.epl.entity.AlarmEplRelation;
import com.lh.modules.epl.entity.AlarmGroup;
import com.lh.modules.epl.entity.AlarmThreshold;
import com.lh.modules.epl.entity.Epl;
import com.lh.modules.epl.entity.Threshold;
import com.lh.modules.epl.service.AlarmEplRelationService;
import com.lh.modules.epl.service.AlarmEplService;
import com.lh.modules.epl.service.AlarmGroupService;
import com.lh.modules.epl.service.AlarmThresholdService;
import com.lh.modules.epl.service.EplService;
import com.lh.modules.epl.service.ThresholdService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
 * 风控规则及阀值配置
 * @author 劉 焱
 * @date 2016-8-24
 * @tags
 */
@Controller
@RequestMapping(value = "${adminPath}/rtc/epl")
public class EplController extends BaseController {
	@Autowired
	private EplService eplService;
	@Autowired
	private ThresholdService thresholdService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private AlarmEplService alarmEplService;
	@Autowired
	private AlarmEplRelationService alarmEplRelationService;
	@Autowired
	private AlarmGroupService alarmGroupService;
	@Autowired
	private AlarmThresholdService alarmThresholdService;
	

	@RequiresPermissions("rtc:epl:list")
	@RequestMapping(value = {"list", ""})
	public String list(Epl epl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Epl> page = eplService.findPage(new Page<Epl>(request, response), epl);
		List<Epl> parentTypes = eplService.findParentTypeList();
		model.addAttribute("page", page);
		model.addAttribute("parentTypes", parentTypes);
		
		return "modules/epl/epl";
	}

	@RequiresPermissions(value={"rtc:epl:add","rtc:epl:edit","rtc:epl:alarm"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Epl epl, Model model, RedirectAttributes redirectAttributes) {
		eplService.edit(epl);

		return "redirect:" + adminPath + "/rtc/epl/list";
	}
	
	@RequiresPermissions("rtc:epl:del")
	@RequestMapping(value = "delete")
	public String delete(Epl epl, RedirectAttributes redirectAttributes) {
		PushEpl obj = eplService.pushZookeeper(epl.getEplId());
		epl.setStatus(3);
		eplService.deleteSoft(epl);
    	commonService.rtcEplPushData(obj, "remove", MyCache.ZOOKEEPER_RISK_PATH);
    	
		return "redirect:" + adminPath + "/rtc/epl/list";
	}

	@RequiresPermissions("rtc:epl:view")
	@RequestMapping(value = "form")
	public String editEpl(Epl epl, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Epl> parentTypes = eplService.findParentTypeList();
		model.addAttribute("parentTypes", parentTypes);
		List<Epl> alarmTypes = alarmEplService.findTypeList();
		model.addAttribute("alarmTypes", alarmTypes);
		if(epl.getId() != null) {
			Epl epl1 = eplService.findUniqueByProperty("id", epl.getId());
			Threshold threshold = new Threshold();
			threshold.setEplId(epl1.getEplId());
			List<Threshold> thresholds = thresholdService.findList(threshold);
			epl1.setThresholdList(thresholds);
			
			if(epl1.getIsAlarm() == 1){
				AlarmEplRelation alarmEplRelation1 = alarmEplRelationService.findUniqueByProperty("epl_id", epl1.getEplId());
				epl1.setAlarmId(alarmEplRelation1 == null ? null :alarmEplRelation1.getAlarmEplId());
			}
			
			model.addAttribute("epl", epl1);
			return "modules/epl/eplForm";
		}
		return "modules/epl/eplForm";
	}
	
	@RequiresPermissions(value={"rtc:epl:view"},logical=Logical.OR)
	@RequestMapping(value = "form1")
	public String checkEpl(Epl epl, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Epl> parentTypes = eplService.findParentTypeList();
		model.addAttribute("parentTypes", parentTypes);
		List<Epl> alarmTypes = alarmEplService.findTypeList();
		model.addAttribute("alarmTypes", alarmTypes);
		
		Epl epl1 = eplService.findUniqueByProperty("id", epl.getId());
		Threshold threshold = new Threshold();
		threshold.setEplId(epl1.getEplId());
		List<Threshold> thresholds = thresholdService.findList(threshold);
		epl1.setThresholdList(thresholds);
		
		if(epl1.getIsAlarm() == 1){
			AlarmEplRelation alarmEplRelation1 = alarmEplRelationService.findUniqueByProperty("epl_id", epl1.getEplId());
			epl1.setAlarmId(alarmEplRelation1 == null ? null :alarmEplRelation1.getAlarmEplId());
		}
		
		model.addAttribute("epl", epl1);
		return "modules/epl/eplForm1";
	}

	@RequiresPermissions(value={"rtc:epl:changeStatus","rtc:epl:add","rtc:epl:edit"},logical=Logical.OR)
	@RequestMapping(value = "changeStatus")
	public String updateStatus(Epl epl) {
		if(epl.getStatus() == 1){
			epl.setStatus(2);
		}else{
			epl.setStatus(1);
		}
		
		eplService.save(epl);
		
		PushEpl obj = eplService.pushZookeeper(epl.getEplId());
		if(epl.getStatus() == 1){//由下线修改为上线状态
	    	commonService.rtcEplPushData(obj, "add", MyCache.ZOOKEEPER_RISK_PATH);
		}else {//由上线修改为下线状态
	    	commonService.rtcEplPushData(obj, "remove", MyCache.ZOOKEEPER_RISK_PATH);
		}
		
		if(epl.getIsAlarm() == 1){
			AlarmEplRelation alarmEplRelation = alarmEplRelationService.findUniqueByProperty("epl_id", epl.getEplId());
			Integer alarmEplId = alarmEplRelation == null ? null : alarmEplRelation.getAlarmEplId();
			PushAlarmEpl obj2 = alarmEplService.pushZookeeper(alarmEplId);
			if(epl.getStatus() == 1){//由下线修改为上线状态
		    	commonService.alarmEplPushData(obj2, "add", MyCache.ZOOKEEPER_ALARM_PATH);
			}else {//由上线修改为下线状态
		    	commonService.alarmEplPushData(obj2, "remove", MyCache.ZOOKEEPER_ALARM_PATH);
			}
		}
		
		return "redirect:" + adminPath + "/rtc/epl/list";
	}

	@RequiresPermissions("rtc:epl:threshold")
	@RequestMapping(value = "threshold")
	public String getThresholds(Threshold threshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Threshold> p = new Page<Threshold>(request, response);
		p.setCount(100);
		p.setPageSize(100);
		Page<Threshold> page = thresholdService.findPage(p, threshold);
		model.addAttribute("page", page);
		
		return "modules/epl/threshold";
	}
	
	@RequiresPermissions("epl:threshold:view")
	@RequestMapping(value = "thresholdEdit")
	public String getThresholdsEdit(Threshold threshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Threshold> p = new Page<Threshold>(request, response);
		p.setCount(100);
		p.setPageSize(100);
		Page<Threshold> page = thresholdService.findPage(p, threshold);
		model.addAttribute("page", page);
		
		return "modules/epl/thresholdEdit";
	}
	
	@RequiresPermissions(value={"epl:threshold:del"})
	@RequestMapping(value = "deleteThreshold")
	public String deleteThreshold(Threshold threshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		thresholdService.delete(threshold);
    	PushEpl obj = eplService.pushZookeeper(threshold.getEplId());
    	commonService.rtcEplPushData(obj, "update", MyCache.ZOOKEEPER_RISK_PATH);
    	
		return "redirect:" + adminPath + "/rtc/epl/threshold?eplId="+threshold.getEplId();
	}

	@RequiresPermissions(value={"epl:threshold:add","epl:threshold:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveThreshold")
	@ResponseBody
	public Threshold saveThreshold(Threshold threshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean isNew = threshold.getId() == null ? true : false;
		try {
			threshold.setThresholdDescribe(URLDecoder.decode(threshold.getThresholdDescribe(),   "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		thresholdService.save(threshold);
    	PushEpl obj = eplService.pushZookeeper(threshold.getEplId());
    	commonService.rtcEplPushData(obj, "update", MyCache.ZOOKEEPER_RISK_PATH);
    	
		if (isNew){
			threshold = thresholdService.findUniqueByProperty("threshold_name", threshold.getThresholdName());
		}
		
		return threshold;
	}

	@RequiresPermissions(value={"rtc:epl:alarm"})
	@RequestMapping(value = "alarmForm")
	public String openAlarmForm(Epl epl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Epl epl1 = eplService.findAlarm(epl);
		epl1.setId(epl.getId());
		epl1.setIsAlarm(epl.getIsAlarm());
		epl1.setEplId(epl.getEplId());
		epl1.setStatus(epl.getStatus());
		model.addAttribute("epl", epl1);
		
		List<AlarmGroup> alarmGroups = alarmGroupService.findList();
		model.addAttribute("alarmGroups", alarmGroups);
		return "modules/epl/alarmForm";
	}

	@RequiresPermissions(value={"rtc:epl:alarm"})
	@RequestMapping(value = "deleteAlarmThreshold")
	@ResponseBody
	public String deleteAlarmThreshold(AlarmThreshold alarmThreshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		alarmThresholdService.deleteAlarmThreshold(alarmThreshold);
		
		return "redirect:" + adminPath + "/rtc/epl/alarmForm?eplId="+alarmThreshold.getEplId();
	}

	@RequiresPermissions(value={"rtc:epl:alarm"})
	@RequestMapping(value = "saveAlarmThreshold")
	@ResponseBody
	public AlarmThreshold saveAlarmThreshold(AlarmThreshold alarmThreshold, HttpServletRequest request, HttpServletResponse response, Model model) {
		alarmThresholdService.saveAlarmThreshold(alarmThreshold);
		
		return alarmThreshold;
	}

	@RequiresPermissions(value={"rtc:epl:alarm"})
	@RequestMapping(value = "saveAlarm")
	public String saveAlarm(Epl epl, Model model, RedirectAttributes redirectAttributes) throws Exception {
		eplService.saveAlarm(epl);

		return "redirect:" + adminPath + "/rtc/epl/list";
	}

	@RequiresPermissions(value={"rtc:epl:alarm"})
	@RequestMapping(value = "deleteAlarm")
	public String deleteAlarm(Epl epl, Model model, RedirectAttributes redirectAttributes) {
		eplService.deleteAlarm(epl);

		return "redirect:" + adminPath + "/rtc/epl/list";
	}
}