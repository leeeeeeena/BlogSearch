package com.yurim.blogsearch.search.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankResponse {
    List<SearchCount> topSearchQueries;

    private RankResponse(List<SearchCount> topSearchQueries) {
        this.topSearchQueries = topSearchQueries;
    }

    public static RankResponse of(List<SearchCount> topSearchQueries){
        return new RankResponse(topSearchQueries);
    }

}
