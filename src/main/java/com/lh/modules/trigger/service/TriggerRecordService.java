package com.lh.modules.trigger.service;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lh.common.config.MyConst;
import com.lh.common.persistence.Page;
import com.lh.common.service.CrudService;
import com.lh.common.utils.DateUtil;
import com.lh.common.utils.DateUtils;
import com.lh.common.utils.StringUtil;
import com.lh.modules.trigger.dao.TriggerRecordDao;
import com.lh.modules.trigger.entity.Detail;
import com.lh.modules.trigger.entity.TriggerRecord;
import com.lh.modules.trigger.entity.TriggerStatistics;

/**
 * 预警触发记录
 * @author 劉 焱
 * @date 2016-8-19
 * @tags
 */
@Service
@Transactional(readOnly = true)
public class TriggerRecordService extends CrudService<TriggerRecordDao, TriggerRecord> {
	
	public TriggerRecord get(String id) {
		TriggerRecord entity = dao.get(id);
		entity = replaceStrVar(entity);
		return entity;
	}

	/**
	 * 触发记录列表
	 * @param page
	 * @param triggerRecord
	 * @return
	 */
	public Page<TriggerRecord> findRecord(Page<TriggerRecord> page, TriggerRecord triggerRecord) {
		triggerRecord.setPage(page);
		page.setList(replaceStrVar(dao.findList(triggerRecord)));
		return page;
	}

	/**
	 * 更新阅读状态
	 */
	@Transactional(readOnly = false)
	public void updateReadFlag(TriggerRecord record) {
		record.setReadFlag("1");
		dao.update(record);
	}

	/**
	 * 触发记录统计
	 * @throws ParseException 
	 */
	@Transactional(readOnly = false)
	public List<TriggerStatistics> getTriggerStatistics(String period) throws ParseException {
		Map<String, String> requestMap = getRequestMap(period);
		return dao.getTriggerStatistics(requestMap);
	}

