package com.yurim.blogsearch.search.service;

import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;

public interface SearchBlogService {

    SearchResponse<BlogPost> search(SearchRequest searchRequest);
}
