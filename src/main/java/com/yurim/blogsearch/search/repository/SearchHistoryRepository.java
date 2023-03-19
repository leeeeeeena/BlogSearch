package com.yurim.blogsearch.search.repository;

import com.yurim.blogsearch.search.domain.SearchHistory;
import com.yurim.blogsearch.search.dto.SearchCount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    @Query("SELECT new com.yurim.blogsearch.search.dto.SearchCount(sh.query, COUNT(sh)) " +
            "FROM SearchHistory sh WHERE sh.searchedAt >=?1 AND sh.searchedAt < ?2 GROUP BY sh.query ORDER BY COUNT(sh) DESC")
    List<SearchCount> findTopHistoryByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}
