package com.kh.chatting.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder.Text;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.kh.chatting.model.vo.Message;

public class JsonEncoder implements Text<Message>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(Message msg) throws EncodeException {
		
		
		return new Gson().toJson(msg);
	}
	
	
	
}
