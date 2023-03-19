package com.yurim.blogsearch.search.dto;

import lombok.Getter;

@Getter
public class SearchCount {

    private String query;

    private Long count;

    public SearchCount(String query, Long count) {
        this.query = query;
        this.count = count;
    }
}
