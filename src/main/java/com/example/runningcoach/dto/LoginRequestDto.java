package com.example.runningcoach.dto;

import com.example.runningcoach.response.ResponseMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @Schema(description = "이메일")
    @NotBlank(message = ResponseMessage.EMPTY_VALUE)
    private String email;
    @Schema(description = "비밀번호")
    @NotBlank(message = ResponseMessage.EMPTY_VALUE)
    private String password;
}
