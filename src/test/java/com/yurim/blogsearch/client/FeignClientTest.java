package com.yurim.blogsearch.client;

import com.yurim.blogsearch.client.dto.kakao.KakaoSearchRequest;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import com.yurim.blogsearch.client.dto.naver.NaverSearchRequest;
import com.yurim.blogsearch.client.dto.naver.NaverSearchResponse;
import com.yurim.blogsearch.client.service.KakaoSearchClient;
import com.yurim.blogsearch.client.service.NaverSearchClient;
import com.yurim.blogsearch.common.error.exception.ClientRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FeignClientTest {

    @Autowired
    KakaoSearchClient kakaoSearchClient;

    @Value("${client.kakao.api-key}")
    private String apiKey;

    @Autowired
    NaverSearchClient naverSearchClient;

    @Value("${client.naver.client-id}")
    private String clientId;

    @Value("${client.naver.client-secret}")
    private String clientSecret;

    private final String NAVER_HEADER_CLIENT_ID = "X-Naver-Client-Id";

    private final String NAVER_HEADER_CLIENT_SECRET = "X-Naver-Client-Secret";

    @Test
    @DisplayName("feign을 사용하여 kakao 블로그 검색 open api 호출")
    void callKakaoBlogSearchSuccess() {

        //given
        String authorization = getKakaoAuthorization();
        Map<String,Object> headers = new HashMap<>();
        headers.put("Authorization",authorization);
        KakaoSearchRequest kakaoSearchRequest = KakaoSearchRequest.builder()
                .query("금리")
                .page(1)
                .size(10)
                .sort(KakaoSearchRequest.SORT_TYPE.accuracy)
                .build();

        //when
        KakaoSearchResponse result = kakaoSearchClient.search(headers,kakaoSearchRequest);

        //then
        assertThat(result.toString()).contains("total");

    }


    protected String getKakaoAuthorization() {
        return new StringBuilder("KakaoAK ")
                .append(apiKey).toString();
    }


    @Test
    @DisplayName("kakao 블로그 검색 api key 잘못됬을 경우 feign 에러 발생")
    void invalidApiKeyThrowException() {

        //given
        String authorization = new StringBuilder("KakaoAK ")
                .append("wrong key").toString();
        Map<String,Object> headers = new HashMap<>();
        headers.put("Authorization",authorization);
        KakaoSearchRequest kakaoSearchRequest = KakaoSearchRequest.builder()
                .query("금리")
                .page(1)
                .size(10)
                .sort(KakaoSearchRequest.SORT_TYPE.accuracy)
                .build();

        //when, then
        assertThatThrownBy(() -> kakaoSearchClient.search(headers,kakaoSearchRequest)).isInstanceOf(ClientRequestException.class);

    }



    @Test
    @DisplayName("feign을 사용하여 naver 블로그 검색 open api 호출")
    void callNaverBlogSearchSuccess() {

        //given
        Map<String, Object> headers = getNaverAuthHeader();

        NaverSearchRequest naverSearchRequest = NaverSearchRequest.builder()
                .query("금리")
                .display(10)
                .start(1)
                .sort(NaverSearchRequest.SORT_TYPE.sim)
                .build();

        //when
        NaverSearchResponse result = naverSearchClient.search(headers,naverSearchRequest);

        //then
        assertThat(result.toString()).contains("total");

    }

    Map<String,Object> getNaverAuthHeader() {
        Map<String,Object> headers = new HashMap<>();
        headers.put(NAVER_HEADER_CLIENT_ID,clientId);
        headers.put(NAVER_HEADER_CLIENT_SECRET,clientSecret);
        return headers;
    }



}
