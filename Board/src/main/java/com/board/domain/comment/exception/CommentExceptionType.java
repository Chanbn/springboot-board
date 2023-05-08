package com.board.domain.comment.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum CommentExceptionType implements BaseExceptionType {
	   WRONG_COMMENT(801,HttpStatus.FORBIDDEN,"인증되지 않은 사용자입니다.");

	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;
	
	private CommentExceptionType(int errorCode, HttpStatus httpsStatus, String errorMessage)
	{
		this.errorCode = errorCode;
		this.httpStatus = httpsStatus;
		this.errorMessage = errorMessage;
	}
	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return this.errorCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		// TODO Auto-generated method stub
		return this.httpStatus;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return this.errorMessage;
	}

}
