package com.yurim.blogsearch.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchRequest {

    private String query;

    private int page; // 1~50

    private int size; // default : 10, 1~50

    private SORT_TYPE sort;

    @Getter
    public enum SORT_TYPE {
        accuracy, //정확도순 (기본)
        recency //최신순
    }

}
