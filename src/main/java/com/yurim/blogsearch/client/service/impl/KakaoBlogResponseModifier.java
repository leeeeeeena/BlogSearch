package com.yurim.blogsearch.client.service.impl;

import com.yurim.blogsearch.client.dto.kakao.BlogDocument;
import com.yurim.blogsearch.client.dto.kakao.KakaoSearchResponse;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchResponse;
import com.yurim.blogsearch.client.service.BlogResponseModifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KakaoBlogResponseModifier implements BlogResponseModifier<KakaoSearchResponse<BlogDocument>> {

    private BlogPost modify(BlogDocument response) {

        return BlogPost.builder()
                .blogName(response.getBlogname())
                .description(response.getContents())
                .postName(response.getTitle())
                .url(response.getUrl())
                .thumbnail(response.getThumbnail())
                .date(response.getDatetime().toLocalDate())
                .build();
    }

    @Override
    public SearchResponse modify(KakaoSearchResponse<BlogDocument> response) {

        List<BlogPost> blogPosts = response.getDocuments().stream()
                .map(blogDocument -> this.modify(blogDocument))
                .collect(Collectors.toList());


        SearchResponse<BlogPost> searchResponse = new SearchResponse<>(
                response.getMeta().getTotal_count(),
                response.getDocuments().size(),
                blogPosts);

        return searchResponse;
    }

}
