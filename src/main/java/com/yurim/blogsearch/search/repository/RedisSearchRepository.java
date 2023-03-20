package com.yurim.blogsearch.search.repository;

import com.yurim.blogsearch.search.dto.SearchCount;
import com.yurim.blogsearch.search.dto.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RedisSearchRepository implements SearchCacheRepository {

    private final RedisTemplate redisTemplate;
    private final String REDIS_SEARCH_QUERY_KEY = "search:query:%s";

    @Override
    public void updateScore(SearchRequest searchRequest) {

        redisTemplate.opsForZSet().incrementScore(getKey(LocalDate.now()),searchRequest.getQuery(),1); //조회와 업데이트을 분리하지 않고 redis 자체 제공 함수를 사용하면 동시성 보장

    }

    @Override
    public List<SearchCount> getRankedQueries(LocalDate targetDate, int size) { //인기검색어

        Set<ZSetOperations.TypedTuple<String>> queryWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(getKey(targetDate),0,size -1);

        if (Objects.isNull(queryWithScores)) {
            return new ArrayList<>();
        }

        List<SearchCount> rankedQueries = queryWithScores.stream().map(this::convertTypedTupleToSearchCount).collect(Collectors.toList());
        return rankedQueries;
    }

    private SearchCount convertTypedTupleToSearchCount(ZSetOperations.TypedTuple<String> typedTuple) {
        SearchCount searchCount = SearchCount.of(typedTuple.getValue(), typedTuple.getScore().longValue());
        return searchCount;
    }


    public String getKey(LocalDate localDate) {
        return String.format(REDIS_SEARCH_QUERY_KEY,localDate);
    }


    @Override
    public void expireByDate(LocalDate targetDate) {
        redisTemplate.expire(getKey(targetDate), 1L, TimeUnit.SECONDS);
    }

}
