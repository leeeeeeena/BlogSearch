package com.yurim.blogsearch.client.service.impl;


import com.yurim.blogsearch.client.dto.naver.BlogItem;
import com.yurim.blogsearch.client.dto.naver.NaverSearchResponse;
import com.yurim.blogsearch.client.service.BlogResponseModifier;
import com.yurim.blogsearch.search.dto.BlogPost;
import com.yurim.blogsearch.search.dto.SearchResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NaverBlogResponseModifier implements BlogResponseModifier<NaverSearchResponse<BlogItem>> {

    private BlogPost modify(BlogItem response) {

        return BlogPost.builder()
                .blogName(response.getBloggername())
                .description(response.getDescription())
                .postName(response.getTitle())
                .url(response.getLink())
                .date(response.getPostdate())
                .build();
    }

    @Override
    public SearchResponse modify(NaverSearchResponse<BlogItem> response) {

        List<BlogPost> blogPosts = response.getItems().stream()
                .map(blogItem -> this.modify(blogItem))
                .collect(Collectors.toList());


        SearchResponse<BlogPost> searchResponse = new SearchResponse<>(
                response.getTotal(),
                response.getDisplay(),
                blogPosts);

        return searchResponse;
    }

}
