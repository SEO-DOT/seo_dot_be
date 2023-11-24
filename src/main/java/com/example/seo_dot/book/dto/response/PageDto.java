package com.example.seo_dot.book.dto.response;

import lombok.Getter;

@Getter
public class PageDto<T> {
    private T data;
    private boolean hasNextPage;

    public PageDto(T data, boolean hasNextPage) {
        this.data = data;
        this.hasNextPage = hasNextPage;
    }
}
