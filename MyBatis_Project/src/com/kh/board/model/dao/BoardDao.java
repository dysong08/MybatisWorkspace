package com.kh.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

public class BoardDao {

	public int selectListCount(SqlSession sqlSession) {
		
		return sqlSession.selectOne("boardMapper.selectListCount");
	}

	public ArrayList<Board> selectList(SqlSession sqlSession, PageInfo pi) {
		
		/*
		* 마이바티스에서는 페이징 처리를 위해 RowBounds 클래스를 제공한다
		
		* offset : 몇 개의 게시글을 건너뛰고 조회할 것인지에 대한 값
		
		boardimit가 5일 경우			 offset 		limit
		currentPage : 1 => 1~5			0			  5
		currentPage : 2 => 6~10			5		      5
		currentPage : 3 => 11~15	    10			  5
		*/
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage() -1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		/*
			RowBounds 객체를 넘겨야 할 경우 selectList메서드의 오버로딩된 메서드 중
			매개변수가 3개인 메서드를 사용해야만 한다.
			
			단, 이때 두번째 매개변수(쿼리문을 꾸밀 객체)가 필요없다면 null값을 제시하면 된다.
		*/
		return (ArrayList)sqlSession.selectList("boardMapper.selectList", null, rowBounds);
	}

	
	
	public int countUpdate(SqlSession sqlSession, int bno) {
		
		return sqlSession.update("boardMapper.countUpdate", bno);
	}

	public Board listAllSelect(SqlSession sqlSession, int bno) {
		
		return sqlSession.selectOne("boardMapper.listAllSelect", bno);
	}

	public int selectSearchCount(SqlSession sqlSession, HashMap<String, String> map) {
		
		return sqlSession.selectOne("boardMapper.selectSearchCount", map);
	}

	public ArrayList<Board> selectSearchList(SqlSession sqlSession, HashMap<String, String> map, PageInfo pi) {
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage() -1) *limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectSearchList", map, rowBounds);
	}


}
