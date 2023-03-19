package com.yurim.blogsearch.search.service.impl;

import com.yurim.blogsearch.client.ClientType;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchRequest;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import com.yurim.blogsearch.client.service.SearchClientService;
import com.yurim.blogsearch.client.service.impl.SearchBlogRouter;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.search.service.SearchBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchBlogServiceImpl implements SearchBlogService {

    private final SearchBlogRouter searchBlogRouter;
    @Override
    public SearchResponse<BlogPost> search(SearchRequest searchRequest) {

        SearchClientService searchClient = searchBlogRouter.getServiceByType(ClientType.KAKAO);
        SearchResponse kakaoSearchResponse = searchClient.search(searchRequest);

        return kakaoSearchResponse;
    }

}
