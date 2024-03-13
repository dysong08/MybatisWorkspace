package com.kh.chatting.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder.Text;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.kh.chatting.model.vo.Message;

public class JsonDecoder implements Text<Message> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message decode(String msg) throws DecodeException {
		
		return new Gson().fromJson(msg, Message.class);
	}

	@Override
	public boolean willDecode(String msg) {
		
		return true;
	}
	
	
	
	
	
	
	
}
