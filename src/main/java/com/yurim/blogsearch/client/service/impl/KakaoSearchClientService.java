package com.yurim.blogsearch.client.service.impl;

import com.yurim.blogsearch.client.dto.kakao.KakaoSearchRequest;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import com.yurim.blogsearch.client.service.KakaoSearchClient;
import com.yurim.blogsearch.client.service.SearchClientService;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Qualifier("KAKAO")
@RequiredArgsConstructor
@Slf4j
public class KakaoSearchClientService implements SearchClientService {

    @Value("${client.kakao.api-key}")
    private String apiKey;

    private final KakaoSearchClient kakaoSearchClient;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private CircuitBreaker circuitBreaker;

    private final KakaoBlogResponseModifier kakaoBlogResponseModifier;

    @PostConstruct
    private void init() {
        circuitBreaker = circuitBreakerFactory.create("KAKAO_OPEN_API");
    }

    protected KakaoSearchResponse callSearchApi(KakaoSearchRequest kakaoSearchRequest) {


        String authorization = getAuthorization();

        Map<String,Object> headers = new HashMap<>();
        headers.put("Authorization",authorization);
        KakaoSearchResponse result = kakaoSearchClient.search(headers,kakaoSearchRequest);
        return result;
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {

        KakaoSearchRequest kakaoSearchRequest = buildKakaoSearchRequest(searchRequest);

        KakaoSearchResponse response = circuitBreaker.run(() -> this.callSearchApi(kakaoSearchRequest), throwable -> {
            log.error(throwable.getMessage());
            return null;
        });

        SearchResponse searchResponse = kakaoBlogResponseModifier.modify(response);
        return searchResponse;
    }

    private KakaoSearchRequest buildKakaoSearchRequest(SearchRequest searchRequest) {
        return KakaoSearchRequest.builder()
                .query(searchRequest.getQuery())
                .page(searchRequest.getPage())
                .size(searchRequest.getSize())
                .sort(convertToKakaoSearchSortType(searchRequest))
                .build();
    }

    private KakaoSearchRequest.SORT_TYPE convertToKakaoSearchSortType(SearchRequest searchRequest) {
        KakaoSearchRequest.SORT_TYPE sortType = null;

        if (searchRequest.getSort() == null) {
            return null;
        }

        if (searchRequest.getSort() == SearchRequest.SORT_TYPE.accuracy) {
            sortType = KakaoSearchRequest.SORT_TYPE.accuracy;
        }

        if (searchRequest.getSort() == SearchRequest.SORT_TYPE.recency) {
            sortType = KakaoSearchRequest.SORT_TYPE.recency;
        }

        return sortType;
    }

    protected String getAuthorization() {
        return new StringBuilder("KakaoAK ")
                .append(apiKey).toString();
    }

}
