package com.lh.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lh.common.config.LoadConfig;
import com.lh.common.websocket.MyTimerTask;


/**
 * 在服务器启动的时候把Web应用路径放到application应用范围内
 * @author 汪涛
 *
 */
public class ServerStartupListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	/**
	 * 1. 在服务器启动的时候把Web应用路径放到application应用范围内
	 * 2. 加载配置文件
	 * 3. 启动定时任务
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		application.setAttribute("appPath", application.getContextPath());
		LoadConfig.load();
		MyTimerTask task = new MyTimerTask();
		task.checkAlarmRecordCounts();
	}
	
}
