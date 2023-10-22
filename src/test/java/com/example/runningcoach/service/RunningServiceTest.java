package com.example.runningcoach.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.example.runningcoach.dto.FrameDto;
import com.example.runningcoach.dto.RunningRequestDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.repository.RunningRepository;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class RunningServiceTest {

	@Autowired
	UserService userService;

	@Autowired
	RunningService runningService;

	@Autowired
	RunningRepository runningRepository;

	@Test
	@DisplayName("자세 분석 및 저장 테스트")
	public void runningServiceTest() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		String email = "runningservice@test.com";

		signupRequestDto.setEmail(email);
		signupRequestDto.setNickname("running");
		signupRequestDto.setPassword("TestPwd1");
		userService.SignupUser(signupRequestDto);

		//when
		RunningRequestDto runningRequestDto = new RunningRequestDto();

		runningRequestDto.setImage("test_img_url");
		runningRequestDto.setDateTime(LocalDateTime.now());
		runningRequestDto.setCadence(40);
		runningRequestDto.setLegAngle(155.2);
		runningRequestDto.setUppderBodyAngle(12.4);

		//then
		assertDoesNotThrow(() -> {
			runningService.runningAnalyze(runningRequestDto, email);
		});

	}

}
