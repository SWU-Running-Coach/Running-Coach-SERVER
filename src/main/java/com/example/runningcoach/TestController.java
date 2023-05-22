package com.example.runningcoach;

import com.example.runningcoach.dto.LoginRequest;
import com.example.runningcoach.response.BaseResponse;
import com.example.runningcoach.response.ResponseMessage;
import com.example.runningcoach.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/test1")
    public ResponseEntity dataTest(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity(BaseResponse.response(StatusCode.OK, ResponseMessage.SUCCESS, loginRequest),
            HttpStatus.OK);
    }

    @GetMapping("test2")
    public ResponseEntity noDataTest() {
        return new ResponseEntity(BaseResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST),
            HttpStatus.BAD_REQUEST);
    }
}
