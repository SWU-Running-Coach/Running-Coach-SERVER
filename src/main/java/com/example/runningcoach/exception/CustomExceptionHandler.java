package com.example.runningcoach.exception;

import com.example.runningcoach.exception.custom.UserConflictException;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {


	@ExceptionHandler
	public ResponseEntity handleUserConflictException(UserConflictException e) {
		return new ResponseEntity(
			BaseResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.CONFLICT_EMAIL),
			HttpStatus.BAD_REQUEST);
	}
}
