package com.yurim.blogsearch.client.service.impl;

import com.yurim.blogsearch.client.dto.naver.NaverSearchRequest;
import com.yurim.blogsearch.client.dto.naver.NaverSearchResponse;
import com.yurim.blogsearch.client.service.NaverSearchClient;
import com.yurim.blogsearch.client.service.SearchClientService;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Qualifier("NAVER")
@RequiredArgsConstructor
@Slf4j
public class NaverSearchClientService implements SearchClientService {

    @Value("${client.naver.client-id}")
    private String clientId;

    @Value("${client.naver.client-secret}")
    private String clientSecret;

    private final String REQUEST_HEADER_CLIENT_ID = "X-Naver-Client-Id";

    private final String REQUEST_HEADER_CLIENT_SECRET = "X-Naver-Client-Secret";

    private final NaverSearchClient naverSearchClient;
    private final NaverBlogResponseModifier naverBlogResponseModifier;

    protected NaverSearchResponse callSearchApi(NaverSearchRequest naverSearchRequest) {



        Map<String,Object> headers = new HashMap<>();
        headers.put(REQUEST_HEADER_CLIENT_ID,clientId);
        headers.put(REQUEST_HEADER_CLIENT_SECRET,clientSecret);
        NaverSearchResponse result = naverSearchClient.search(headers,naverSearchRequest);
        return result;
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {

        NaverSearchRequest naverSearchRequest = buildNaverSearchRequest(searchRequest);

        NaverSearchResponse response = this.callSearchApi(naverSearchRequest);

        SearchResponse searchResponse = naverBlogResponseModifier.modify(response);
        return searchResponse;
    }

    private NaverSearchRequest buildNaverSearchRequest(SearchRequest searchRequest) {
        return NaverSearchRequest.builder()
                .query(searchRequest.getQuery())
                .start(searchRequest.getPage())
                .display(searchRequest.getSize())
                .sort(convertToNaverSearchSortType(searchRequest))
                .build();
    }

    private NaverSearchRequest.SORT_TYPE convertToNaverSearchSortType(SearchRequest searchRequest) {
        NaverSearchRequest.SORT_TYPE sortType = null;

        if (Objects.isNull(searchRequest.getSort())) {
            return null;
        }

        if (searchRequest.getSort() == SearchRequest.SORT_TYPE.accuracy) {
            sortType = NaverSearchRequest.SORT_TYPE.sim;
        }

        if (searchRequest.getSort() == SearchRequest.SORT_TYPE.recency) {
            sortType = NaverSearchRequest.SORT_TYPE.date;
        }

        return sortType;
    }

}
