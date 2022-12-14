package com.movieyo.board.dto;

import java.sql.Date;
import java.util.Objects;

public class BoardDto {
	private int boardNo;
	private int userNo;
	private String boardHead;
	private String boardTitle;
	private String boardContent;
	private Date boardCredate;
	private Date boardModdate;
	private int boardCount;
	private String userName;
	private int next;
	private int last;
	private String nexttitle;
	private String lasttitle;

	public BoardDto() {
		super();
	}

	public BoardDto(int boardNo, int userNo, String boardHead, String boardTitle, String boardContent,
			Date boardCredate, Date boardModdate, int boardCount, String userName, int next, int last, String nexttitle,
			String lasttitle) {
		super();
		this.boardNo = boardNo;
		this.userNo = userNo;
		this.boardHead = boardHead;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardCredate = boardCredate;
		this.boardModdate = boardModdate;
		this.boardCount = boardCount;
		this.userName = userName;
		this.next = next;
		this.last = last;
		this.nexttitle = nexttitle;
		this.lasttitle = lasttitle;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getBoardHead() {
		return boardHead;
	}

	public void setBoardHead(String boardHead) {
		this.boardHead = boardHead;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public Date getBoardCredate() {
		return boardCredate;
	}

	public void setBoardCredate(Date boardCredate) {
		this.boardCredate = boardCredate;
	}

	public Date getBoardModdate() {
		return boardModdate;
	}

	public void setBoardModdate(Date boardModdate) {
		this.boardModdate = boardModdate;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public String getNexttitle() {
		return nexttitle;
	}

	public void setNexttitle(String nexttitle) {
		this.nexttitle = nexttitle;
	}

	public String getLasttitle() {
		return lasttitle;
	}

	public void setLasttitle(String lasttitle) {
		this.lasttitle = lasttitle;
	}

	@Override
	public String toString() {
		return "BoardDto [boardNo=" + boardNo + ", userNo=" + userNo + ", boardHead=" + boardHead + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardCredate=" + boardCredate + ", boardModdate="
				+ boardModdate + ", boardCount=" + boardCount + ", userName=" + userName + ", next=" + next + ", last="
				+ last + ", nexttitle=" + nexttitle + ", lasttitle=" + lasttitle + "]";
	}

}
