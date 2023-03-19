package com.yurim.blogsearch.search.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RankRequest {
    LocalDate searchDate;

    private RankRequest(LocalDate searchDate) {
        this.searchDate = searchDate;
    }

    public static RankRequest of(LocalDate date){
        return new RankRequest(date);
    }

}
