package com.lh.common.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 加载配置文件
 * @author 劉 焱
 * @date 2016-8-9
 * @tags
 */
public class LoadConfig {
    static private String zookeeperNode = "10.1.171.103:2181,10.1.171.104:2181,10.1.171.105:2181,10.1.171.106:2181";
    static private String zookeeperRiskPath = "/risk/rule";
    static private String zookeeperAlarmPath = "/alarm/rule";
    static private long timerWebsocketInterval = 1000;
    
    public static void load(){
		org.springframework.core.io.Resource resource = new ClassPathResource("config.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			System.out.println("config文件加载中......");
			zookeeperNode = props.getProperty("zookeeper_node").toString().trim();
			zookeeperRiskPath = props.getProperty("zookeeper_risk_path").toString().trim();
			zookeeperAlarmPath = props.getProperty("zookeeper_alarm_path").toString().trim();
			timerWebsocketInterval = Long.parseLong(props.getProperty("timer_websocket_interval").toString().trim());
		} catch (IOException e) {
			System.out.println("config文件加载出错!");
			e.printStackTrace();
		}
	}
    
    public static String getZookeeperNode() {
		return zookeeperNode;
	}

	public static String getZookeeperRiskPath() {
		return zookeeperRiskPath;
	}

	public static String getZookeeperAlarmPath() {
		return zookeeperAlarmPath;
	}

	public static long getTimerWebsocketInterval() {
		return timerWebsocketInterval;
	}

}