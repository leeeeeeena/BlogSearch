package com.yurim.blogsearch.search.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse<T> {

    private int total;

    private int size;

    List<T>  elements;

    public SearchResponse(int total, int size, List<T> elements) {
        this.total = total;
        this.size = size;
        this.elements = elements;
    }


}
