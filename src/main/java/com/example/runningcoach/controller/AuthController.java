package com.example.runningcoach.controller;

import com.example.runningcoach.dto.FindpwRequestDto;
import com.example.runningcoach.dto.LoginRequestDto;
import com.example.runningcoach.dto.LoginResponseDto;
import com.example.runningcoach.dto.SingupRequestDto;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "auth", description = "로그인, 회원가입 관련 api")
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Operation(summary = "회원가입", description = "회원가입 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 201, \"message\" : \"성공\", \"data\" : {}}")})),
        @ApiResponse(responseCode = "400", description = "1. 이메일을 입력해주세요 \t\n 2. 비밀번호를 입력해주세요 \t\n 3. 올바르지 않은 이메일입니다 \t\n"
            + "4. 올바르지 않은 비밀번호입니다 \t\n 5. 이미 가입된 이메일입니다",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 400, \"message\" : \"이메일을 입력해주세요\", \"data\" : {}}")}))
    })

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SingupRequestDto singupRequestDto) {

        return new ResponseEntity(BaseResponse.response(StatusCode.CREATED, ResponseMessage.SUCCESS),
            HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "로그인 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 200, \"message\" : \"성공\", \"data\" : {\"accessToken\" : \"aaaaaaaaaaaaaaaa\", \"refreshToken\" : \"aaaaaaaaaaaaaaaa\" }}")})),
        @ApiResponse(responseCode = "400", description = "로그인에 실패했습니다",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 400, \"message\" : \"로그인에 실패했습니다\", \"data\" : {}}")}))
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) {

        LoginResponseDto loginResponseDto = new LoginResponseDto();

        return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS, loginResponseDto),
            HttpStatus.OK);
    }

    @Operation(summary = "access token 재발급", description = "access token 재발급 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 201, \"message\" : \"성공\", \"data\" : {\"accessToken\" : \"aaaaaaaaaaaaaaaa\" }}")})),
        @ApiResponse(responseCode = "400", description = "1. 토큰이 존재하지 않습니다 \t\n 2. 토큰이 만료되었습니다",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 400, \"message\" : \"토큰이 존재하지 않습니다\", \"data\" : {}}")}))

    })
    @PostMapping("/access-token")
    public ResponseEntity accessToken(@RequestHeader("Authorization") String accessToken) {

        String token = "";

        return new ResponseEntity(BaseResponse.response(StatusCode.CREATED, ResponseMessage.SUCCESS, token),
            HttpStatus.CREATED);
    }

    @Operation(summary = "임시 비밀번호 발급", description = "임시 비밀번호 발급 api")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 200, \"message\" : \"성공\", \"data\" : {} }")})),
        @ApiResponse(responseCode = "400", description = "1. 올바르지 않은 이메일입니다 \t\n 2. 존재하지 않는 이메일입니다",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BaseResponse.class),
                examples = {@ExampleObject(value = "{\"statusCode\": 400, \"message\" : \"올바르지 않은 이메일입니다\", \"data\" : {}}")}))

    })
    @PostMapping("/find-pw")
    public ResponseEntity findPw(@RequestBody FindpwRequestDto findpwRequestDto)
    {
        return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS),
            HttpStatus.OK);
    }

}
