package com.board.domain.member.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class MemberException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BaseExceptionType exceptionType;
	
	public MemberException(BaseExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}
	
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return exceptionType;
	}

}
