package com.example.runningcoach.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    @Schema(description = "access token")
    private String accessToken;
    @Schema(description = "refresh token")
    private String refreshToken;
}
