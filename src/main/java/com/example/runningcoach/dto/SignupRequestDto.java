package com.example.runningcoach.dto;

import com.example.runningcoach.response.ResponseMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    //TODO: 이메일이든 비밀번호든 값이 안들어오면 다 똑같은 에러메세지 반환 하는데, 뭐가 안들어왔는지 알려줘야하나 논의하기
    @Schema(description = "이메일")
    @NotBlank(message = ResponseMessage.EMPTY_VALUE)
    @Email(message = ResponseMessage.INVALID_EMAIL)
    private String email;

    //TODO: 비밀번호 글자수 제한 논의해보기
    @Schema(description = "비밀번호")
    @NotBlank(message = ResponseMessage.EMPTY_VALUE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = ResponseMessage.INVALID_PASSWORD)
    private String password;

    //TODO: 닉네임 글자수 제한 논의해보기
    @Schema(description = "닉네임")
    @NotBlank(message = ResponseMessage.EMPTY_VALUE)
    private String nickname;
}
