package com.yurim.blogsearch.client.service;

import com.yurim.blogsearch.client.dto.naver.BlogItem;
import com.yurim.blogsearch.client.dto.naver.NaverSearchRequest;
import com.yurim.blogsearch.client.dto.naver.NaverSearchResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(url = "${client.naver.host}",name = "naver")
@Headers({ "Content-Type: application/json" })
public interface NaverSearchClient {

    @GetMapping("${client.naver.search-uri}")
    NaverSearchResponse<BlogItem> search(@RequestHeader Map<String,Object> headers, @SpringQueryMap NaverSearchRequest searchQuery);
    // spring web context 를 사용하고 있으므로, feign.HeaderMap 이 아니라 @RequestHeader 사용
}
