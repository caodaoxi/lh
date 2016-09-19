/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lh.common.websocket;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.lh.common.config.MyCache;

/**
 * WebSocket消息处理器
 * @author 劉 焱
 * @date 2016-7-29
 * @tags
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {
	@Resource
	private MyWebSocketReceivedMsg myWebSocketReceivedMsg;

	/**
	 * 握手成功后 初次链接成功执行
	 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        myWebSocketReceivedMsg.afterConnectionEstablished(session);
    }

    /**
     * 接受消息处理消息
     */
    @Override
    public void handleMessage(WebSocketSession wss, WebSocketMessage<?> wsm) throws Exception {
    	TextMessage returnMessage = new TextMessage((String) MyCache.triggerUndeal.get("list"));
		wss.sendMessage(returnMessage);
    }

    /**
     * 处理一个错误从底层WebSocket消息。
     */
    @Override
    public void handleTransportError(WebSocketSession wss, Throwable thrwbl) throws Exception {
        if(wss.isOpen()){
            wss.close();
        }
//       System.out.println("websocket connection closed......");
    }

    /**
     * 连接关闭后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession wss, CloseStatus cs) throws Exception {
//        System.out.println("websocket connection closed......");
    }

    /**
     * 是否分段发送消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
}
