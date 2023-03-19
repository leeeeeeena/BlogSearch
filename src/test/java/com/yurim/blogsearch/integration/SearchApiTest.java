package com.yurim.blogsearch.integration;

import com.yurim.blogsearch.search.dto.SearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("integration")
public class SearchApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    public String SEARCH_PLACE_PATH = "/v1/search/blog";

    public HttpEntity httpEntity;

    @BeforeEach
    public void prepare(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(httpHeaders);
    }

    @Test
    @DisplayName("정상적인 키워드 검색 요청에 대해 정상 응답")
    public void blogSearchRequestReturn200() {

        String uri = SEARCH_PLACE_PATH + "?query={query}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("query","카카오");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, queryParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }



    @Test
    @DisplayName("키워드 검색 응답이 1개 이상일 시 다음 필드를 포함하고 있어야 한다.")
    public void responseContainValues() {

        String uri = SEARCH_PLACE_PATH + "?query={query}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("query","은행");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, queryParams);
        assertThat(response.getBody()).contains("total");
        assertThat(response.getBody()).contains("size");
        assertThat(response.getBody()).contains("elements");
        assertThat(response.getBody()).contains("postName");
        assertThat(response.getBody()).contains("url");
        assertThat(response.getBody()).contains("blogName");
        assertThat(response.getBody()).contains("description");
        assertThat(response.getBody()).contains("thumbnail");
        assertThat(response.getBody()).contains("date");

    }


    @Test
    @DisplayName("페이징할 페이지넘버와 사이즈, sorting 방법을 정해서 요청할 수 있다")
    public void canRequestPagingAndPagingSize() {

        String uri = SEARCH_PLACE_PATH + "?query={query}?page={page}&size={size}&sort={sort}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("query","은행");
        queryParams.put("page","1");
        queryParams.put("size","5");
        queryParams.put("sort","recency");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, queryParams);
        assertThat(response.getBody()).contains("5"); //응답에 page size

    }



    @Test
    @DisplayName("query 파라미터가 없으면 오류발생")
    public void noKeywordParamReturn400Error() {

        String uri = SEARCH_PLACE_PATH + "?query={query}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("query",null);
        ResponseEntity<SearchResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SearchResponse.class, queryParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}
