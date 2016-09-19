package com.lh.common.websocket;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lh.common.config.MyCache;
import com.lh.common.config.MyConst;
import com.lh.modules.sys.entity.User;

/**
 * websocke的收到消息的具体实现
 * @author wangyong01@szkingdom
 *
 */
@Component("webSokcetReceivedMsg") //配置文件生成相应Bean
public class MyWebSocketReceivedMsg {
	private static Logger logger=Logger.getLogger(MyWebSocketReceivedMsg.class);
	@Resource
	private  MyWebSocketSendMsg webSocketSendMsg;
	
	/**
	 * httpsession待完善, 不能使用
	 * @param session
	 * @param webSocketMessage
	 * @throws Exception
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> webSocketMessage) throws Exception {
		// 检查HttpSession是否过期
		User user = (User) session.getAttributes().get(MyConst.SESSION_USER);
		System.out.println(session.getAttributes().get(MyConst.SESSION_USER));
		String userId = user == null ? null : user.getId();
		if (!checkHttpSession(session, userId)) {
			webSocketSendMsg.closeSessionByUserId(userId);// 过期关闭
			return;
		} else {
			// TODO 延长httpSession存活时间 防止HttpSession过期
		}
		// 获取文本消息
		TextMessage textMessage = new TextMessage(webSocketMessage.getPayload()+ "");
		String content = textMessage.getPayload();
		logger.info("userId" + userId + " ;content" + content);
		// 处理信息
//		dealStringMsg(userName, content);
	}
	
	
	/**
	 * 握手成功后 初次链接成功执行的方法
	 * @param session
		 * @throws Exception
	 */
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		User user = (User) session.getAttributes().get(MyConst.SESSION_USER);
        String userId = user.getId();
        // 放入容器中
        MyCache.userWebSocketSession.put(userId, session);
        // 发送未处理消息
        webSocketSendMsg.sendTextMessageToUser(userId, (String) MyCache.triggerUndeal.get("list"));
    }
	
	/**
	 * 检查HttpSession是否过期，通过返回true，不通过返回false
	 * @param session
	 * @return 
	 */
	private boolean checkHttpSession(WebSocketSession session,String userId){
    	HttpSession httpSession = MyCache.userHttpSessionMap.get(userId);
    	if(httpSession==null){
    		return false;
    	}else{
    		return true;
    	}
	}

}
