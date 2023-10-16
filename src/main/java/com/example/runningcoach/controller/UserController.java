package com.example.runningcoach.controller;

import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.MypageResponseDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.dto.UpdateUserRequestDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	//TODO: param대신 jwt 토큰 설정
	@GetMapping("/{email}")
	public ResponseEntity myPage(@PathVariable String email) {
		MypageResponseDto mypageResponseDto = userService.myPage(email);

		return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS, mypageResponseDto),
			HttpStatus.OK);
	}

	//TODO: param 대신 jwt 토큰 설정
	@PatchMapping("/info/{email}")
	public ResponseEntity updateUser(@PathVariable String email, @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto,
		BindingResult bindingResult) {
		//valid check
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(BaseResponse.response(StatusCode.BAD_REQUEST, Objects.requireNonNull(
				bindingResult.getFieldError()).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
		}

		userService.updateUser(updateUserRequestDto, email);

		return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS),
			HttpStatus.OK);
	}
}
