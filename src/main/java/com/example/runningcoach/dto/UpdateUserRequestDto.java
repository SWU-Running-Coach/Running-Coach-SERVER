package com.example.runningcoach.dto;

import com.example.runningcoach.response.ResponseMessage;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = ResponseMessage.INVALID_PASSWORD)
	private String password;
	private String nickname;
	private String profile;
}
