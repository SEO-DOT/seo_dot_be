package com.example.seo_dot.book.dto.response;

import com.example.seo_dot.book.domain.Keyword;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KeywordResponseDTO {
    private Long id;
    private String keyword;

    public KeywordResponseDTO(Keyword keyword) {
        if (keyword != null) {
            this.id = keyword.getId();
            this.keyword = keyword.getKeyword();
        }
    }
}
