package com.yurim.blogsearch.client.service.impl;

import com.yurim.blogsearch.client.dto.kakao.KakaoSearchRequest;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import com.yurim.blogsearch.client.service.KakaoSearchClient;
import com.yurim.blogsearch.client.service.SearchClientService;
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
public class KakaoSearchClientService implements SearchClientService<KakaoSearchRequest,KakaoSearchResponse> {

    @Value("${client.kakao.api-key}")
    private String apiKey;

    private final KakaoSearchClient kakaoSearchClient;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private CircuitBreaker circuitBreaker;

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
    public KakaoSearchResponse search(KakaoSearchRequest searchRequest) {
        //circuit breaker 적용
        KakaoSearchResponse response = circuitBreaker.run(() -> this.callSearchApi(searchRequest), throwable -> {
            log.error(throwable.getMessage());
            return null;
        });

        return response;
    }

    protected String getAuthorization() {
        return new StringBuilder("KakaoAK ")
                .append(apiKey).toString();
    }

}
