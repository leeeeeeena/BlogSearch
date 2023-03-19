package com.yurim.blogsearch.common;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class CommonResponse<T> {

    private String message;
    private String code;
    private T data;

    private CommonResponse(String message, String code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static <T> CommonResponse<T> of(String message, ResponseCode code) {
        return new CommonResponse<>(
                StringUtils.hasText(message) ? message : code.getReason(),
                code.getCode(),
                null);
    }

    public static <T> CommonResponse<T> of(String message, ResponseCode code, T data) {
        return new CommonResponse<>(message, code.getCode(), data);
    }

    public static <T> CommonResponse<T> of(ResponseCode code, T data) {
        return new CommonResponse<>(code.getReason(), code.getCode(), data);
    }

}
