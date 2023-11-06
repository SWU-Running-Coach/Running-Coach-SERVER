package com.example.runningcoach.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackByMonthDayResponseDto {
	private int idx;
	private String image;
	private int cadence;
	private double legAngle;
	private double upperBodyAngle;
}
