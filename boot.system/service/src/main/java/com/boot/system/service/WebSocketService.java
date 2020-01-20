package com.boot.system.service;

import javax.websocket.Session;

public interface WebSocketService {
	
	void onOpen(Session session, String sid);
	
	void onClose();
	
	void onMessage(String message, Session session);
	
	void onError(Session session, Throwable error);
	
	void sendMessage(String message);
	
	 void sendInfo(String message, String sid);
	 
	 int getOnlineCount();
	 
	 void addOnlineCount();
	 
	 void subOnlineCount();

}
