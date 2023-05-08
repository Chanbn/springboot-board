package com.board.domain.comment.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class CommentException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BaseExceptionType exceptionType;

	public CommentException(BaseExceptionType exceptionType)
	{
		this.exceptionType = exceptionType;
	}
	@Override
	public BaseExceptionType getExceptionType() {
		// TODO Auto-generated method stub
		return exceptionType;
	}

}
