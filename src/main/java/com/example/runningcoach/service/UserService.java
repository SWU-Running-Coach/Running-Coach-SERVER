package com.example.runningcoach.service;

import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.MypageResponseDto;
import com.example.runningcoach.dto.SignupRequestDto;
import com.example.runningcoach.dto.UpdateUserRequestDto;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.exception.custom.FailLoginException;
import com.example.runningcoach.exception.custom.LeaveUserException;
import com.example.runningcoach.exception.custom.NoExistEmailException;
import com.example.runningcoach.exception.custom.SamePasswordException;
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

	public void updateUser(UpdateUserRequestDto updateUserRequestDto, String email) {
		Optional<User> user = userRepository.findByEmail(email);

		//존재하지 않는 이메일
		if (user.isEmpty()) {
			throw new NoExistEmailException(ResponseMessage.NO_EXIST_EMAIL);
		}
		//동일한 비밀번호
		if (updateUserRequestDto.getPassword() != null
			&& (passwordEncoder.matches(updateUserRequestDto.getPassword(),
			user.get().getPassword()))) {
			throw new SamePasswordException(ResponseMessage.SAME_PASSWORD);
		}

		String new_password = user.get().getPassword();
		String new_nickname = user.get().getNickname();
		String new_profile = user.get().getImage();

		if (updateUserRequestDto.getPassword() != null)
			new_password = passwordEncoder.encode(updateUserRequestDto.getPassword());

		if (updateUserRequestDto.getNickname() != null)
			new_nickname = updateUserRequestDto.getNickname();

		if (updateUserRequestDto.getProfile() != null)
			new_profile = updateUserRequestDto.getProfile();

		User new_user = User.builder()
			.userId(user.get().getUserId())
			.email(user.get().getEmail())
			.password(new_password)
			.nickname(new_nickname)
			.image(new_profile)
			.status(user.get().getStatus())
			.createdDate(user.get().getCreatedDate())
			.deletedDate(user.get().getDeletedDate()).build();

		userRepository.save(new_user);
	}

	public void deleteUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		//존재하지 않는 이메일
		if (user.isEmpty()) {
			throw new NoExistEmailException(ResponseMessage.NO_EXIST_EMAIL);
		}

		User new_user = User.builder()
			.userId(user.get().getUserId())
			.email(user.get().getEmail())
			.password(user.get().getPassword())
			.nickname(user.get().getNickname())
			.image(user.get().getImage())
			.status(0)
			.createdDate(user.get().getCreatedDate())
			.deletedDate(user.get().getDeletedDate()).build();

		userRepository.save(new_user);
	}
}
