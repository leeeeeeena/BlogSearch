package com.yurim.blogsearch.client.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoSearchRequest {

    private String query;

    private int page;

    private int size; // default : 10, 1~50

    private SORT_TYPE sort;

    public enum SORT_TYPE {
        accuracy, //정확도순 (기본)
        recency //최신순
    }
}
