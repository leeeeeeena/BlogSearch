package com.yurim.blogsearch.search.service.impl;

import com.yurim.blogsearch.client.ClientType;
import com.yurim.blogsearch.client.service.SearchClientService;
import com.yurim.blogsearch.client.service.impl.SearchBlogRouter;
import com.yurim.blogsearch.search.domain.SearchHistory;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.search.repository.SearchCacheRepository;
import com.yurim.blogsearch.search.repository.SearchHistoryRepository;
import com.yurim.blogsearch.search.service.SearchBlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchBlogServiceImpl implements SearchBlogService {

    private final SearchBlogRouter searchBlogRouter;

    private final SearchHistoryRepository searchHistoryRepository;

    private final SearchCacheRepository searchCacheRepository;

    @Value("${infra.enable.redis}")
    private boolean useCache;

    @Override
    @Transactional
    public SearchResponse<BlogPost> search(SearchRequest searchRequest) {

        SearchResponse searchResponse = getSearchResponse(searchRequest);

        saveSearchHistory(searchRequest);

        return searchResponse;
    }



    private SearchResponse getSearchResponse(SearchRequest searchRequest) {

        SearchResponse searchResponse;
        try {
            searchResponse = getSearchResponseByClientType(searchRequest, ClientType.KAKAO);
        } catch (Exception e) {
            searchResponse = getSearchResponseByClientType(searchRequest, ClientType.NAVER);
            log.error("KaKao open api 호출 에러: {}",e.getMessage());
        }
        return searchResponse;
    }



    private SearchResponse getSearchResponseByClientType(SearchRequest searchRequest, ClientType clientType) {
        SearchClientService searchClient = searchBlogRouter.getServiceByType(clientType);
        SearchResponse searchResponse = searchClient.search(searchRequest);
        return searchResponse;
    }



    private void saveSearchHistory(SearchRequest searchRequest) {
        saveSearchQueryInCache(searchRequest);
        saveSearchHistoryInDB(searchRequest);
    }



    private void saveSearchQueryInCache(SearchRequest searchRequest) {
        if (useCache) {
            searchCacheRepository.updateScore(searchRequest);
        }
    }



    public void saveSearchHistoryInDB(SearchRequest searchRequest) {

        SearchHistory searchHistory = SearchHistory.of(searchRequest.getQuery());
        searchHistoryRepository.save(searchHistory);
    }


}
