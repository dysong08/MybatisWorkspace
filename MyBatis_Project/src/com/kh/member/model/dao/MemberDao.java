package com.kh.member.model.dao;

import org.apache.ibatis.session.SqlSession;

import com.kh.member.model.vo.Member;

public class MemberDao {

	public Member loginMember(SqlSession sqlSession, Member m) {
		
		// 필요한 변수 셋팅
		// Member m - null;
		// PreparedStatement pstmt = null;
		// ResultSet rset = null;
		// String sql = "SELECT * FROM MEMBER ..";
		// pstmt = conn.preparedStatement(sql);
		// 예외처리, 위치홀더 변환, 조회된 결과값(rset)을 VO클래스(Member)로 변환
		// 자원반납 등등..
		
		
		return sqlSession.selectOne("memberMapper.loginMember", m);
		// selectOne : 조회결과가 없다면 null값을 반환
		// sqlSession.sql문 종류에 맞는 메서드("매퍼파일의namespace.해당sql문의 id값")
		
	}

	public int insertMember(SqlSession sqlSession, Member m) {
		
		
		return sqlSession.insert("memberMapper.insertMember", m);
	}

}
