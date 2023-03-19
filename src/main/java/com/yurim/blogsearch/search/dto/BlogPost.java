package com.yurim.blogsearch.search.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.yurim.blogsearch.common.Constants.DEFAULT_DATE_FORMAT;

@Getter
@Builder
public class BlogPost {

    private String postName;

    private String url; // 글 링크

    private String blogName;

    private String description; //글 요약

    private String thumbnail;

    @DateTimeFormat(pattern = DEFAULT_DATE_FORMAT)
    private LocalDate date;

}
