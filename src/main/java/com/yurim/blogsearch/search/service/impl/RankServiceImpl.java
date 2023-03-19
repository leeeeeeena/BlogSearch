package com.yurim.blogsearch.search.service.impl;

import com.yurim.blogsearch.search.dto.SearchCount;
import com.yurim.blogsearch.search.dto.RankRequest;
import com.yurim.blogsearch.search.dto.RankResponse;
import com.yurim.blogsearch.search.repository.SearchHistoryRepository;
import com.yurim.blogsearch.search.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

    private static final int MAX_RANK_RESULT = 10;
    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    @Transactional(readOnly = true)
    public RankResponse getTopSearchedQueries(RankRequest rankRequest) {

        LocalDate targetDate = Optional.ofNullable(rankRequest.getSearchDate())
                .orElse(LocalDate.now());
        LocalDateTime startDateTime = targetDate.atStartOfDay();
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        List<SearchCount> searchCounts = searchHistoryRepository.findTopHistoryByDate(startDateTime, endDateTime, PageRequest.of(0, MAX_RANK_RESULT));

        return RankResponse.of(searchCounts);
    }

}