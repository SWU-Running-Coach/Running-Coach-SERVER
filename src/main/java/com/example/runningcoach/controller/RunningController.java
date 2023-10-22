package com.example.runningcoach.controller;

import com.example.runningcoach.dto.RunningRequestDto;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import com.example.runningcoach.service.RunningService;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class RunningController {

	@Autowired
	RunningService runningService;

	//TODO: param대신 jwt 토큰 설정
	@PostMapping ("/running/{email}")
	public ResponseEntity running(@RequestBody RunningRequestDto runningRequestDto, @PathVariable String email) {

		runningService.runningAnalyze(runningRequestDto, email);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(URI.create("/feedback?datetime="+runningRequestDto.getDateTime()+"&email="+email));

		return new ResponseEntity(
			BaseResponse.response(StatusCode.SEE_OTHER, ResponseMessage.SUCCESS),
			httpHeaders,
			HttpStatus.SEE_OTHER);
	}

	//TODO: param대신 jwt 토큰 설정
	@GetMapping("/feedback")
	public ResponseEntity feedback (@RequestParam (value = "datetime", required = true) LocalDateTime dateTime,
	@RequestParam (value = "email", required = true) String email) {

		//TODO: 구현 필요
		return new ResponseEntity(
			BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS),
			HttpStatus.OK
		);
	}
}
