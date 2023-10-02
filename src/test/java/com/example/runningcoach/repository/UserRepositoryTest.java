package com.example.runningcoach.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.runningcoach.entity.User;
import com.example.runningcoach.enums.Role;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("유저 등록 테스트")
	public void saveUser() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("image 값 없이 유저 등록 테스트")
	public void saveUserNoImage() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("status 값 없이 유저 등록 테스트")
	public void saveUserNoStatus() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("deletedDate 값 없이 유저 등록 테스트")
	public void saveUserNodeletedDate() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.role(Role.MEMBER).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("role 값 없이 유저 등록 테스트")
	public void saveUserNoRole() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now()).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("이메일로 회원 조회")
	public void findByEmail() {
		//given
		User user = new User();

		user.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role(Role.MEMBER).build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findByEmail(user.getEmail()).get();
		assertThat(user).isEqualTo(result);
	}


}
