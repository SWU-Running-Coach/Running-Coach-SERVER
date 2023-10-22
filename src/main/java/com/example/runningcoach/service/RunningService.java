package com.example.runningcoach.service;

import com.example.runningcoach.dto.RunningRequestDto;
import com.example.runningcoach.entity.Running;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.exception.custom.NoExistEmailException;
import com.example.runningcoach.repository.RunningRepository;
import com.example.runningcoach.repository.UserRepository;
import com.example.runningcoach.response.ResponseMessage;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunningService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RunningRepository runningRepository;

	public void runningAnalyze(RunningRequestDto runningRequestDto, String email) {
		Optional<User> user = userRepository.findByEmail(email);

		//존재하지 않는 이메일
		if (user.isEmpty()) {
			throw new NoExistEmailException(ResponseMessage.NO_EXIST_EMAIL);
		}

		Running running = Running.builder()
			.user(user.get())
			.image(runningRequestDto.getImage())
			.cadence(runningRequestDto.getCadence())
			.uppderBodyAngle(runningRequestDto.getUppderBodyAngle())
			.legAngle(runningRequestDto.getLegAngle())
			.dateTime(runningRequestDto.getDateTime()).build();

		runningRepository.save(running);
	}
}
