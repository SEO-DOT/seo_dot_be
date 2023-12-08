package com.example.seo_dot.order.domain.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {
    private List<Long> cartIdList;
}
