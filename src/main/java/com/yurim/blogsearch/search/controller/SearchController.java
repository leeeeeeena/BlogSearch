package com.yurim.blogsearch.search.controller;

import com.yurim.blogsearch.common.CommonResponse;
import com.yurim.blogsearch.common.ResponseCode;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.search.service.SearchBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search")
public class SearchController {

    private final SearchBlogService searchBlogService;

    /**
     * 장소 검색 API
     */
    @GetMapping("/blog")
    public CommonResponse searchPlace(@RequestParam(required = true) String query,
                                      @RequestParam(defaultValue = "1",required = false) int page,
                                      @RequestParam(defaultValue = "10",required = false) int size,
                                      @RequestParam(required = false) SearchRequest.SORT_TYPE sort){

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        SearchResponse<BlogPost> searchResponse = searchBlogService.search(searchRequest);

        return CommonResponse.of(
                ResponseCode.REQUEST_SUCCESS,
                searchResponse);
    }

}
