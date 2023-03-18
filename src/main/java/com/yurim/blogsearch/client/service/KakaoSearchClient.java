package com.yurim.blogsearch.client.service;

import com.yurim.blogsearch.client.dto.kakao.KakaoSearchRequest;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(url = "${client.kakao.host}",name = "kakao")
@Headers({ "Content-Type: application/json" })
public interface KakaoSearchClient {

    @GetMapping("${client.kakao.search-uri}")
    KakaoSearchResponse search(@RequestHeader Map<String,Object> headers, @SpringQueryMap KakaoSearchRequest searchQuery);
    // spring web context 를 사용하고 있으므로, feign.HeaderMap 이 아니라 @RequestHeader 사용
}
