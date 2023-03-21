package com.yurim.blogsearch.service;

import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchRequest;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.search.service.SearchBlogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;
@TestPropertySource(properties = {
        "infra.enable.redis=false",
        "client.kakao.search-uri=wrong-uri"
})
@SpringBootTest
public class SearchBlogServiceTest {

    @Autowired
    SearchBlogService searchBlogService;


    @Test
    @DisplayName("블로그 검색서비스에서 카카오api 호출시 에러가 나면 네이버api 호출")
    void callNaverApiWhenKakaoApiThrowException(){

        SearchRequest searchRequest = SearchRequest.builder()
                .query("에러")
                .build();
        SearchResponse<BlogPost> searchResponse = searchBlogService.search(searchRequest);

        //네이버는 썸네일이 응답에 없음
        assertThat(searchResponse.getElements().get(0).getThumbnail()).isNull();
    }

}
