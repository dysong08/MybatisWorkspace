package com.kh.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

//양방향 암호화 지원 클래스
// java.api에서 제공하고 있음
public class AESCryptor {
	
	private static SecretKey key; // 암호화를 위한 키
	private String path;	// 암호화키가 저장된 경로
	
	public AESCryptor() {
		// 인스턴스화 될 때 기본설정 추가
		// 1. key파일이 있다면 key파일에서 SecretKey객체를 불러오고 
		//	  key가 없다면 SercretKey객체를 생성하여 파일로 저장
		
		this.path = AESCryptor.class.getResource("/").getPath();
		// C:\MybatisWorkspace\MyBatis_Project\WebContent\WEB-INF\classes
		this.path = this.path.substring(0, this.path.indexOf("classes"));
		// C:\MybatisWorkspace\MyBatis_Project\WebContent\WEB-INF\
		
		File f = new File(this.path + "/mkm.mk");
		// key를 저장하고 있는 파일 이름 mkm.mk -> SecretKey객체를 저장시킬 예정
		
		if(f.exists()) {
			// key를 저장하는 파일이 있다면
			try(ObjectInputStream ois = new ObjectInputStream (new FileInputStream(f)) ){
				
				this.key = (SecretKey)ois.readObject();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		} else {
			// 파일이 없다면 파일 생성 후 내부에 SrecretKey객체 추가
			if(key == null) {
				// key값 생성해주는 메서드
				getGenerator();
			}
		}
	}

	
	private void getGenerator() {
		// SrecretKey 객체 생성하는 객체
		SecureRandom ser = new SecureRandom();
		
		// key를 생성해주는 클래스
		KeyGenerator keygen = null;
		
		try {
			// 1. 적용할 알고리즘 AES => AES알고리즘은 key가 1개 필요함
			keygen = KeyGenerator.getInstance("AES");
			
			keygen.init(128, ser);
			
			this.key = keygen.generateKey();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// 생성된 key객체를 mkm.mk파일에 저장하기
		File f = new File(this.path + "/mkm.mk");
		try(ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(f))){
			oos.writeObject(this.key);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static String encrypt(String str) {
		
		String resultValue = "";
		
		try {
			// key을 가지고 암호화처리하는 클래스(Cipher)
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, AESCryptor.key);
			
			// 매개변수로 전달받은 문자열 암호화처리하기
			byte[] encrpt = str.getBytes( Charset.forName("UTF-8"));
			byte[] result = cipher.doFinal(encrpt);
			
			resultValue = Base64.getEncoder().encodeToString(result);
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		
		
		return resultValue;
	}
	
	
	// 생성된 key를 복구화 하는 메서드
	// 생성의 역순으로 복구해야 함
	public static String decrypt(String encryptedStr) {
		
		String decryptedValue = "";
		
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, AESCryptor.key);
			// cipher 초기화함
			
			byte[] decodeStr = Base64.getDecoder().decode(encryptedStr.getBytes( Charset.forName("UTF-8")));
			byte[] originStr = cipher.doFinal(decodeStr);
			
			decryptedValue = new String(originStr);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		
		return decryptedValue;
	}
	
	
	
	
	
}
