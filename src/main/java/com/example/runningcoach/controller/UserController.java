package com.example.runningcoach.controller;

import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import com.example.runningcoach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	//TODO: swagger 등록
	@GetMapping("/join")
	public ResponseEntity signUp(@RequestBody SignupRequestDto signupRequestDto) {
		//TODO: exception 처리

		userService.SignupUser(signupRequestDto);

		return new ResponseEntity(BaseResponse.response(StatusCode.CREATED, ResponseMessage.SUCCESS),
			HttpStatus.CREATED);
	}
}
