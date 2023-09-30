package com.example.runningcoach.entity;

import com.example.runningcoach.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(length = 100, nullable = false)
	private String email;
	@Column(length = 255, nullable = false)
	private String password;
	@Column(length = 45, nullable = false)
	private String nickname;
	@Column(length = 200, columnDefinition = "default_url")
	private String image;
	@Column(columnDefinition = "1")
	private int status;
	@Column(nullable = false)
	private LocalDateTime createdDate;
	@Column(nullable = true)
	private LocalDateTime deletedDate;
	@Enumerated(EnumType.STRING)
	@Column(length = 15, columnDefinition = "MEMBER")
	private Role role;

	@Builder
	public User(String email, String password, String nickname, String image, int status, LocalDateTime createdDate, LocalDateTime deletedDate, Role role) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.image = image;
		this.status = status;
		this.createdDate = createdDate;
		this.deletedDate = deletedDate;
		this.role = role;
	}
}
