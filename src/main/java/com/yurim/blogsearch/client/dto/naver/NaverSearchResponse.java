package com.yurim.blogsearch.client.dto.naver;

import lombok.Getter;

import java.util.List;

@Getter
public class NaverSearchResponse<T> {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<T> items;

}
