package com.kh.common.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class PasswordEncryptWrapper extends HttpServletRequestWrapper{

	public PasswordEncryptWrapper(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public String getParameter(String name) {
		
		String value = "";
		
		// 매개변수로 전달받은 name변수의 값이 userPwd일 경우 암호화하기
		if(name.equals("userPwd")) {
			System.out.println("암호화전 pwd : " + super.getParameter(name));
			
			// 암호화작업
			value = getSHA512(super.getParameter(name));
			
			System.out.println("암호화후 pwd : " + value);
		}else {
			// 전달받은 매개변수가 비밀번호가 아닐 경우 원래 가지고 있던 기존값 그대로 반환
			value = super.getParameter(name);
		}
		return value;
	}
	
	
	private String getSHA512(String val) {
		
		// 암호화 처리객체 선언
		MessageDigest md = null;
		
		try {
			// 사용할 암호화 알고리즘을 선택해서 객체 생성
			md = MessageDigest.getInstance("SHA-512");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 암호화시 bit연산을 한다.
		md.update( val.getBytes(Charset.forName("UTF-8")) );
		// 문자열을 byte로 변환해주는 함수
		
		return Base64.getEncoder().encodeToString(md.digest());
		// 비트연산한 결과값 (byte[])을 String배열로 변환
		
	}
	
	
	
}
