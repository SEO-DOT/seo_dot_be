package com.example.seo_dot.cart.dto.request;

import lombok.Getter;

@Getter
public class CartQuantityUpdateDTO {

    private Long cartId;
    private int quantity;
}
