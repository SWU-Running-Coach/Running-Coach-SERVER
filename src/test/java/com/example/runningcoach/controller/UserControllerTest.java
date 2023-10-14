package com.example.runningcoach.controller;

import com.example.runningcoach.dto.LoginRequestDto;
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
		signupRequestDto.setPassword("Controller23");

		String data = objectMapper.writeValueAsString(signupRequestDto);

		//when
		ResultActions result = mockMvc
							.perform(MockMvcRequestBuilders.post("/users/join")
															.content(data)
															.contentType(MediaType.APPLICATION_JSON)
															.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@DisplayName("/users/join 이메일 중복 테스트")
	@Test
	public void duplicateSignUp() throws Exception {
		//given
		SignupRequestDto signupRequestDto1 = new SignupRequestDto();

		signupRequestDto1.setEmail("duplicate@test.com");
		signupRequestDto1.setNickname("controller1");
		signupRequestDto1.setPassword("Controller23");

		String data1 = objectMapper.writeValueAsString(signupRequestDto1);

		SignupRequestDto signupRequestDto2 = new SignupRequestDto();

		signupRequestDto2.setEmail("duplicate@test.com");
		signupRequestDto2.setNickname("controller1");
		signupRequestDto2.setPassword("Controller23");

		String data2 = objectMapper.writeValueAsString(signupRequestDto2);
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
				.content(data1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/join")
				.content(data2)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/join 빈칸 테스트")
	@Test
	public void EmptySignUp() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("empty@test.com");
		signupRequestDto.setNickname("controller");

		String data = objectMapper.writeValueAsString(signupRequestDto);

		//when
		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/join")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/join 비밀번호 형식 테스트")
	@Test
	public void PwdSignUp() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("pwd@test.com");
		signupRequestDto.setNickname("controller");
		signupRequestDto.setPassword("pw");

		String data = objectMapper.writeValueAsString(signupRequestDto);

		//when
		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/join")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/join 이메일 형식 테스트")
	@Test
	public void EmailSignUp() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("email");
		signupRequestDto.setNickname("controller");
		signupRequestDto.setPassword("Controller12");

		String data = objectMapper.writeValueAsString(signupRequestDto);

		//when
		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/join")
				.content(data)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/login 성공 테스트")
	@Test
	public void Login() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("login1@test.com");
		signupRequestDto.setNickname("login");
		signupRequestDto.setPassword("Pwdsqwd12");

		LoginRequestDto loginRequestDto = new LoginRequestDto();

		loginRequestDto.setEmail("login1@test.com");
		loginRequestDto.setPassword("Pwdsqwd12");

		String signUp = objectMapper.writeValueAsString(signupRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
				.content(signUp)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//when
		String login = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/login")
				.content(login)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@DisplayName("/users/login 존재하지 않는 이메일 테스트")
	@Test
	public void EmailLogin() throws Exception {
		//given
		LoginRequestDto loginRequestDto = new LoginRequestDto();

		loginRequestDto.setEmail("loginemail@test.com");
		loginRequestDto.setPassword("Pwdsqwd12");

		//when
		String login = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/login")
				.content(login)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/login 비밀번호 불일치 테스트")
	@Test
	public void PwdLogin() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("loginpw@test.com");
		signupRequestDto.setNickname("login");
		signupRequestDto.setPassword("Pwdsqwd12");

		LoginRequestDto loginRequestDto = new LoginRequestDto();

		loginRequestDto.setEmail("loginpw@test.com");
		loginRequestDto.setPassword("Pwdsqwd1");

		String signUp = objectMapper.writeValueAsString(signupRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
			.content(signUp)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		//when
		String login = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/login")
				.content(login)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/login 이메일 빈칸 테스트")
	@Test
	public void EmailNullLogin()throws Exception {
		//given
		LoginRequestDto loginRequestDto = new LoginRequestDto();

		loginRequestDto.setPassword("Pwdasdf1234");

		//when
		String login = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/login")
				.content(login)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users/login 비밀번호 빈칸 테스트")
	@Test
	public void PwNullLogin()throws Exception {
		//given
		LoginRequestDto loginRequestDto = new LoginRequestDto();

		loginRequestDto.setEmail("login1@test.com");

		//when
		String login = objectMapper.writeValueAsString(loginRequestDto);

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.post("/users/login")
				.content(login)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@DisplayName("/users 성공 테스트")
	@Test
	public void myPage() throws Exception {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("mypagecc@test.com");
		signupRequestDto.setNickname("mypage");
		signupRequestDto.setPassword("Pwdsqwd12");

		String signUp = objectMapper.writeValueAsString(signupRequestDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/join")
			.content(signUp)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));

		//when
		String email = "mypagecc@test.com";

		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.get("/users")
				.param(email)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@DisplayName("/users 존재하지 않는 이메일 테스트")
	@Test
	public void noEmailMyPage() throws Exception {
		//given
		String email = "mypageemail@test.com";

		//when
		ResultActions result = mockMvc
			.perform(MockMvcRequestBuilders.get("/users")
				.param(email)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		//then
		assertThat(result.andReturn().getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
}
