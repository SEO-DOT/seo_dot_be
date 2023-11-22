package com.example.seo_dot.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class RequestPayDto {

    private String orderUid;
    private String bookName;
    private String username;
    private Long price;
    private String email;

    public RequestPayDto(String orderUid, String bookName, String username, Long price, String email) {
        this.orderUid = orderUid;
        this.bookName = bookName;
        this.username = username;
        this.price = price;
        this.email = email;
    }
}
