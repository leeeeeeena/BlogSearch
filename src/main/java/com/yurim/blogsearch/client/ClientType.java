package com.yurim.blogsearch.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {

    KAKAO("kakaoSearchClientService",0),
    NAVER("naverSearchClientService",1),

    ;

    private String beanName;
    private int order;
}
