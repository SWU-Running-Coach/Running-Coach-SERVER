package com.example.runningcoach.response;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.runningcoach.TestController;
import com.example.runningcoach.dto.LoginRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(TestController.class)
public class ResponseTest {

    @Autowired
    MockMvc mvc;

    @DisplayName("data가 있는 테스트")
    @Test
    void dataTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("테스트용 이메일");
        loginRequest.setPassword("테스트용 비밀번호");

        Gson gson = new Gson();
        String content = gson.toJson(loginRequest);

        mvc.perform(post("/test/test1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print());


    }

    @DisplayName("data가 없는 테스트")
    @Test
    void noDataTest() throws Exception {
        mvc.perform(get("/test/test2"))
            .andExpect(status().isBadRequest())
            .andDo(MockMvcResultHandlers.print());
    }
}
