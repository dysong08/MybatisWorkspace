package com.kh.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;
import com.kh.common.template.Template;

public class BoardServiceImpl implements BoardService{

	private BoardDao boardDao = new BoardDao();
	
	@Override
	public int selectListCount() {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int listCount = boardDao.selectListCount(sqlSession);
		
		sqlSession.close();
		
		return listCount;
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		ArrayList<Board> list = boardDao.selectList(sqlSession, pi);
		
		sqlSession.close();
		return list;
	}

	@Override
	public int countUpdate(int bno) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		int count = boardDao.countUpdate(sqlSession, bno);
		
		if(count > 0) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		
		return count;
	}

	@Override
	public Board listAllSelect(int bno) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		Board b = boardDao.listAllSelect(sqlSession, bno);
		
		sqlSession.close();
		
		return b;
	}

	@Override
	public int selectSearchCount(HashMap<String, String> map) {
		//SELECT문 호출
		SqlSession sqlSession = Template.getSqlSession();
		
		int searchCount = boardDao.selectSearchCount(sqlSession, map);
		
		sqlSession.close();
		
		return searchCount;
	}

	@Override
	public ArrayList<Board> selectSearchList(HashMap<String, String> map, PageInfo pi) {
		//SELECT문 호출
		SqlSession sqlSession = Template.getSqlSession();
		
		ArrayList<Board> list = boardDao.selectSearchList(sqlSession, map, pi);
		
		sqlSession.close();
		return list;
	}
	

	

}
