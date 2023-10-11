package com.example.runningcoach.controller;

import com.example.runningcoach.dto.SignupRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@DisplayName("/users/join 성공 테스트")
	@Test
	public void successSignUp() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("controller@test.com");
		signupRequestDto.setNickname("controller1");
		signupRequestDto.setPassword("Controller!23");

		String data = objectMapper.writeValueAsString(signupRequestDto);

		//when
		ResultActions result = mockMvc
							.perform(MockMvcRequestBuilders.get("/users/join")
															.content(data)
															.contentType(MediaType.APPLICATION_JSON)
															.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}
}
