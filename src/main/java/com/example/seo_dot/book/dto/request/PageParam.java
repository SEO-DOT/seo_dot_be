package com.example.seo_dot.book.dto.request;

import lombok.Setter;

@Setter
public class PageParam {
    private String category = "01";
    private Integer page = 1;
    private Integer per = 20;
    private String sort = "new";

    public String getCategory() {
        return category;
    }

    public Integer getPage() {
        if (page == 0) {
            return page;
        }
        return this.page - 1;
    }

    public String getSort() {
        return sort;
    }

    public Integer getPer() {
        return per;
    }
}