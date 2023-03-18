package com.yurim.blogsearch.client.dto.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class KakaoSearchResponse {

    private BlogMeta meta;

    private List<BlogDocument> documents;

}
