package com.kh.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

public interface BoardService {

	int selectListCount();

	ArrayList<Board> selectList(PageInfo pi);

	int countUpdate(int bno);

	Board listAllSelect(int bno);

	int selectSearchCount(HashMap<String, String> map);

	ArrayList<Board> selectSearchList(HashMap<String, String> map, PageInfo pi);


}
