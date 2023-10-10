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
		User user = User.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role("MEMBER").build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
	}

	@Test
	@DisplayName("image와 status, role 값 없이 유저 등록 테스트")
	public void saveUserNoImage() {
		//given
		User user = User.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
		assertThat(result.getImage()).isEqualTo("default_url");
		assertThat(result.getStatus()).isEqualTo(1);
		assertThat(result.getRole()).isEqualTo("MEMBER");
	}

	@Test
	@DisplayName("status 값 없이 유저 등록 테스트")
	public void saveUserNoStatus() {
		//given
		User user = User.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role("MEMBER").build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(user).isEqualTo(result);
		assertThat(result.getStatus()).isEqualTo(1);

	}

	@Test
	@DisplayName("deletedDate 값 없이 유저 등록 테스트")
	public void saveUserNodeletedDate() {
		//given
		User user = User.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.role("MEMBER").build();

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
		User user = User.builder()
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
		assertThat(result.getRole()).isEqualTo("MEMBER");
	}

	@Test
	@DisplayName("default 값 등록 테스트")
	public void saveDeafult() {
		//given
		User user = new User();
		//when
		userRepository.save(user);

		//then
		User result = userRepository.findById(user.getUserId()).get();
		assertThat(result.getImage()).isEqualTo("default_url");
		assertThat(result.getStatus()).isEqualTo(1);
		assertThat(result.getRole()).isEqualTo("MEMBER");
	}

	@Test
	@DisplayName("이메일로 회원 조회")
	public void findByEmail() {
		//given
		User user = User.builder()
			.email("test@test")
			.password("1234")
			.nickname("nick")
			.image("image")
			.status(1)
			.createdDate(LocalDateTime.now())
			.deletedDate(LocalDateTime.now())
			.role("MEMBER").build();

		//when
		userRepository.save(user);

		//then
		User result = userRepository.findByEmail(user.getEmail()).get();
		assertThat(user).isEqualTo(result);
	}


}
