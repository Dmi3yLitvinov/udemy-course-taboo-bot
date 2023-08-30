package com.taboo.model;

import lombok.Data;

import java.util.List;

@Data
public class SevenWonders {
    private List<Article> articles;

    @Data
    public static class Article {
        private String title;
        private String description;
        private String articleUrl;
        private String imageUrl;
        private String thumbUrl;
    }
}
