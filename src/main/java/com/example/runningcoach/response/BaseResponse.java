package com.example.runningcoach.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BaseResponse<T> {

    @Schema(description = "상태 코드")
    private int statusCode;
    @Schema(description = "메시지")
    private String message;
    @Schema(description = "응답 데이터")
    private T data;

    public BaseResponse(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static<T> BaseResponse<T> response(final int statusCode, final String message) {
        return response(statusCode, message, null);
    }

    public static<T> BaseResponse<T> response(final int statusCode, final String message, final T t) {
        return BaseResponse.<T>builder()
            .data(t)
            .statusCode(statusCode)
            .message(message)
            .build();
    }
}
