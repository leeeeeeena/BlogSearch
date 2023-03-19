package com.yurim.blogsearch.client.dto.kakao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yurim.blogsearch.common.ZonedDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

import static com.yurim.blogsearch.common.Constants.ZONED_DATE_TIME_FORMAT;

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

    @JsonFormat(pattern = ZONED_DATE_TIME_FORMAT)
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime datetime;

}
