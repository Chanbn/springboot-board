package com.board.domain.post.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum PostExceptionType implements BaseExceptionType{
	WRONG_POST(701,HttpStatus.NOT_FOUND, "존재하지 않는 게시물입니다.");
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;

	PostExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		// TODO Auto-generated method stub
		return httpStatus;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

}
