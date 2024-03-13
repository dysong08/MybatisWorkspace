package com.kh.common.template;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Template {
	
	/*
		기존 JDBC
		
		1) getConnection함수 -> driver.propertise파일을 읽어들여서
			해당 DB와 접속된 Connection객체를 생성해서 반환
		
		2) close함수 -> 전달받은 JDBC객체를 반납시키는 함수
		
		3) 트랜잭션 함수들(commit, rollback)
	*/
	
	
	// MyBatis
	public static SqlSession getSqlSession() {
		
		// MyBatis 설정정보 읽어들이기
		// resources/mybatis-comfig.xml
		
		
		
		// 설정정보 안에 저장된 DB와 접속하여 SqlSession객체 생성 후 반환할 예정
		SqlSession sqlSession = null;
		
		// SqlSession객체를 생성하기 위해서는 SqlSessionFactory객체가 필요함
		// SqlSessionFactory객체를 생성하기 위해서는 SqlSessionFactoryBuilder객체가 필요함
		
		String resource = "/mybatis-comfig.xml";
		// /는 최상의폴더(== classes)
		
		
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			// 1단계) new SqlSessionFactoryBuilder() : new SqlSessionFactoryBuilder객체
			// 2단계) .build(stream) : 통로로부터 mybatis-comfig.xml파일을 읽어들임
			// 3단계) .openSession(false) : sqlSession객체 생성 및 트랜잭션 처리시 자동 커밋지정 여부
			//		=> false설정시 자동커밋을 하지 않음(기본값은 false)
				   
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
			sqlSession = ssf.openSession(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sqlSession;
		
	}

	
}
