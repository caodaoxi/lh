package com.lh.common.websocket;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.codehaus.jackson.map.ObjectMapper;

import com.lh.common.config.MyCache;
import com.lh.common.config.MyConst;
import com.lh.common.utils.DateUtils;
import com.lh.common.utils.NumberUtil;
import com.lh.common.utils.SpringContextHolder;
import com.lh.modules.trigger.entity.TriggerStatistics;
import com.lh.modules.trigger.service.TriggerRecordService;


/**
 * 定时任务
 * @author 劉 焱
 * @date 2016-7-29
 * @tags
 */
public class MyTimerTask {
	/**
	 * 定时扫描数据库告警信息数量
	 */
	public void checkAlarmRecordCounts() {

		new Timer().schedule(new TimerTask(){ 
        //调用schedule方法执行任务
            public void run() {
        		TriggerRecordService recordService =  SpringContextHolder.getBean("triggerRecordService");
                // 查询当天未读消息
        		Map<String, String> map = new HashMap<String, String>();
        		Date date = new Date();
        		String nowDate = DateUtils.formatDate(date, MyConst.YYYY_MM_DD);
        		map.put("startDate", nowDate+" 00:00:00");
        		map.put("endDate", DateUtils.formatDate(date, MyConst.YYYY_MM_DD_HH_MM_SS));
        		if(MyCache.triggerUndeal.get("nowDate") == null || ! nowDate.equals((String)MyCache.triggerUndeal.get("nowDate"))){
        			MyCache.triggerUndeal.put("flag", true);
        			MyCache.triggerUndeal.put("nowDate", nowDate);
        			MyCache.triggerUndeal.put("unprocessed", 0);
        			MyCache.triggerUndeal.put("total", 0);
        			MyCache.triggerUndeal.put("processed", 0);
        			MyCache.triggerUndeal.put("processing", 0);
        			MyCache.triggerUndeal.put("triggers", "");
        			MyCache.triggerUndeal.put("list", "");
        			MyCache.triggerUndeal.put("currentStatistics", "");
        		}else{
        			MyCache.triggerUndeal.put("flag", false);
        		}
                TriggerStatistics triggerStatistics = recordService.findUndealAndTotalCount(map);
                
                System.out.println(MyCache.triggerUndeal);
            	if(triggerStatistics.getUnprocessed() != (Integer)MyCache.triggerUndeal.get("unprocessed") || triggerStatistics.getTotal() != (Integer)MyCache.triggerUndeal.get("total") ||
            			triggerStatistics.getProcessed() != (Integer)MyCache.triggerUndeal.get("processed") || triggerStatistics.getProcessing() != (Integer)MyCache.triggerUndeal.get("processing") || 
            			(Boolean)MyCache.triggerUndeal.get("flag")){
            		//右侧小铃铛
            		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
            		list = recordService.findUndealTypes(map);
            		MyCache.triggerUndeal.put("list", list);
            		MyCache.triggerUndeal.put("unprocessed", triggerStatistics.getUnprocessed());
            		
            		MyCache.triggerUndeal.put("total", triggerStatistics.getTotal());
            		MyCache.triggerUndeal.put("processed", triggerStatistics.getProcessed());
            		MyCache.triggerUndeal.put("processing", triggerStatistics.getProcessing());
            		MyCache.triggerUndeal.put("ignored", triggerStatistics.getIgnored());
            		
            		//当日各类型事件数量及占比情况
            		MyCache.triggerUndeal.put("currentStatistics", getCurrentStatistics(triggerStatistics));
            		
            		//获取日/时/分图形数据
            		try {
						MyCache.triggerUndeal.put("day", recordService.getTriggerStatisticsByPeriod2("day", map.get("endDate")));
						MyCache.triggerUndeal.put("hour", recordService.getTriggerStatisticsByPeriod2("hour", map.get("endDate")));
						MyCache.triggerUndeal.put("minute", recordService.getTriggerStatisticsByPeriod2("minute", map.get("endDate")));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
            		
            		//最新事件
        			List<Map<String, Object>> triggers = recordService.findLatest(map);
        			MyCache.triggerUndeal.put("triggers", triggers);
            		
            		//转换为json格式便于传输
            		ObjectMapper mapper = new ObjectMapper();
            		String json = "";
            		try {
            			json = mapper.writeValueAsString(MyCache.triggerUndeal);
					} catch (IOException e) {
						e.printStackTrace();
					}
            		MyCache.triggerUndeal.put("list", json);
            		System.out.println(MyCache.triggerUndeal);
            		System.out.println((String) MyCache.triggerUndeal.get("list"));
            		//发送给所有人
            		MyWebSocketSendMsg socketSendMsg = new MyWebSocketSendMsg();
            		socketSendMsg.sendMessageToUsers((String) MyCache.triggerUndeal.get("list"));
            	}

            }

        } , 10000 , MyCache.timerWebsocketInterval);//过10秒后执行，之后每隔300毫秒执行一次
	}
	
	/**
	 * 当日各类型事件数量及占比情况
	 * @param triggerStatistics
	 * @return
	 */
	private static Map<String, Object> getCurrentStatistics(TriggerStatistics triggerStatistics) {
		Map<String, Object> currentStatistics = new HashMap<String, Object>();
		currentStatistics.put("unprocessed", triggerStatistics.getUnprocessed());
		currentStatistics.put("unprocessedPro", triggerStatistics.getTotal() == 0 ? "0%" : 
			NumberUtil.doubleCutToStrRound(Double.parseDouble(triggerStatistics.getUnprocessed()*100/triggerStatistics.getTotal() + ""), 2) + "%");
		currentStatistics.put("processed", triggerStatistics.getProcessed());
		currentStatistics.put("processedPro", triggerStatistics.getTotal() == 0 ? "0%" : 
			NumberUtil.doubleCutToStrRound(Double.parseDouble(triggerStatistics.getProcessed()*100/triggerStatistics.getTotal() + ""), 2) + "%");
		currentStatistics.put("processing", triggerStatistics.getProcessing());
		currentStatistics.put("processingPro", triggerStatistics.getTotal() == 0 ? "0%" : 
			NumberUtil.doubleCutToStrRound(Double.parseDouble(triggerStatistics.getProcessing()*100/triggerStatistics.getTotal() + ""), 2) + "%");
		currentStatistics.put("ignored", triggerStatistics.getIgnored());
		currentStatistics.put("ignoredPro", triggerStatistics.getTotal() == 0 ? "0%" : 
			NumberUtil.doubleCutToStrRound(Double.parseDouble(triggerStatistics.getIgnored()*100/triggerStatistics.getTotal() + ""), 2) + "%");
		return currentStatistics;
	}
}
