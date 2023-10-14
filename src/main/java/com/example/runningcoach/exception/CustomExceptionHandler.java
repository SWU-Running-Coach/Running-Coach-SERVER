package com.example.runningcoach.exception;

import com.example.runningcoach.exception.custom.EmptyException;
import com.example.runningcoach.exception.custom.FailLoginException;
import com.example.runningcoach.exception.custom.LeaveUserException;
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

	//TODO: message 받아오는 것으로 변경
	@ExceptionHandler
	public ResponseEntity handleUserConflictException(UserConflictException e) {
		return new ResponseEntity(
			BaseResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.CONFLICT_EMAIL),
			HttpStatus.BAD_REQUEST);
	}

	//TODO: message 받아오는 것으로 변경
	@ExceptionHandler
	public ResponseEntity handleEmptyException(EmptyException e) {
		return new ResponseEntity(
			BaseResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.EMPTY_VALUE),
			HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity handleFailLoginException(FailLoginException e) {
		return new ResponseEntity(
			BaseResponse.response(StatusCode.BAD_REQUEST, e.getMessage()),
			HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity handleLeaveUserException(LeaveUserException e) {
		return new ResponseEntity(
			BaseResponse.response(StatusCode.FORBIDDEN, e.getMessage()),
			HttpStatus.FORBIDDEN);
	}
}
