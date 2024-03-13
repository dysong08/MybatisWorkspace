package com.kh.board.model.vo;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class Board {

	
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private int boardWriter;
	private String userId;
	private int count;
	private Date createDate;
	private String status;
	
	private ArrayList<Reply> list;
	
}
