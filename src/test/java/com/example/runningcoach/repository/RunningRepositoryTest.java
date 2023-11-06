package com.example.runningcoach.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.runningcoach.entity.Running;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.enums.Role;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class RunningRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RunningRepository runningRepository;

	@Test
	@DisplayName("등록 테스트")
	public void saveSuccess() {
		//given
		User user = User.builder()
			.email("running@test")
			.password("1234")
			.nickname("running")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER.toString()).build();

		userRepository.save(user);

		//when
		Running running = Running.builder()
			.image("image")
			.cadence(10)
			.legAngle(150.1)
			.uppderBodyAngle(13)
			.dateTime(LocalDateTime.now())
			.user(user).build();

		runningRepository.save(running);

		List<Running> result = runningRepository.findByUserUserId(user.getUserId());
		assertThat(result.get(0).getUser()).isEqualTo(user);
	}

	@Test
	@DisplayName("이메일과 datetime로 조회 테스트")
	public void findByDatetime() {
		//given
		String email = "datetime@test.com";
		LocalDateTime localDateTime = LocalDateTime.now();

		User user = User.builder()
			.email(email)
			.password("1234")
			.nickname("running")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER.toString()).build();

		userRepository.save(user);

		//when
		Running running = Running.builder()
			.image("image")
			.cadence(10)
			.legAngle(150.1)
			.uppderBodyAngle(13)
			.dateTime(localDateTime)
			.user(user).build();

		runningRepository.save(running);

		Running running2 = Running.builder()
			.image("image")
			.cadence(4)
			.legAngle(155.5)
			.uppderBodyAngle(3)
			.dateTime(LocalDateTime.now())
			.user(user).build();

		runningRepository.save(running2);

		List<Running> result = runningRepository.findByUserEmailAndDateTime(email, localDateTime);
		assertThat(result.get(0).getCadence()).isEqualTo(running.getCadence());
		assertThat(result.get(0).getLegAngle()).isEqualTo(running.getLegAngle());
		assertThat(result.get(0).getUppderBodyAngle()).isEqualTo(running.getUppderBodyAngle());
	}

	@Test
	@DisplayName("이메일과 datetime의 월로 조회 테스트")
	public void findByDatetimeMonthAndEmail() {
		//given
		String email = "datetimemonth@test.com";
		LocalDateTime localDateTime = LocalDateTime.now();

		User user = User.builder()
			.email(email)
			.password("1234")
			.nickname("running")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER.toString()).build();

		userRepository.save(user);

		//when
		Running running = Running.builder()
			.image("image")
			.cadence(10)
			.legAngle(150.1)
			.uppderBodyAngle(13)
			.dateTime(localDateTime)
			.user(user).build();

		runningRepository.save(running);

		Running running2 = Running.builder()
			.image("image")
			.cadence(4)
			.legAngle(155.5)
			.uppderBodyAngle(3)
			.dateTime(LocalDateTime.now())
			.user(user).build();

		runningRepository.save(running2);

		List<Running> result = runningRepository.findByDateTimeYearAndMonthAndUserEmail(localDateTime.getYear(), localDateTime.getMonthValue(), email);

		assertThat(running).isEqualTo(result.get(0));
		assertThat(running2).isEqualTo(result.get(1));
	}
}

