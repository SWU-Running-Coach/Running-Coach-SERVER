package com.example.runningcoach.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingupRequestDto {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;
    @Schema(description = "닉네임")
    private String nickname;
}
