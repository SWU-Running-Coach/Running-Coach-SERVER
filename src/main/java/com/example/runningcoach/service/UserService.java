package com.example.runningcoach.service;

import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.MypageResponseDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.exception.custom.FailLoginException;
import com.example.runningcoach.exception.custom.LeaveUserException;
import com.example.runningcoach.exception.custom.NoExistEmailException;
import com.example.runningcoach.exception.custom.UserConflictException;
import com.example.runningcoach.repository.UserRepository;
import com.example.runningcoach.response.ResponseMessage;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public void SignupUser(SignupRequestDto signupRequestDto) {

		//이미 가입된 이메일 오류
		if (userRepository.findByEmail(signupRequestDto.getEmail()).isPresent()) {
			throw new UserConflictException();
		}

		User user = User.builder()
				.email(signupRequestDto.getEmail())
				.password(passwordEncoder.encode(signupRequestDto.getPassword()))
				.nickname(signupRequestDto.getNickname()).build();

		userRepository.save(user);

	}

	public void LoginUser(LoginRequestDto loginRequestDto) {
		Optional<User> user = userRepository.findByEmail(loginRequestDto.getEmail());

		//이메일 오류
		if (user.isEmpty()) {
			throw new FailLoginException(ResponseMessage.FAIL_LOGIN);
		}

		//비밀번호 불일치
		if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword())) {
			throw new FailLoginException(ResponseMessage.FAIL_LOGIN);
		}

		//탈퇴한 회원 로그인 시도
		if (user.get().getStatus() == 0) {
			throw new LeaveUserException(ResponseMessage.LEAVE_USER);
		}
	}

	public MypageResponseDto myPage(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		//존재하지 않는 이메일
		if (user.isEmpty()) {
			throw new NoExistEmailException(ResponseMessage.NO_EXIST_EMAIL);
		}
		MypageResponseDto mypageResponseDto = new MypageResponseDto();

		mypageResponseDto.setEmail(user.get().getEmail());
		mypageResponseDto.setNickname(user.get().getNickname());
		mypageResponseDto.setProfile(user.get().getImage());

		return mypageResponseDto;
	}
}
