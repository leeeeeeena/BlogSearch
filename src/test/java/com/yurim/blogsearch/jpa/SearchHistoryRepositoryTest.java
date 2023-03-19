package com.yurim.blogsearch.jpa;

import com.yurim.blogsearch.search.domain.SearchHistory;
import com.yurim.blogsearch.search.dto.SearchCount;
import com.yurim.blogsearch.search.repository.SearchHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Transactional
public class SearchHistoryRepositoryTest {

    //@DataJpaTest 는 JPA 레포지토리, EntityManager, DataSource 등 관련 빈들만 등록하여 비교적 가볍고, 내장 데이터베이스를 사용하여 별도 설정없이 사용 가능

    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    @Test
    @DisplayName("검색기록 테이블 저장")
    public void saveSearchHistory() {

        //given
        SearchHistory searchHistory = SearchHistory.of("금리");
        assertThat(searchHistory.getId()).isNull();

        //when
        SearchHistory savedSearchHistory = searchHistoryRepository.save(searchHistory);

        //then
        assertThat(savedSearchHistory.getId()).isNotNull();
    }


    @Test
    @DisplayName("많이 검색된 횟수대로 리턴")
    public void getTopSearchedQueries() {

        //given
        prepareData();
        int MAX_RANK_RESULT = 10;

        //when
        LocalDateTime startDate = LocalDate.now().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);
        List<SearchCount> searchCounts = searchHistoryRepository.findTopHistoryByDate(startDate, endDate, PageRequest.of(0, MAX_RANK_RESULT));

        //then
        assertThat(searchCounts).hasSizeLessThanOrEqualTo(MAX_RANK_RESULT);
        assertThat(searchCounts.get(0).getCount()).isGreaterThanOrEqualTo(searchCounts.get(1).getCount());

    }


    public void prepareData() {

        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));
        searchHistoryRepository.save(SearchHistory.of("금리"));


        searchHistoryRepository.save(SearchHistory.of("은행"));
        searchHistoryRepository.save(SearchHistory.of("은행"));
        searchHistoryRepository.save(SearchHistory.of("은행"));
        searchHistoryRepository.save(SearchHistory.of("은행"));


        searchHistoryRepository.save(SearchHistory.of("디지털"));
        searchHistoryRepository.save(SearchHistory.of("디지털"));

    }


}
