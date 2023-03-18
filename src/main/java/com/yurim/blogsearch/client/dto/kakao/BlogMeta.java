package com.yurim.blogsearch.client.dto.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlogMeta {

    private int total_count;

    private int pageable_count; //total_cnt 중 노출 가능 문서 수

    private boolean is_end; // 현재 페이지가 마지막 페이지인지

}
