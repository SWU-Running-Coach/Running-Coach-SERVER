package com.example.runningcoach.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunningResponseDto {

	private String image;
	private int cadence;
	private double legAngle;
	private double upperBodyAngle;
	private LocalDateTime dateTime;
}
