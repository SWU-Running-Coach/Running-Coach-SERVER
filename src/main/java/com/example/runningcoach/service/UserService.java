package com.example.runningcoach.service;

import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public void SignupUser(SignupRequestDto signupRequestDto) {

		//TODO: 비밀번호 암호화
		User user = User.builder()
				.email(signupRequestDto.getEmail())
				.password(signupRequestDto.getPassword())
				.nickname(signupRequestDto.getNickname()).build();

		userRepository.save(user);

		//TODO: 이미 가입된 이메일 오류

	}
}
