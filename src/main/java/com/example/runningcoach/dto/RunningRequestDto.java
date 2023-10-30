package com.example.runningcoach.dto;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunningRequestDto {
	private String image;
	private int cadence;
	private double legAngle;
	//TODO: 오타 수정
	private double uppderBodyAngle;
	private LocalDateTime dateTime;
}

