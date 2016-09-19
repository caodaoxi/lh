package com.lh.common.websocket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lh.common.config.MyCache;


/**
 * websocket消息发送
 * @author 劉 焱
 * @date 2016-8-1
 * @tags
 */
@Component("webSocketSendMsg")
public class MyWebSocketSendMsg {
	
	private static final Logger logger=Logger.getLogger(MyWebSocketSendMsg.class);
	 
    
    /**
     * 发送文本消息给所有人 
     * @param
     */
    public  void sendMessageToUsers(String message) {
    	try {
			TextMessage textMessage=new TextMessage(message.getBytes("UTF-8"));
			sendMessageToUsers(textMessage);
		} catch (UnsupportedEncodingException e) {
			logger.warn("不支持UTF-8字符集"+message, e);
		}
    }

    
    /**
     * 发送消息至指定用户
     * @param userId
     * @param message
     * @return
     */
    public boolean sendTextMessageToUser(String userId, String message) {
		try {
			logger.info(message);
			TextMessage textMessage = new TextMessage(message.getBytes("UTF-8"));
			boolean result = sendTextMessageToUser(userId, textMessage);
			return result;
		} catch (UnsupportedEncodingException e) {
			logger.warn("不支持UTF-8字符集:"+message, e);
			e.printStackTrace();
		}
		return false;
    }
    
    /**
     * 主动关闭WebSocketSession
     * @param 
     */
    public  void closeSessionByUserId(String userId){
    	WebSocketSession session = MyCache.userWebSocketSession.get(userId);
    	if(session!=null&&session.isOpen()){
    		try {
				session.close();
			} catch (IOException e) {
				logger.info("主动关闭失败",e);
			}
    	}
    }
    
    /**
     * 给某个用户发送消息
     * @param userId
     * @param message
     * @return
     */
    private  boolean sendTextMessageToUser(String userId, TextMessage message) {
    	WebSocketSession session = MyCache.userWebSocketSession.get(userId);
        try {
            if (session!=null&&session.isOpen()) {
            	session.sendMessage(message);
            	return true;
            }
        } catch (IOException e) {
        	logger.info("发送失败"+message,e);
        }
        return false;
    }
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    private  void sendMessageToUsers(TextMessage message) {
    	Iterator<Entry<String, WebSocketSession>> ites=MyCache.userWebSocketSession.entrySet().iterator();
    	while(ites.hasNext()){
    		Entry<String, WebSocketSession> entry=ites.next();
    		WebSocketSession session=entry.getValue();
    		System.out.println("session----------------------->\n"+session);
            try {
                if (session!=null&&session.isOpen()) {
                	session.sendMessage(message);
                }
            } catch (IOException e) {
            	logger.info("发送失败"+message,e);
            }

    	}
    }

}
