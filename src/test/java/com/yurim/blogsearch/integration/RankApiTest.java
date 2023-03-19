package com.yurim.blogsearch.integration;

import com.yurim.blogsearch.search.domain.SearchHistory;
import com.yurim.blogsearch.search.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //RANDOM_PORT 사용 시 실제 내장톰캣 기동
@Tag("integration")
public class RankApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    public String SEARCH_RANK_PATH = "/v1/search/rank";
    public String SEARCH_DATE_EXAMPLE = "2023-03-19";

    public HttpEntity httpEntity;

    @BeforeEach
    public void prepare(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(httpHeaders);

        inputData();
    }

    @Test
    @DisplayName("파라미터 없이 요청 시 당일 검색랭킹 리턴")
    public void requestSearchRankWithNoParam() {

        String uri = SEARCH_RANK_PATH;
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    @DisplayName("파라미터 요청 시 해당일자 검색랭킹 리턴")
    public void requestSearchRankWithParam() {

        String uri = SEARCH_RANK_PATH + "?searchDate={searchDate}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("searchDate",SEARCH_DATE_EXAMPLE);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class, queryParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }



    @Test
    @DisplayName("랭킹검색 응답이 1개 이상일 시 다음 필드를 포함하고 있어야 한다.")
    public void responseContainValues() {

        String uri = SEARCH_RANK_PATH ;
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        assertThat(response.getBody()).contains("topSearchQueries");
        assertThat(response.getBody()).contains("query");
        assertThat(response.getBody()).contains("count");

    }


    @Test
    @DisplayName("미래의 키워드 검색 랭킹을 요청할 수 없다.")
    public void cannotRequestFutureDate() {

        String uri = SEARCH_RANK_PATH + "?searchDate={searchDate}";
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("searchDate","3023-03-20");
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class,queryParams);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }


    public void inputData() {
        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));

        searchHistoryRepository.save(SearchHistory.of("은행"));
        searchHistoryRepository.save(SearchHistory.of("은행"));

        searchHistoryRepository.save(SearchHistory.of("디지털"));
    }

    // RANDOMPORT 에서는 @Transactional rollback 되지 않으므로 clear() 구현 필요하나, 본 프로젝트에서는 test 환경을 따로 두지 않고 있으므로 생략

}
