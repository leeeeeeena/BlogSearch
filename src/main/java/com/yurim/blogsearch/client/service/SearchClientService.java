package com.yurim.blogsearch.client.service;

import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;

public interface SearchClientService {

    SearchResponse search(SearchRequest searchRequest);
}
