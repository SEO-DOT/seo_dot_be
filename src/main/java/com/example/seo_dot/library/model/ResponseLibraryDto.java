package com.example.seo_dot.library.model;

import com.example.seo_dot.library.domain.Library;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseLibraryDto {

    private Long categoryId;
    private String category;
    private String colorCode;
    private String thumbnail;

    public ResponseLibraryDto(Library library) {
        this.categoryId = library.getId();
        this.category = library.getCategory();
        this.colorCode = library.getColorCode();
        this.thumbnail = library.getThumbnail();
    }
}