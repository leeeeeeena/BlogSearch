package com.yurim.blogsearch.search.service;

import com.yurim.blogsearch.search.dto.RankRequest;
import com.yurim.blogsearch.search.dto.RankResponse;

public interface RankService {

    RankResponse getTopSearchedQueries(RankRequest rankRequest);
}
