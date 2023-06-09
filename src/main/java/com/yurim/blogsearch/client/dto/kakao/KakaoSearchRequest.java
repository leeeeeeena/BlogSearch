package com.yurim.blogsearch.client.dto.kakao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoSearchRequest {

    private String query;

    private int page; // 1~50

    private int size; // default : 10, 1~50

    private SORT_TYPE sort;

    public enum SORT_TYPE {
        accuracy, //정확도순 (기본)
        recency //최신순
    }
}
