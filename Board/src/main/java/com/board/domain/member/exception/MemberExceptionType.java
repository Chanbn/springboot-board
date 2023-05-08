package com.board.domain.member.exception;

import org.springframework.http.HttpStatus;

import com.board.exception.BaseExceptionType;

public enum MemberExceptionType implements BaseExceptionType{
	ALREADY_EXIST_USERNAME(600, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
	ALREADY_EXIST_NICKNAME(600, HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
	ALREADY_EXIST_EMAIL(600, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    WRONG_PASSWORD(601,HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_MEMBER(602, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    WRONG_USER(603,HttpStatus.FORBIDDEN,"인증되지 않은 사용자입니다."),
    WRONG_ACTIV(604,HttpStatus.FORBIDDEN,"삭제 권한이 없습니다."),
    ;
	
	private int errorCode;
	private HttpStatus httpStatus;
	private String errorMessage;

	private MemberExceptionType(int errorCode, HttpStatus httpsStatus, String errorMessage) {
		// TODO Auto-generated constructor stub
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
