package com.kh.member.model.service;

import org.apache.ibatis.session.SqlSession;

import com.kh.common.template.Template;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberServiceImpl implements MemberService{

	
	
	private MemberDao memberDao = new MemberDao();
	
	@Override
	public Member loginMember(Member m) {
		
		// Connection conn = getConnection();
		SqlSession sqlSession = Template.getSqlSession();
		
		Member loginUser = memberDao.loginMember(sqlSession, m);
		
		// close(conn);
		sqlSession.close();
		
		return loginUser;
	}
	

	public int insertMember(Member m) {
		
		SqlSession sqlSession = Template.getSqlSession(); 
		int result = memberDao.insertMember(sqlSession, m);
		
		if(result > 0) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		return result;
	}

	
	
	
	
	
	
	
}
