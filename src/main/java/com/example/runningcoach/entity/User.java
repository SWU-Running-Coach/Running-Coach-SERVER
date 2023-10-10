package com.example.runningcoach.entity;


import com.example.runningcoach.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(length = 100)
	private String email;

	@Column(length = 255)
	private String password;

	@Column(length = 45)
	private String nickname;

	@Builder.Default
	@Column(columnDefinition = "varchar(200) default 'default_url'")
	private String image = "default_url";

	@Builder.Default
	@Column(columnDefinition = "int default 1")
	private int status = 1;

	private LocalDateTime createdDate;

	private LocalDateTime deletedDate;

	@Builder.Default
	@Column(columnDefinition = "varchar(15) default 'MEMBER'")
	private String role = Role.MEMBER.toString();

}
