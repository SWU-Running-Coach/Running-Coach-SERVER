package com.example.runningcoach.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Running {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long runningId;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(length = 255)
	private String image;

	private int cadence;

	private double legAngle;

	private double uppderBodyAngle;

	private LocalDateTime dateTime;
	private String feedback_leg;
	private String feedback_body;

}
