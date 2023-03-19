package com.yurim.blogsearch.search.service;

import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import org.springframework.stereotype.Service;

@Service
public class SearchBlogServiceImpl implements SearchBlogService{

    @Override
    public SearchResponse<BlogPost> search(SearchRequest searchRequest) {
        return null;
    }
}
