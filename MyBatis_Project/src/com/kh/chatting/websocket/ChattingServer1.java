package com.kh.chatting.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.kh.chatting.model.vo.Message;

// 일반클래스를 웹소켓 서버로 등록하기 위한 방법
@ServerEndpoint(value="/chatting.do")
public class ChattingServer1 {
	
	// 내부에 클라이언트가 웹소켓 요청하는 내용을 처리하는 메서드들 등록하기
	
	
	private static Map<String, Session> clients = new HashMap<>();
	
	@OnOpen
	public void open(Session session, EndpointConfig config) {
		// 접속요청한 클라이언트와 접속이 연결되면 실행되는 메서드
		
		System.out.println("클라이언트 접속");
		System.out.println(session.getId());
		
		clients.put(session.getId(), session);
	}
	
	
	// 클라이언트에서 보내는 메세지를 처리하는 메서드
	@OnMessage
	public void message(Session session, String msg) { // jsonObjcet '{key:value, key:value}'
		
		// session : 메세지를 보낸 클라이언트의 session
		// msg	   : socket함수의 매개변수로 전달한 데이터
		
		// 데이터 확인
		System.out.println(msg);
		// {"sender":"admin", "receiver":"xx", "msg":"xxx"}
		
		// 클라이언트가 보낸 메세지 파싱
		Message m = new Gson().fromJson(msg, Message.class);

		// 클라이언트가 보낸 데이터를 세션에 저장 => 다른 클라이언트에게도 보이게 하기 위함
		session.getUserProperties().put("msg", m);
		
		// 클라이언트가 보낸 데이터를 다른 클라이언트들에게 전송하기
		// 1. 접속한 클라이언트 관리하기
		//   1) Collection클래스를 통해 관리하기
		//   2) session클래스에서 접속한 모든 session을 알 수 있는 메서드를 사용하여 관리하기
		//		session.getOpenSessions() => 현재 웹소켓에 접속중인 모든 session객체를 반환함
		
		
		// 2. 접속 session객체를 이용하여 데이터를 전송하기
		//		session.getBasicRemote() => 접속한 세션과 연결되는 출력스트림을 가져옴
		
		// 3.  getBasicRemote로 가져온 객체의 sendText() 함수 호출 => 메세지를 클라이언트에게 전송
		
		
		Set<String> keys = clients.keySet();
		
		for( String key : keys ) {
			Session ss = clients.get(key);
			
			// 사용자 정보조회(Message객체 내부에 sender 정보가 곧 접속한 사용자의 id값)
			
			if(!ss.isOpen() || ss.getUserProperties() == null) {
				continue;
			}
			
			Message clientData = (Message)ss.getUserProperties().get("msg");
			
			
			if(clientData != null) {
				String userId = clientData.getSender();
				
				if(userId.equals(m.getReceiver()) || userId.equals(m.getSender())) {
					// 현재 반복중인 사용자가 전달된 메세지의 수신자이거나, 발송자 본인인 경우
					
					try {
						ss.getBasicRemote().sendText(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		
		
	}
	
	
}
