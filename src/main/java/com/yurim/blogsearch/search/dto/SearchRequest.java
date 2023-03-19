package com.yurim.blogsearch.search.dto;

import lombok.Getter;

@Getter
public class SearchRequest extends PagingRequest{
    private String query;

}
