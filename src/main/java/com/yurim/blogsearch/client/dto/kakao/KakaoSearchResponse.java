package com.yurim.blogsearch.client.dto.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoSearchResponse {

    private BlogMeta meta;

    private List<BlogDocument> documents;

}
