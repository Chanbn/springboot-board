package com.board.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.board.domain.member.exception.MemberException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

//	@ExceptionHandler(value = {BaseException.class})
//	public ResponseEntity handleBaseException(BaseException exception) {
//		log.error("BaseException errorMessage(): {}",exception.getExceptionType().getErrorMessage());
//        log.error("BaseException errorCode(): {}",exception.getExceptionType().getErrorCode());
//		
//		return new ResponseEntity(exception.getExceptionType().getErrorMessage(),exception.getExceptionType().getHttpStatus());
//	}
	
	
}
