package com.example.runningcoach.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.MypageResponseDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.repository.UserRepository;
import com.example.runningcoach.response.ResponseMessage;
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

	@Test
	@DisplayName("로그인 성공 테스트")
	public void successLogin() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setEmail("login@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("login");

		userService.SignupUser(signupRequestDto);

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("login@test.com");
		loginRequestDto.setPassword("Pwdasdf1");

		//when
		//then
		assertDoesNotThrow(() -> {
			userService.LoginUser(loginRequestDto);
		});
	}

	@Test
	@DisplayName("로그인 실패 테스트 (비밀번호 불일치)")
	public void failPwdLogin() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setEmail("fail@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("login");

		userService.SignupUser(signupRequestDto);

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("fail@test.com");
		loginRequestDto.setPassword("FailPwd2");

		//when
		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			userService.LoginUser(loginRequestDto);
		});

		//then
		assertEquals(throwable.getMessage(), ResponseMessage.FAIL_LOGIN);
	}

	@Test
	@DisplayName("로그인 실패 테스트 (존재하지 않는 이메일)")
	public void failEmailLogin() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setEmail("noemail@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("login");

		userService.SignupUser(signupRequestDto);

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("no@test.com");
		loginRequestDto.setPassword("Pwdasdf1");

		//when
		Throwable throwable = assertThrows(RuntimeException.class, () -> {
				userService.LoginUser(loginRequestDto);
			});
		//then
		assertEquals(throwable.getMessage(), ResponseMessage.FAIL_LOGIN);
	}

	@Test
	@DisplayName("탈퇴한 계정 로그인")
	public void noLogin() {
		//given
		User user = User.builder()
			.email("leave@test.com")
			.password(passwordEncoder.encode("Pwdasdf1"))
			.nickname("leave")
			.status(0).build();

		userRepository.save(user);

		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setEmail("leave@test.com");
		loginRequestDto.setPassword("Pwdasdf1");

		//when
		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			userService.LoginUser(loginRequestDto);
		});
		//then
		assertEquals(throwable.getMessage(), ResponseMessage.LEAVE_USER);
	}

	@Test
	@DisplayName("마이페이지 불러오기")
	public void myPage() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setEmail("mypage@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("mypage");

		userService.SignupUser(signupRequestDto);

		//when
		//then
		assertDoesNotThrow(() -> {
			MypageResponseDto mypageResponseDto = userService.myPage("mypage@test.com");
			assertEquals(mypageResponseDto.getNickname(), signupRequestDto.getNickname());
		});
	}

	@Test
	@DisplayName("마이페이지 존재하지 않는 이메일")
	public void NoEmailmyPage() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();
		signupRequestDto.setEmail("mypage@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("mypage");

		userService.SignupUser(signupRequestDto);

		//when
		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			MypageResponseDto mypageResponseDto = userService.myPage("my@test.com");
		});

		//then
		assertEquals(throwable.getMessage(), ResponseMessage.NO_EXIST_EMAIL);
	}

	@Test
	@DisplayName("회원 정보 수정 pw, nickname, profile 성공")
	public void updateUserAll() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("update@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("update");

		userService.SignupUser(signupRequestDto);

		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setPassword("newPassword");
		updateUserRequestDto.setNickname("newUpdate1");
		updateUserRequestDto.setProfile("new_profile_url");

		//when
		String email = "update@test.com";

		//then
		assertDoesNotThrow(() -> {
			userService.updateUser(updateUserRequestDto, email);
		});
	}

	@Test
	@DisplayName("회원 정보 수정 profile만 수정 성공")
	public void updateUserProfile() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("update_1@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("update1");

		userService.SignupUser(signupRequestDto);

		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setProfile("new_profile_url1");

		//when
		String email = "update_1@test.com";

		//then
		assertDoesNotThrow(() -> {
			userService.updateUser(updateUserRequestDto, email);
		});
	}

	@Test
	@DisplayName("회원 정보 수정 pw, nickname 수정 성공")
	public void updateUserTwo() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("update2@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("update");

		userService.SignupUser(signupRequestDto);

		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setPassword("newPassword2");
		updateUserRequestDto.setNickname("newUpdate2");


		//when
		String email = "update2@test.com";

		//then
		assertDoesNotThrow(() -> {
			userService.updateUser(updateUserRequestDto, email);
		});
	}

	@Test
	@DisplayName("회원 정보 수정 중 비밀번호 형식 오류")
	public void updatePw() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("updatepw@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("update");

		userService.SignupUser(signupRequestDto);

		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setPassword("newPassword");
		updateUserRequestDto.setNickname("newUpdate");
		updateUserRequestDto.setProfile("new_profile_url");

		//when
		String email = "updatepw@test.com";

		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			userService.updateUser(updateUserRequestDto, email);
		});

		//then
		assertEquals(throwable.getMessage(), ResponseMessage.INVALID_PASSWORD);
	}

	@Test
	@DisplayName("회원 정보 수정 중 이전과 동일한 비밀번호로 수정")
	public void updateSamePw() {
		//given
		SignupRequestDto signupRequestDto = new SignupRequestDto();

		signupRequestDto.setEmail("update@test.com");
		signupRequestDto.setPassword("Pwdasdf1");
		signupRequestDto.setNickname("update");

		userService.SignupUser(signupRequestDto);

		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setPassword("Pwdasdf1");
		updateUserRequestDto.setNickname("newUpdate");
		updateUserRequestDto.setProfile("new_profile_url");

		//when
		String email = "update@test.com";

		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			userService.updateUser(updateUserRequestDto, email);
		});

		//then
		assertEquals(throwable.getMessage(), ResponseMessage.SAME_PASSWORD);
	}

	@Test
	@DisplayName("회원 정보 수정 중 존재하지 않는 이메일")
	public void updateNoEmail() {
		UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestDto();

		updateUserRequestDto.setPassword("Pwdasdf1");
		updateUserRequestDto.setNickname("newUpdate");
		updateUserRequestDto.setProfile("new_profile_url");

		//when
		String email = "updatetest@test.com";

		Throwable throwable = assertThrows(RuntimeException.class, () -> {
			userService.updateUser(updateUserRequestDto, email);
		});

		//then
		assertEquals(throwable.getMessage(), ResponseMessage.NO_EXIST_EMAIL);
	}
}
