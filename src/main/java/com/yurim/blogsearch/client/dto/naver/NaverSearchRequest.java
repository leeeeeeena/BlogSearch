package com.yurim.blogsearch.client.dto.naver;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverSearchRequest {

    private String query;

    private int display; // 한번에 표시할 결과 갯수 default : 10 , max : 100

    private int start; // default : 1 , max : 100

    private SORT_TYPE sort;


    public enum SORT_TYPE {

        sim, // 정확도순 (기본값)
        date // 날짜순 (내림차순)
    }

}
