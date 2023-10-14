package com.example.runningcoach.controller;

import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import com.example.runningcoach.service.UserService;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	//TODO: swagger 등록
	@PostMapping("/join")
	public ResponseEntity signUp(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
		//valid check
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(BaseResponse.response(StatusCode.BAD_REQUEST, Objects.requireNonNull(
				bindingResult.getFieldError()).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}
		userService.SignupUser(signupRequestDto);

		return new ResponseEntity(BaseResponse.response(StatusCode.CREATED, ResponseMessage.SUCCESS),
			HttpStatus.CREATED);
	}

	//TODO: jwt 토큰 반환
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult bindingResult) {
		//valid check
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(BaseResponse.response(StatusCode.BAD_REQUEST, Objects.requireNonNull(
				bindingResult.getFieldError()).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		userService.LoginUser(loginRequestDto);

		return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS),
			HttpStatus.OK);
	}
}