	/**
	 * 阀值替换
	 * @param entity
	 * @return
	 */
	public List<TriggerRecord> replaceStrVar(List<TriggerRecord> list){
		List<TriggerRecord> returnList = new ArrayList<TriggerRecord>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			for(TriggerRecord triggerRecord : list){
				Map<String, Object> map = mapper.readValue(triggerRecord.getTriggerState(), Map.class);
				TriggerRecord record = triggerRecord;
				String triggerState = triggerRecord.getTextState();

				for (Object s : map.keySet()) {
					if("start_time".equals(s.toString()) || "end_time".equals(s.toString())){
						String time = DateUtils.getDateByTimestamp((Long) map.get(s.toString()), "yyyy-MM-dd HH:mm:ss");
						triggerState = triggerState.replaceAll("\\$\\{".concat(s.toString()).concat("\\}"), time);
					}else{
						triggerState = triggerState.replaceAll("\\$\\{".concat(s.toString()).concat("\\}"), map.get(s.toString()).toString());
					}
				}
				String decrible = getEplSql(triggerRecord.getThresholds(), triggerRecord.getEplDescribe());
				record.setEplDescribe(decrible);
				record.setTriggerState(triggerState);
				returnList.add(record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 单条阀值替换
	 * @param triggerRecord
	 * @return
	 */
	public TriggerRecord replaceStrVar(TriggerRecord triggerRecord){
		ObjectMapper mapper = new ObjectMapper();
		TriggerRecord record = triggerRecord;
		try {
				Map<String, Object> map = mapper.readValue(triggerRecord.getTriggerState(), Map.class);
				String triggerState = triggerRecord.getTextState();

				for (Object s : map.keySet()) {
					if("start_time".equals(s.toString()) || "end_time".equals(s.toString())){
						String time = DateUtils.getDateByTimestamp((Long) map.get(s.toString()), "yyyy-MM-dd HH:mm:ss");
						triggerState = triggerState.replaceAll("\\$\\{".concat(s.toString()).concat("\\}"), time);
					}else{
						triggerState = triggerState.replaceAll("\\$\\{".concat(s.toString()).concat("\\}"), map.get(s.toString()).toString());
					}
				}
				String decrible = getEplSql(triggerRecord.getThresholds(), triggerRecord.getEplDescribe());
				record.setEplDescribe(decrible);
				record.setTriggerState(triggerState);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return record;
	}
	/**
	 * 规则阀值替换
	 * @param thresholds
	 * @param eplSql
	 * @return
	 */
	public String getEplSql(String thresholds, String eplSql){
		if(thresholds != null && !StringUtils.isEmpty(thresholds)) {
			String[] dts = thresholds.split(",");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for(String threshold : dts) {
				String[] fields = threshold.split("=");
				paramMap.put(fields[0], fields[1]);
			}
			eplSql = replaceStrVar(paramMap, eplSql);
		}
		return eplSql;
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

	/**
	 * 触发记录获取详情页
	 * @param pageNumber
	 * @param pageSize
	 * @param fundid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Detail> queryDetailPage(Integer pageNumber, Integer pageSize, long fundid, long startDate, long endDate) {
		String[] fieldList = new String[]{"@fields.timestamp", "@fields.rc_msg_para.fundid", "@fields.stationaddr", "@fields.rc_msg_para.orderprice", "@fields.rc_msg_para.market", "@fields.rc_msg_para.orderqty", "@fields.rc_msg_para.stkcode"};
		List<Detail> details = null;
		details = getDetail(fundid, fieldList, startDate, endDate, pageNumber, pageSize);
		return details;
	}

	/**
	 * 获取详情
	 * @param fundid
	 * @param fieldList
	 * @param start
	 * @param end
	 * @param from
	 * @param size
	 * @return
	 */
	public static List<Detail> getDetail(long fundid, String[] fieldList, long start, long end, int from, int size) {
		Client client = getClient();
		ArrayList<Detail> details = null;
		try {
			QueryBuilder termQuery = termQuery( "@fields.rc_msg_para.fundid", fundid);
			QueryBuilder rangeQuery = rangeQuery("@fields.timestamp").from(start).to(end);
			QueryBuilder boolQuery = boolQuery().must(termQuery).must(rangeQuery);
			SearchResponse response = client.prepareSearch("rtc*").setTypes("Entrust")
					.addFields(fieldList)
					.setQuery(boolQuery).setFrom(from).setSize(size).execute().actionGet();
			SearchHits hits = response.getHits();
			if(hits.getTotalHits() == 0) return details;
			details = new ArrayList<Detail>();
			for (SearchHit hit : hits.getHits()) {
				Detail detail = new Detail();
				detail.setFundid(Long.parseLong(hit.field("@fields.rc_msg_para.fundid").getValue().toString()));
				detail.setMarket(hit.field("@fields.rc_msg_para.market").getValue().toString());
				detail.setOrderprice(Double.parseDouble(hit.field("@fields.rc_msg_para.orderprice").getValue().toString()));
				detail.setOrderqty(Double.parseDouble(hit.field("@fields.rc_msg_para.orderqty").getValue().toString()));
				detail.setStationaddr(hit.field("@fields.stationaddr").getValue().toString());
				detail.setStkcode(hit.field("@fields.rc_msg_para.stkcode").getValue().toString());
				detail.setDt(new Date(Long.parseLong(hit.field("@fields.timestamp").getValue().toString())));
				details.add(detail);
			}
			return details;
		} finally {
			client.close();
		}
	}

	public static Client getClient() {
		Client client = null;
		Settings settings = null;
		try {
//			settings = ImmutableSettings.settingsBuilder()
//					.put("cluster.name", "rtc").build();
			settings = Settings.settingsBuilder()
					.put("cluster.name", "rtc").build();
			client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.103"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.104"), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.105"), 9300));
//			client = new TransportClient(settings)
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.103"), 9300))
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.104"), 9300))
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.171.105"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 根据时段统计触发记录
	 * @throws ParseException 
	 */
	@Transactional(readOnly = false)
	public List<TriggerStatistics> getTriggerStatisticsByPeriod(String period) throws ParseException {
		Map<String, String> requestMap = getRequestMap(period);
		return dao.getTriggerStatisticsByPeriod(requestMap);
	}

	/**
	 * 获取SQL查询参数
	 * @param period
	 * @return
	 * @throws ParseException
	 */
	private Map<String, String> getRequestMap(String period) throws ParseException {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("period", period);
		String startDate = "";
		if("minute".equals(period)){
			startDate = DateUtils.getIntervalTime(new Date(), MyConst.YYYY_MM_DD_HH_MM, -149*60);
		}else if("hour".equals(period)){
			startDate = DateUtils.getIntervalTime(new Date(), MyConst.YYYY_MM_DD_HH, -23*60*60);
		}else{
			startDate = DateUtils.getIntervalTime(new Date(), MyConst.YYYY_MM_DD, -29*24*60*60);
		}
		requestMap.put("startDate", startDate);
		return requestMap;
	}

	/**
	 * 根据时段统计触发记录并封装数据
	 * @param period
	 * @return
	 * @throws ParseException 
	 */
	public Map<String, Object> getTriggerStatisticsByPeriod2(String period, String endDateStr) throws ParseException {
		if(StringUtil.isEmpty(period)){
			period = "day";
		}
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("period", period);
		String startDate = "";
		Date endDate = StringUtil.isEmpty(endDateStr) ? new Date() : DateUtil.stringToDate(endDateStr, MyConst.YYYY_MM_DD_HH_MM_SS);
		if("minute".equals(period)){
			startDate = DateUtils.getIntervalTime(endDate, MyConst.YYYY_MM_DD_HH_MM, -29*60);
		}else if("hour".equals(period)){
			startDate = DateUtils.getIntervalTime(endDate, MyConst.YYYY_MM_DD_HH, -23*60*60);
		}else{
			startDate = DateUtils.getIntervalTime(endDate, MyConst.YYYY_MM_DD, -29*24*60*60);
		}
		requestMap.put("startDate", startDate);
		requestMap.put("endDate", DateUtil.dateToString(endDate, MyConst.YYYY_MM_DD_HH_MM_SS));
		
		List<TriggerStatistics> triggerStatistics = dao.getTriggerStatisticsByPeriod(requestMap);
		Map<String, Object> data = new HashMap<String, Object>();
		//数据当前增长情况
		TriggerStatistics triggerStatistics2 = getTriggerIncrease(requestMap);
		Map<String, Object> statisticsMap = new HashMap<String, Object>();
		statisticsMap.put("totalAdd", triggerStatistics2.getTotalAdd());
		statisticsMap.put("totalPre", triggerStatistics2.getTotalPre());
		statisticsMap.put("unprocessedAdd", triggerStatistics2.getUnprocessedAdd());
		statisticsMap.put("unprocessedPre", triggerStatistics2.getUnprocessedPre());
		statisticsMap.put("processingAdd", triggerStatistics2.getProcessingAdd());
		statisticsMap.put("processingPre", triggerStatistics2.getProcessingPre());
		statisticsMap.put("processedAdd", triggerStatistics2.getProcessedAdd());
		statisticsMap.put("processedPre", triggerStatistics2.getProcessedPre());
		statisticsMap.put("ignoredAdd", triggerStatistics2.getIgnoredAdd());
		statisticsMap.put("ignoredPre", triggerStatistics2.getIgnoredPre());
		data.put("triggerStatistics", statisticsMap);
		
		List<String> map = new ArrayList<String>();
		//获得开始时间结束时间之间的日期数组
		//以天为单位统计[默认]
		if ("day".equals(period)) {
			map = DateUtil.getEveryDayStr(startDate, new Date(), MyConst.YYYY_MM_DD, MyConst.YYYY_MM_DD);
		//以小时为单位统计
		} else if ("hour".equals(period)) {
			map = DateUtil.getEveryHourStr(new Date(), 1, 24, MyConst.YYYY_MM_DD_HH);
		} else if ("minute".equals(period)) {
			map = DateUtil.getEveryMinuteStr(new Date(), 1, 30, MyConst.YYYY_MM_DD_HH_MM);
		}
		
		return dataHandle(period, map, triggerStatistics, data);
	}

	/**
	 * 触发记录当前时间段增长情况
	 * @param requestMap
	 * @return
	 */
	private TriggerStatistics getTriggerIncrease(Map<String, String> requestMap) {
		List<TriggerStatistics> list1 = dao.getTriggerStatisticsNew(requestMap);
		List<TriggerStatistics> list2 = dao.getTriggerStatisticsOld(requestMap);
		TriggerStatistics triggerStatistics1 = new TriggerStatistics();
		TriggerStatistics triggerStatistics2 = new TriggerStatistics();
		if(list1 != null && list1.size() != 0){
			triggerStatistics1 = list1.get(0);
		}
		if(list2 != null && list2.size() != 0){
			triggerStatistics2 = list2.get(0);
		}
		
		TriggerStatistics triggerStatistics = new TriggerStatistics();
		triggerStatistics.setProcessedAdd(triggerStatistics1.getProcessed() - triggerStatistics2.getProcessed());
		triggerStatistics.setProcessingAdd(triggerStatistics1.getProcessing() - triggerStatistics2.getProcessing());
		triggerStatistics.setIgnoredAdd(triggerStatistics1.getIgnored() - triggerStatistics2.getIgnored());
		triggerStatistics.setUnprocessedAdd(triggerStatistics1.getUnprocessed() - triggerStatistics2.getUnprocessed());
		triggerStatistics.setTotalAdd(triggerStatistics1.getTotal() - triggerStatistics2.getTotal());
		
		if(triggerStatistics1.getProcessed() == 0 && triggerStatistics2.getProcessed() == 0){
			triggerStatistics.setProcessedPre(0);
		}else if(triggerStatistics2.getProcessed() == 0){
			triggerStatistics.setProcessedPre(100);
		}else{
			triggerStatistics.setProcessedPre((triggerStatistics1.getProcessed() - triggerStatistics2.getProcessed())*100 / triggerStatistics2.getProcessed());
		}
		
		if(triggerStatistics1.getProcessing() == 0 && triggerStatistics2.getProcessing() == 0){
			triggerStatistics.setProcessingPre(0);
		}else if(triggerStatistics2.getProcessing() == 0){
			triggerStatistics.setProcessingPre(100);
		}else{
			triggerStatistics.setProcessingPre((triggerStatistics1.getProcessing() - triggerStatistics2.getProcessing())*100 / triggerStatistics2.getProcessing());
		}
		
		if(triggerStatistics1.getIgnored() == 0 && triggerStatistics2.getIgnored() == 0){
			triggerStatistics.setIgnoredPre(0);
		}else if(triggerStatistics2.getIgnored() == 0){
			triggerStatistics.setIgnoredPre(100);
		}else{
			triggerStatistics.setIgnoredPre((triggerStatistics1.getIgnored() - triggerStatistics2.getIgnored())*100 / triggerStatistics2.getIgnored());
		}
		
		if(triggerStatistics1.getUnprocessed() == 0 && triggerStatistics2.getUnprocessed() == 0){
			triggerStatistics.setUnprocessedPre(0);
		}else if(triggerStatistics2.getUnprocessed() == 0){
			triggerStatistics.setUnprocessedPre(100);
		}else{
			triggerStatistics.setUnprocessedPre((triggerStatistics1.getUnprocessed() - triggerStatistics2.getUnprocessed())*100 / triggerStatistics2.getUnprocessed());
		}
		
		if(triggerStatistics1.getTotal() == 0 && triggerStatistics2.getTotal() == 0){
			triggerStatistics.setTotalPre(0);
		}else if(triggerStatistics2.getTotal() == 0){
			triggerStatistics.setTotalPre(100);
		}else{
			triggerStatistics.setTotalPre((triggerStatistics1.getTotal() - triggerStatistics2.getTotal())*100 / triggerStatistics2.getTotal());
		}
		
		return triggerStatistics;
	}

	
	/**
	 * 数据封装
	 * @param map
	 * @param statistics
	 * @param data
	 * @return
	 */
	private Map<String, Object> dataHandle(String period, List<String> map, List<TriggerStatistics> triggerStatistics, Map<String, Object> data) {
		List<String> xAxisData = new ArrayList<String>();
		xAxisData.addAll(map);
		
		//设置日期横轴数据
		List<String> xAxisData2 = new ArrayList<String>();
		if ("day".equals(period)) {
			for(String date : xAxisData){
				xAxisData2.add(Integer.parseInt(date.substring(5, 7))+"月"+Integer.parseInt(date.substring(8, 10))+"日");
			}
		} else if ("hour".equals(period)) {
			for(String date : xAxisData){
				xAxisData2.add(date.substring(11));
			}
		} else if ("minute".equals(period)) {
			for(String date : xAxisData){
				xAxisData2.add(date.substring(11));
			}
		}
		data.put("xAxisData", xAxisData2);
		
		//设置数据类别[中间标题]
		List<String> legendData1 = new ArrayList<String>();
		legendData1.add("总数");
		legendData1.add("未处理数");
		data.put("legendData1", legendData1);

		//横轴长度
		int xSize = xAxisData.size();
		//总数
		int[] total = new int[xSize];
		//未处理数
		int[] unprocesseds = new int[xSize];
		//已处理数
//		int[] processeds = new int[xSize];
//		//忽略数
//		int[] ignoreds = new int[xSize];
//		//处理中数
//		int[] processings = new int[xSize];

//		System.out.println(triggerStatistics);
		for(TriggerStatistics statistic : triggerStatistics){
			String date = statistic.getTriggerDate();
			int index = xAxisData.indexOf(date);
			total[index] = statistic.getTotal();
			unprocesseds[index] = statistic.getUnprocessed();
		}
		
		//统计
		data.put("unprocesseds", unprocesseds);
		data.put("total", total);

		return data;
	}
	
	/**
	 * 获取最新十条未处理记录
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> findLatest(Map<String, String> map) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<TriggerRecord> returnList = replaceStrVar(dao.findLatest(map));
		for(TriggerRecord record : returnList){
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("triggerTime", record.getTriggerTime());
			map1.put("eplDescribe", record.getEplDescribe());
			map1.put("eplName", record.getEplName());
			map1.put("fundid", record.getFundid());
			map1.put("orgname", record.getOrgname());
			map1.put("triggerState", record.getTriggerState());
			map1.put("status", record.getStatus());
			map1.put("dealTime", record.getDealTime());
			
			list.add(map1);
		}
		return list;
	}
	/**
	 * 触发记录统计页面分页查询
	 * @param page
	 * @param triggerRecord
	 * @return
	 */
	public Page<TriggerRecord> findStatistics(Page<TriggerRecord> page, TriggerRecord triggerRecord) {
		triggerRecord.setPage(page);
		List<TriggerRecord> list = dao.findStatisticsList(triggerRecord);
		TriggerRecord total = dao.findStatisticsTotalList(triggerRecord);
		if(total == null){
			total = new TriggerRecord();
		}
		TriggerRecord record = new TriggerRecord();
		record.setEplName("total");
		record.setDealCount(total.getDealCount());
		record.setIgnoreCount(total.getIgnoreCount());
		record.setUndealCount(total.getUndealCount());
		record.setVerificationCount(total.getVerificationCount());
		record.setTotalCount(total.getTotalCount());
		list.add(record);
		
		page.setList(list);
		return page;
	}
	/**
	 * 获取未处理各类型触发记录数量
	 * @return
	 */
	public List<Map<String, Object>> findUndealTypes(Map<String, String> map) {
		return  dao.findUndealTypes(map);
	}
	/**
	 * 获取各状态触发记录数量
	 * @param map
	 * @return
	 */
	public TriggerStatistics findUndealAndTotalCount(Map<String, String> map) {
		return dao.findUndealAndTotalCount(map);
	}
	
}