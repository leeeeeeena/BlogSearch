package com.yurim.blogsearch.client.dto.kakao;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class KakaoSearchResponse<T> {

    private BlogMeta meta;

    private List<T> documents;

}
