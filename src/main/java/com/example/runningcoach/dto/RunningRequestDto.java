package com.example.runningcoach.dto;

import com.example.runningcoach.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunningRequestDto {
	private String image;
	private int cadence;
	private double legAngle;
	private double uppderBodyAngle;
	private LocalDateTime dateTime;
}

