package com.example.runningcoach.service;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입 테스트")
	public void signUpTest() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("test@test.com");
		signupRequestDto.setNickname("testNickname");
		signupRequestDto.setPassword("TestPwd1");

		//when
		userService.SignupUser(signupRequestDto);

		//then
		User result = userRepository.findByEmail(signupRequestDto.getEmail()).get();
		assertThat(result.getEmail()).isEqualTo(signupRequestDto.getEmail());
		assertThat(result.getNickname()).isEqualTo(signupRequestDto.getNickname());
	}

	@Test
	@DisplayName("비밀번호 암호화 테스트")
	public void passwordTest() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("pwd@test.com");
		signupRequestDto.setNickname("testNickname");
		signupRequestDto.setPassword("TestPwd1");

		//when
		userService.SignupUser(signupRequestDto);

		//then
		User result = userRepository.findByEmail(signupRequestDto.getEmail()).get();
		assertThat(result.getEmail()).isEqualTo(signupRequestDto.getEmail());
		assertThat(passwordEncoder.matches(signupRequestDto.getPassword(), result.getPassword())).isTrue();
	}
}
