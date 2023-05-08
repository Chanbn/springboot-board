package com.board.file.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum FileExceptionType implements BaseExceptionType{
    FILE_CAN_NOT_SAVE(10000, HttpStatus.BAD_REQUEST, "파일 저장에 실패했습니다."),
    FILE_CAN_NOT_DELETE(10001, HttpStatus.BAD_REQUEST, "파일 삭제에 실패했습니다."),
	File_NOT_EXIST(10002,HttpStatus.NOT_FOUND,"파일이 존재하지 않습니다.");
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;
	
	private FileExceptionType(int errorCode, HttpStatus httpStatus,String errorMessage) {
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
	@Override
	public int getErrorCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HttpStatus getHttpStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
