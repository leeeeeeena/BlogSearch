package com.yurim.blogsearch.search.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_query_searched_at", columnList = "query,searchedAt")
})
@Getter
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String query;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime searchedAt;

    private SearchHistory(String query) {
        this.query = query;
    }

    public static SearchHistory of(String query) {
        return new SearchHistory(query);
    }

    @PrePersist
    protected void onPersist() {
        searchedAt = LocalDateTime.now();
    }
}
