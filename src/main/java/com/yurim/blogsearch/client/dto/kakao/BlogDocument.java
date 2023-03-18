package com.yurim.blogsearch.client.dto.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BlogDocument
{

    private String title;

    private String contents;

    private String url;

    private String blogname;

    private String thumbnail;

    @DateTimeFormat(pattern = "YYYY-MM-DDThh:mm:ss.000+tz")
    private LocalDateTime datetime;

}
