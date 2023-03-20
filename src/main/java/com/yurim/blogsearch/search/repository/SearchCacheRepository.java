package com.yurim.blogsearch.search.repository;

import com.yurim.blogsearch.search.dto.SearchCount;
import com.yurim.blogsearch.search.dto.SearchRequest;

import java.time.LocalDate;
import java.util.List;

public interface SearchCacheRepository {

    void updateScore(SearchRequest searchRequest);

    List<SearchCount> getRankedQueries(LocalDate targetDate, int size);

    void expireByDate(LocalDate targetDate);

}
