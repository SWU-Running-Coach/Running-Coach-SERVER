package com.example.runningcoach.repository;

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
		//when
		//then
	}

	@Test
	@DisplayName("이메일로 회원 조회")
	public void findByEmail() {
		//given
		//when
		//then
	}


}
