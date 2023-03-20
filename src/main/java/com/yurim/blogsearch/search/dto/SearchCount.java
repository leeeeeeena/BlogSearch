package com.yurim.blogsearch.search.dto;

import lombok.Getter;
import org.springframework.data.redis.core.ZSetOperations;

@Getter
public class SearchCount {

    private String query;

    private Long count;

    public SearchCount(String query, Long count) {
        this.query = query;
        this.count = count;
    }

    public static SearchCount of(String query, Long count) {
        return new SearchCount(query,count);
    }

}
