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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Qualifier("KAKAO")
@RequiredArgsConstructor
@Slf4j
public class KakaoSearchClientService implements SearchClientService {

    @Value("${client.kakao.api-key}")
    private String apiKey;

    private final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    private final KakaoSearchClient kakaoSearchClient;
    private final KakaoBlogResponseModifier kakaoBlogResponseModifier;

    protected KakaoSearchResponse callSearchApi(KakaoSearchRequest kakaoSearchRequest) {


        String authorization = getAuthorization();
        Map<String,Object> headers = new HashMap<>();
        headers.put(REQUEST_HEADER_AUTHORIZATION,authorization);

        KakaoSearchResponse result = kakaoSearchClient.search(headers,kakaoSearchRequest);
        return result;
    }

    @Override
    public SearchResponse search(SearchRequest searchRequest) {

        KakaoSearchRequest kakaoSearchRequest = buildKakaoSearchRequest(searchRequest);

        KakaoSearchResponse response = this.callSearchApi(kakaoSearchRequest);

        //FIXME null 리턴하면 nullpointexception 에러남
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

        if (Objects.isNull(searchRequest.getSort())) {
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
