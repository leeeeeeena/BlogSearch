package com.yurim.blogsearch.search.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PagingResponse<T> {

    private int total;

    private int size;

    List<T>  elements;


}
