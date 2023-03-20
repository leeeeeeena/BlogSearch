package com.yurim.blogsearch.client.dto.naver;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class NaverSearchResponse<T> {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<T> items;

}
