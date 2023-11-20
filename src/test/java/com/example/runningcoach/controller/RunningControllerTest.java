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
			HttpStatus.SEE_OTHER.value());
	}

	@DisplayName("/feedback?datetime=YYYY-MM-DDTHH:MM:SS&email=email@emai.com 성공 테스트")
	@Test
	public void successFeedback() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		String email = "feedbackcontroller@test.com";

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

		LocalDateTime localDateTime = LocalDateTime.now();
		runningRequestDto.setImage("test_img_url");
		runningRequestDto.setDateTime(localDateTime);
		runningRequestDto.setCadence(40);
		runningRequestDto.setLegAngle(155.2);
		runningRequestDto.setUppderBodyAngle(12.4);

		String RunningData = objectMapper.writeValueAsString(runningRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/running/{email}", email)
				.content(RunningData)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));


		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/feedback")
			.param("datetime", String.valueOf(localDateTime))
			.param("email", email)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(
			HttpStatus.OK.value());
	}

	@DisplayName("/calender?y=2023&m=11&email=email@emai.com 성공 테스트")
	@Test
	public void successFeedbackByMonth() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		String email = "feedbackbyEmailr@test.com";

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

		LocalDateTime localDateTime = LocalDateTime.now();
		runningRequestDto.setImage("test_img_url");
		runningRequestDto.setDateTime(localDateTime);
		runningRequestDto.setCadence(40);
		runningRequestDto.setLegAngle(155.2);
		runningRequestDto.setUppderBodyAngle(12.4);

		String RunningData = objectMapper.writeValueAsString(runningRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/running/{email}", email)
			.content(RunningData)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		RunningRequestDto runningRequestDto2 = new RunningRequestDto();

		LocalDateTime localDateTime2 = LocalDateTime.now();
		runningRequestDto.setImage("test_img_url2");
		runningRequestDto.setDateTime(localDateTime2);
		runningRequestDto.setCadence(41);
		runningRequestDto.setLegAngle(153.2);
		runningRequestDto.setUppderBodyAngle(11.4);

		String RunningData2 = objectMapper.writeValueAsString(runningRequestDto2);

		mockMvc.perform(MockMvcRequestBuilders.post("/running/{email}", email)
			.content(RunningData2)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));


		//then
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/calender")
			.param("y", String.valueOf(localDateTime.getYear()))
			.param("m", String.valueOf(localDateTime.getMonthValue()))
			.param("email", email)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(
			HttpStatus.OK.value());
	}

	@DisplayName("/calender?y=2023&m=11&d=6&email=email@emai.com 성공 테스트")
	@Test
	public void successFeedbackByMonthDay() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		String email = "feedbackbyEmailDay@test.com";

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

		LocalDateTime localDateTime = LocalDateTime.now();
		runningRequestDto.setImage("test_img_url");
		runningRequestDto.setDateTime(localDateTime);
		runningRequestDto.setCadence(40);
		runningRequestDto.setLegAngle(155.2);
		runningRequestDto.setUppderBodyAngle(12.4);

		String RunningData = objectMapper.writeValueAsString(runningRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/running/{email}", email)
			.content(RunningData)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		RunningRequestDto runningRequestDto2 = new RunningRequestDto();

		LocalDateTime localDateTime2 = LocalDateTime.now();
		runningRequestDto.setImage("test_img_url2");
		runningRequestDto.setDateTime(localDateTime2);
		runningRequestDto.setCadence(41);
		runningRequestDto.setLegAngle(153.2);
		runningRequestDto.setUppderBodyAngle(11.4);

		String RunningData2 = objectMapper.writeValueAsString(runningRequestDto2);

		mockMvc.perform(MockMvcRequestBuilders.post("/running/{email}", email)
			.content(RunningData2)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));


		//then
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/calender/feedback")
			.param("y", String.valueOf(localDateTime.getYear()))
			.param("m", String.valueOf(localDateTime.getMonthValue()))
			.param("d", String.valueOf(localDateTime.getDayOfMonth()))
			.param("email", email)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(
			HttpStatus.OK.value());
	}
}
