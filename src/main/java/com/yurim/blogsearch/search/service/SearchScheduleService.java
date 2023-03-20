package com.yurim.blogsearch.search.service;

import com.yurim.blogsearch.search.repository.SearchCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchScheduleService {

    @Value("${schedule.cache.cron}")
    private static final String REMOVE_SEARCH_CACHE_CRON = "0 50 23 * * *"; // second, minute, hour, day, month, weekday

    private final SearchCacheRepository redisSearchRepository;

    @Scheduled(cron = REMOVE_SEARCH_CACHE_CRON)
    public void removeSearchCache() {
        LocalDate yesterday = LocalDate.now().minusDays(1L);
        log.info("remove search cache data at : {}", yesterday);
        redisSearchRepository.expireByDate(yesterday);
    }

}