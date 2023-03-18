package com.yurim.blogsearch.client.dto.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverSearchResponse {

    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<BlogItem> items;

}
