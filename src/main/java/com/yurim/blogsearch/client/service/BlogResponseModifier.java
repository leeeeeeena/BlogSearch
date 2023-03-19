package com.yurim.blogsearch.client.service;

import com.yurim.blogsearch.search.dto.SearchResponse;

public interface BlogResponseModifier<T> {
    SearchResponse modify(T response);
}
