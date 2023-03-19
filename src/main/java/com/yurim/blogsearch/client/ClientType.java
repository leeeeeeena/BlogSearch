package com.yurim.blogsearch.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {

    KAKAO("kakaoSearchClientService"),
    NAVER("naverSearchClientService"),

    ;

    private String searchClientService;
}
