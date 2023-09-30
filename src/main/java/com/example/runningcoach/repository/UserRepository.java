package com.example.runningcoach.repository;

import com.example.runningcoach.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
