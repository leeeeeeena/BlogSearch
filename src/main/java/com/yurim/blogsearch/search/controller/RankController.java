package com.yurim.blogsearch.search.controller;

import com.yurim.blogsearch.common.CommonResponse;
import com.yurim.blogsearch.common.ResponseCode;
import com.yurim.blogsearch.common.error.exception.BadRequestException;
import com.yurim.blogsearch.search.dto.RankRequest;
import com.yurim.blogsearch.search.dto.RankResponse;
import com.yurim.blogsearch.search.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static com.yurim.blogsearch.common.Constants.DEFAULT_DATE_FORMAT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/search/rank")
public class RankController {

    private final RankService rankService;

    @GetMapping
    public CommonResponse<RankResponse> getTopSearchedWord(@RequestParam(required = false)
                                                          @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT) LocalDate searchDate) {

        validateRankRequest(searchDate);
        RankResponse rankResponse = rankService.getTopSearchedQueries(RankRequest.of(searchDate));
        return CommonResponse.of(ResponseCode.REQUEST_SUCCESS, rankResponse);
    }

    private void validateRankRequest(LocalDate searchDate) {
        if (searchDate != null && searchDate.isAfter(LocalDate.now())) {
            throw new BadRequestException(ResponseCode.REQUEST_PARAMETER_NOT_ALLOWED,"당일 이후의 랭킹결과를 검색할 수 없습니다.");
        }
    }

}