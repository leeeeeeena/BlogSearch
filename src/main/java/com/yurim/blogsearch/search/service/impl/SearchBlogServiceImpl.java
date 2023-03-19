package com.yurim.blogsearch.search.service.impl;

import com.yurim.blogsearch.client.ClientType;
import com.yurim.blogsearch.client.service.SearchClientService;
import com.yurim.blogsearch.client.service.impl.SearchBlogRouter;
import com.yurim.blogsearch.search.domain.SearchHistory;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.search.repository.SearchHistoryRepository;
import com.yurim.blogsearch.search.service.SearchBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchBlogServiceImpl implements SearchBlogService {

    private final SearchBlogRouter searchBlogRouter;

    private final SearchHistoryRepository searchHistoryRepository;
    @Override
    @Transactional
    public SearchResponse<BlogPost> search(SearchRequest searchRequest) {

        SearchClientService searchClient = searchBlogRouter.getServiceByType(ClientType.KAKAO);
        SearchResponse kakaoSearchResponse = searchClient.search(searchRequest);

        saveSearchHistory(searchRequest);
        return kakaoSearchResponse;
    }

    public void saveSearchHistory(SearchRequest searchRequest){

        SearchHistory searchHistory = SearchHistory.of(searchRequest.getQuery());
        searchHistoryRepository.save(searchHistory);
    }

}
