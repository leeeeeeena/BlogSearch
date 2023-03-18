package com.yurim.blogsearch.client.dto.kakao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@ToString
public class BlogDocument
{

    private String title;

    private String contents;

    private String url;

    private String blogname;

    private String thumbnail;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSXXX",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(as = OffsetDateTime.class)
    private OffsetDateTime datetime;

}
