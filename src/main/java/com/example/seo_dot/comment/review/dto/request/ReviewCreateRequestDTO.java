package com.example.seo_dot.comment.review.dto.request;

import lombok.Getter;

@Getter
public class ReviewCreateRequestDTO {

    private String contents;

    private Integer score;

    private boolean spoiler;
}
