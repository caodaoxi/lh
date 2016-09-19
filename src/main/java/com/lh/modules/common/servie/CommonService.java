package com.lh.modules.common.servie;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.lh.common.config.MyCache;
import com.lh.modules.common.entity.PushAlarmEpl;
import com.lh.modules.common.entity.PushEpl;


/**
 * 公共方法实现层
 * @author 劉 焱
 * @date 2016-8-1
 * @tags
 */
@Service
public class CommonService{
	/**
	 * 向zookeeper节点中推送规则变更信息
	 */
	public void pushZookeeper(String json, String path) {
		CuratorFramework curator = CuratorFrameworkFactory
				.newClient(MyCache.ZOOKEEPER_NODE, 5000, 5000, new RetryNTimes(10, 5000));
		curator.start();
		try {
			if(curator.checkExists().forPath(path) == null ){
				curator.create().forPath(path, json.getBytes());
			}else{
				curator.setData().forPath(path, json.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 报警规则推送信息处理
	 */
	public void alarmEplPushData(PushAlarmEpl pushAlarmEpl, String action, String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
	    	Map<String, Object> alarmMap = new HashMap<String, Object>();
	    	alarmMap.put("alarmTemplate", pushAlarmEpl.getAlarmTemplate());
	    	alarmMap.put("alarmType", pushAlarmEpl.getAlarmType());
	    	alarmMap.put("email", pushAlarmEpl.getEmail());
	    	alarmMap.put("id", pushAlarmEpl.getId());
	    	alarmMap.put("phone", pushAlarmEpl.getPhone());
	
	    	map.put("alarm", alarmMap);
	    	map.put("type", "epl");
	    	map.put("eplName", pushAlarmEpl.getEplName());
	    	map.put("eplSql", getEplSql(pushAlarmEpl.getThresholds(), pushAlarmEpl.getEpl()));
	    	map.put("id", pushAlarmEpl.getId());
	    	map.put("action", action);
	    	
	    	String json = mapper.writeValueAsString(map);
			pushZookeeper(json, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 风控规则推送信息处理
	 */
	public void rtcEplPushData(PushEpl pushEpl, String action, String path) {
		ObjectMapper mapper = new ObjectMapper();
		try {
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("type", "epl");
	    	map.put("eplName", pushEpl.getEplName());
	    	map.put("eplSql", getEplSql(pushEpl.getThresholds(), pushEpl.getEpl()));
	    	map.put("id", pushEpl.getId());
	    	map.put("action", action);
	    	
	    	String json = mapper.writeValueAsString(map);
			pushZookeeper(json, path);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	/**
	 * 遍历规则阀值并替换
	 * @param thresholds
	 * @param alarmEplSql
	 * @return
	 */
	public String getEplSql(String thresholds, String alarmEplSql){
    	if(thresholds != null && !StringUtils.isEmpty(thresholds)) {
    		String[] dts = thresholds.split(",");
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		for(String threshold : dts) {
    			String[] fields = threshold.split("=");
    			paramMap.put(fields[0], fields[1]);
    		}
    		alarmEplSql = replaceStrVar(paramMap, alarmEplSql);
    	}
    	return alarmEplSql;
	}
	/**
	 * 替换规则中的阀值
	 * @param map
	 * @param template
	 * @return
	 */
    public String replaceStrVar(Map<String, Object> map, String template){
        for (Object s : map.keySet()) {
            template = template.replaceAll("\\$\\{".concat(s.toString()).concat("\\}"), map.get(s.toString()).toString());
        }
        return template;
    }
}
