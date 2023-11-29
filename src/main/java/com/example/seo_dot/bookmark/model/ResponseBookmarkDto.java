package com.example.seo_dot.bookmark.model;

import com.example.seo_dot.bookmark.domain.Bookmark;
import com.example.seo_dot.bookmark.domain.ColorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBookmarkDto {

    private Long categoryId;
    private String category;
    private String colorCode;
    private String thumbnail;

    public ResponseBookmarkDto(Bookmark bookmark) {
        this.categoryId = bookmark.getId();
        this.category = bookmark.getCategory();
        this.colorCode = bookmark.getColorCode();
        this.thumbnail = bookmark.getThumbnail();
    }
}