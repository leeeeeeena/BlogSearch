package com.yurim.blogsearch.search.dto;

import lombok.Getter;

@Getter
public class PagingRequest {

    private int page; // 1~50

    private int size; // default : 10, 1~50

    private SORT_TYPE sort;

    public enum SORT_TYPE {
        accuracy, //정확도순 (기본)
        recency //최신순
    }

}
