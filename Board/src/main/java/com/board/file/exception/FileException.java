package com.board.file.exception;

import com.board.exception.BaseException;
import com.board.exception.BaseExceptionType;

public class FileException extends BaseException {
private BaseExceptionType exceptionType;
public FileException(BaseExceptionType exceptionType) {
	// TODO Auto-generated constructor stub
	this.exceptionType = exceptionType;
}

public BaseExceptionType getExceptionType() {
	return exceptionType;
}

}
