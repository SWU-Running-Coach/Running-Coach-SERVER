package com.example.runningcoach.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.runningcoach.dto.RunningRequestDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class RunningControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@DisplayName("/running/{email} 성공 테스트")
	@Test
	public void successRunning() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		String email = "runningcontroller@test.com";

		signupRequestDto.setEmail(email);
		signupRequestDto.setNickname("controller1");
		signupRequestDto.setPassword("Controller23");

		String SignUpData = objectMapper.writeValueAsString(signupRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
				.content(SignUpData)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//when
		RunningRequestDto runningRequestDto = new RunningRequestDto();

		runningRequestDto.setImage("test_img_url");
		runningRequestDto.setDateTime(LocalDateTime.now());
		runningRequestDto.setCadence(40);
		runningRequestDto.setLegAngle(155.2);
		runningRequestDto.setUppderBodyAngle(12.4);

		String RunningData = objectMapper.writeValueAsString(runningRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/running/{email}", email)
				.content(RunningData)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(
			HttpStatus.CREATED.value());
	}
}
