package com.lh.common.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.socket.WebSocketSession;


/**
 * 存放所有的缓存变量
 * @author 劉 焱
 * @date 2016-7-29
 * @tags
 */
public class MyCache {
	/**
	 * 触发记录未处理总数
	 */
	public static Map<String, Object> triggerUndeal = new ConcurrentHashMap<String, Object>();
	/**
     *  存放用户与WebSocketSession对应信息, key为userName value为WebSocketSession
     */
    public static final Map<String,WebSocketSession> userWebSocketSession = new ConcurrentHashMap<String,WebSocketSession>(50);
	/**
	 * 存放用户登录的HttpSession容器 其key为userId;
	 */
	public static final Map<String, HttpSession> userHttpSessionMap = new ConcurrentHashMap<String, HttpSession>(50);
	/** 
	 * Zookeeper节点地址 
	 */
	public static final String ZOOKEEPER_NODE = LoadConfig.getZookeeperNode();
	/** 
	 * Zookeeper风控规则路径 
	 */
	public static final String ZOOKEEPER_RISK_PATH = LoadConfig.getZookeeperRiskPath();
	/** 
	 * Zookeeper报警规则路径 
	 */
	public static final String ZOOKEEPER_ALARM_PATH = LoadConfig.getZookeeperAlarmPath();	
	/**
	 * websocket推送任务轮询数据库时间间隔
	 */
	public static long timerWebsocketInterval  = LoadConfig.getTimerWebsocketInterval();
}
