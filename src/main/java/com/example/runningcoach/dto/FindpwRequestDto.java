package com.example.runningcoach.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindpwRequestDto {
    @Schema(description = "이메일")
    private String email;
}
