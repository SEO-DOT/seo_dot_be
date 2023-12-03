package com.example.seo_dot.mypage.dto.request;

import lombok.Setter;

@Setter
public class MyPageOrderPageParam {

    private Integer page = 1;
    private Integer per = 20;

    public Integer getPage() {
        if (page == 0) {
            return page;
        }
        return this.page - 1;
    }

    public Integer getPer() {
        return per;
    }

}
