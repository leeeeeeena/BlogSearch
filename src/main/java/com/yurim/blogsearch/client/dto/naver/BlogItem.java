package com.yurim.blogsearch.client.dto.naver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BlogItem {

    private String title;

    private String link;

    private String description;

    private String bloggername;

    private String bloggerlink;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate postdate; //블로그 포스트 작성된 날짜


}
