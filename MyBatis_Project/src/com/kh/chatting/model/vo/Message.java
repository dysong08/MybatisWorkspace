package com.kh.chatting.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class Message {

	private String sender;
	private String receiver;
	private String msg;
	
	
}
