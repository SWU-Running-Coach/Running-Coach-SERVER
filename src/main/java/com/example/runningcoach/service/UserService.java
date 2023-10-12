package com.example.runningcoach.service;

import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.exception.custom.UserConflictException;
import com.example.runningcoach.repository.UserRepository;
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
}
